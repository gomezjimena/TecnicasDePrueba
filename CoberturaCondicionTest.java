/**
 * PRUEBAS DE CAJA BLANCA: Cobertura de condicion.
 *
 * Disenamos los casos de prueba MIRANDO EL CODIGO de Validador.esElegible,
 * con el objetivo de que cada una de las 3 condiciones individuales tome
 * al menos una vez el valor VERDADERO y al menos una vez el valor FALSO.
 *
 * Nota importante para la exposicion: con solo 2 casos (todo V / todo F)
 * ya se logra el 100% de cobertura de condicion, pero eso NO implica que
 * se hayan probado todas las combinaciones posibles de la decision (para
 * eso existen tecnicas mas exigentes, como MC/DC). El Caso 3 se agrega
 * para mostrar una combinacion mixta y reforzar ese punto en vivo.
 */
public class CoberturaCondicionTest {

    private static int totalCasos = 0;
    private static int casosExitosos = 0;

    public static void ejecutar() {
        System.out.println("===== CAJA BLANCA: COBERTURA DE CONDICION =====");
        CoverageTracker.reiniciar();
                ReportGenerator.iniciar();

        // Caso 1: las 3 condiciones en VERDADERO
        verificar("Caso 1 (edad=25, ingresos=1500000, deuda=false)",
                Validador.esElegible(25, 1_500_000, false), true);

        // Caso 2: las 3 condiciones en FALSO
        verificar("Caso 2 (edad=16, ingresos=500000, deuda=true)",
                Validador.esElegible(16, 500_000, true), false);

        // Caso 3 (adicional): combinacion mixta -> ilustra que ya cubrimos
        // V/F de cada condicion, pero esta combinacion especifica de la
        // decision (V, F, V) no se habia probado todavia.
        verificar("Caso 3 (edad=30, ingresos=500000, deuda=false)",
                Validador.esElegible(30, 500_000, false), false);
        
        // register suite-level note in report generator
        ReportGenerator.addNote("Suite: Cobertura de condición ejecutada.");
        System.out.printf("%nResultado de la suite: %d/%d casos exitosos%n", casosExitosos, totalCasos);
        CoverageTracker.imprimirReporte();
    }

    private static void verificar(String nombreCaso, boolean obtenido, boolean esperado) {
        totalCasos++;
        boolean paso = obtenido == esperado;
        if (paso) casosExitosos++;
        System.out.printf("  %-55s esperado=%-6s obtenido=%-6s -> %s%n",
                nombreCaso, esperado, obtenido, paso ? "PASA" : "FALLA");
                ReportGenerator.addTestCase(nombreCaso, esperado, obtenido, paso);
    }
}
