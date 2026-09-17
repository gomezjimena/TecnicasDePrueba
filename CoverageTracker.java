import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Instrumentador simple de cobertura de condicion (caja blanca).
 *
 * Registra, para cada condicion booleana individual evaluada dentro de una
 * decision, si tomo el valor VERDADERO y/o FALSO al menos una vez durante
 * la ejecucion de las pruebas. Esto simula de forma simplificada lo que
 * hacen herramientas reales de cobertura (como JaCoCo) pero a nivel de
 * condicion, no solo de linea o de rama.
 */
public class CoverageTracker {

    // nombre de la condicion -> [fue vista como verdadera?, fue vista como falsa?]
    private static final Map<String, boolean[]> registro = new LinkedHashMap<>();

    private CoverageTracker() {
    }

    /**
     * Registra el resultado de evaluar una condicion y devuelve ese mismo
     * valor, para poder "envolver" la condicion original sin cambiar la
     * logica del programa.
     */
    public static boolean track(String nombreCondicion, boolean valor) {
        boolean[] estado = registro.computeIfAbsent(nombreCondicion, k -> new boolean[2]);
        if (valor) {
            estado[0] = true; // vista en verdadero
        } else {
            estado[1] = true; // vista en falso
        }
        return valor;
    }

    /** Limpia el registro para poder medir un nuevo lote de pruebas desde cero. */
    public static void reiniciar() {
        registro.clear();
    }

    /** Imprime un reporte legible con el porcentaje de cobertura de condicion. */
    public static void imprimirReporte() {
        System.out.println();
        System.out.println("----- Reporte de cobertura de condicion -----");

        int totalResultadosPosibles = registro.size() * 2; // cada condicion: V y F
        int resultadosCubiertos = 0;

        for (Map.Entry<String, boolean[]> entrada : registro.entrySet()) {
            String nombre = entrada.getKey();
            boolean vistaV = entrada.getValue()[0];
            boolean vistaF = entrada.getValue()[1];
            if (vistaV) resultadosCubiertos++;
            if (vistaF) resultadosCubiertos++;

            System.out.printf("  Condicion: %-22s Verdadero: %-4s Falso: %-4s%n",
                    nombre, vistaV ? "SI" : "NO", vistaF ? "SI" : "NO");
        }

        double porcentaje = totalResultadosPosibles == 0
                ? 0.0
                : (100.0 * resultadosCubiertos / totalResultadosPosibles);

        System.out.printf("  => Cobertura de condicion: %.1f%% (%d de %d resultados posibles)%n",
                porcentaje, resultadosCubiertos, totalResultadosPosibles);

        // También alimentamos el ReportGenerator para producir un HTML.
        for (Map.Entry<String, boolean[]> entrada : registro.entrySet()) {
            String nombre = entrada.getKey();
            boolean vistaV = entrada.getValue()[0];
            boolean vistaF = entrada.getValue()[1];
            ReportGenerator.recordCondition(nombre, vistaV, vistaF);
        }
    }
}
