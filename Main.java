public class Main {

    public static void main(String[] args) {
        System.out.println("  EXPOSICION - TECNICAS DE PRUEBA (Curso de Calidad)");
        System.out.println("  Caja negra  -> Pruebas de exploracion");
        System.out.println("  Caja blanca -> Cobertura de condicion");
        System.out.println();

        CoberturaCondicionTest.ejecutar();
        ExploracionSesion.ejecutar();

        ReportGenerator.finalizar();

        System.out.println();
        System.out.println("===== RESUMEN =====");
        System.out.println("1) Caja blanca: se alcanzo 100% de cobertura de condicion con solo");
        System.out.println("   3 casos de prueba, disenados mirando directamente el codigo fuente.");
        System.out.println("2) Caja negra: la sesion exploratoria encontro preguntas de diseno");
        System.out.println("   (validacion de edad/ingresos negativos) que NO surgen de analizar");
        System.out.println("   el codigo, sino de interactuar libremente con el sistema.");
        System.out.println("3) Conclusion: ambas tecnicas son complementarias, no sustitutas:");
        System.out.println("   una mide que tanto del CODIGO se ha recorrido; la otra evalua si");
        System.out.println("   el COMPORTAMIENTO tiene sentido desde la perspectiva del usuario.");
    }
}
