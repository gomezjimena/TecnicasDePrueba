/**
 * Clase de negocio muy simple que se usa como "sistema bajo prueba" (SUT)

 * Regla de negocio: una persona es elegible (por ejemplo, para un credito
 * o un registro) si es mayor de edad, tiene ingresos suficientes y no
 * tiene una deuda vigente.
 */
public class Validador {

    public static boolean esElegible(int edad, double ingresos, boolean tieneDeudaVigente) {
        // Cada condicion se evalua por separado y se registra en el
        // CoverageTracker. Esto es lo que permite, mas adelante, medir
        // cuantas de ellas tomaron valor V y F durante las pruebas.
        boolean condEdad = CoverageTracker.track("edad >= 18", edad >= 18);
        boolean condIngresos = CoverageTracker.track("ingresos >= 1000000", ingresos >= 1_000_000);
        boolean condSinDeuda = CoverageTracker.track("!tieneDeudaVigente", !tieneDeudaVigente);

        // La DECISION completa combina las 3 condiciones con AND logico.
        return condEdad && condIngresos && condSinDeuda;
    }
}
