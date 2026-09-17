import java.util.ArrayList;
import java.util.List;

/**
 * PRUEBAS DE CAJA NEGRA: Sesion de pruebas exploratorias.
 *
 * A diferencia de CoberturaCondicionTest (donde los casos se disenan mirando
 * el codigo fuente), aqui el "tester" NO parte de casos fijos: define una
 * mision (charter), y va decidiendo el siguiente valor a probar segun lo
 * que observa en cada resultado, buscando valores limite, combinaciones
 * poco obvias y posibles preguntas de diseno -- tal como se haria en una
 * sesion real de Session-Based Test Management (SBTM).
 */
public class ExploracionSesion {

    private static final String CHARTER =
            "Explorar Validador.esElegible con valores limite y combinaciones "
          + "poco obvias, buscando comportamientos inesperados o preguntas de "
          + "diseno que no surgen solo de leer el requisito.";

    private static final List<String> notas = new ArrayList<>();

    public static void ejecutar() {
        System.out.println();
        System.out.println("===== CAJA NEGRA: SESION EXPLORATORIA (SBTM) =====");
        System.out.println("Charter: " + CHARTER);
        System.out.println();

        ReportGenerator.iniciar();

        probar("Edad justo en el limite inferior (18)", 18, 2_000_000, false);
        probar("Edad justo debajo del limite (17)", 17, 2_000_000, false);
        probar("Ingresos exactamente en el limite (1000000)", 25, 1_000_000, false);
        probar("Edad negativa (dato fuera de dominio)", -5, 2_000_000, false);
        probar("Ingresos negativos (dato fuera de dominio)", 25, -500_000, false);
        probar("Edad extrema (200 anios)", 200, 2_000_000, false);
        probar("Ingresos con decimales cerca del limite", 25, 999_999.99, false);
        probar("Persona con deuda pero el resto en regla", 30, 5_000_000, true);
        probar("Todos los valores en cero", 0, 0, false);

        System.out.println();
        System.out.println("--- Notas y hallazgos de la sesion ---");
        for (String nota : notas) {
            System.out.println("  * " + nota);
        }
    }

    private static void probar(String ideaDePrueba, int edad, double ingresos, boolean deuda) {
        boolean resultado = Validador.esElegible(edad, ingresos, deuda);
        System.out.printf("  %-46s edad=%-5d ingresos=%-12.2f deuda=%-5s -> elegible=%s%n",
                ideaDePrueba, edad, ingresos, deuda, resultado);
        ReportGenerator.addExploration(ideaDePrueba, edad, ingresos, deuda, resultado);

        // El tester va registrando observaciones/preguntas a medida que
        // interactua con el sistema (esto es lo caracteristico de la
        // exploracion: aprender y decidir sobre la marcha).
        if (edad < 0) {
            notas.add("Con edad negativa el metodo simplemente responde 'no elegible'. "
                    + "¿No deberia rechazarse antes como dato invalido, en vez de "
                    + "tratarse igual que un caso 'legitimo' de no elegibilidad?");
        }
        if (ingresos < 0) {
            notas.add("Ingresos negativos tampoco generan ningun error explicito. "
                    + "Posible vacio en la validacion de entrada del formulario.");
        }
        if (edad == 18) {
            notas.add("edad=18 SI se considera elegible (regla >=), confirma que "
                    + "el limite de mayoria de edad es inclusivo, tal como dice el requisito.");
        }
        if (ingresos == 1_000_000) {
            notas.add("ingresos=1000000 exacto tambien es elegible, confirma limite inclusivo "
                    + "en el valor frontera de ingresos.");
        }
    }
}
