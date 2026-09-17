import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Generador simple de informes HTML para la demo.
 *
 * Recolecta resultados de las suites y genera un archivo
 * `reports/report.html` con un resumen legible para la presentación.
 */
public class ReportGenerator {

    private static final List<String> testCases = new ArrayList<>();
    private static final List<String> explorations = new ArrayList<>();
    private static final List<String> notes = new ArrayList<>();
    private static final Map<String, boolean[]> conditions = new LinkedHashMap<>();

    public static void iniciar() {
        // asegura que exista la carpeta de reportes
        File dir = new File("reports");
        if (!dir.exists()) dir.mkdirs();
    }

    public static void addTestCase(String nombre, boolean esperado, boolean obtenido, boolean paso) {
        testCases.add(String.format("%s|esperado=%s|obtenido=%s|paso=%s", nombre, esperado, obtenido, paso));
    }

    public static void addExploration(String idea, int edad, double ingresos, boolean deuda, boolean resultado) {
        explorations.add(String.format("%s|edad=%d|ingresos=%.2f|deuda=%s|resultado=%s",
                idea, edad, ingresos, deuda, resultado));
    }

    public static void addNote(String nota) {
        notes.add(nota);
    }

    public static void recordCondition(String nombre, boolean vistaV, boolean vistaF) {
        conditions.put(nombre, new boolean[]{vistaV, vistaF});
    }

    public static void finalizar() {
        File out = new File("reports/report.html");
        try (BufferedWriter w = new BufferedWriter(new FileWriter(out))) {
            w.write("<html><head><meta charset=\"utf-8\"><title>Informe de Pruebas</title>");
            w.write("<style>body{font-family:Arial,Helvetica,sans-serif;}table{border-collapse:collapse;width:100%}td,th{border:1px solid #ddd;padding:8px}th{background:#f2f2f2}</style>");
            w.write("</head><body>");
            w.write("<h1>Informe de Pruebas - Caja Blanca y Caja Negra</h1>");

            w.write("<h2>Casos de prueba (Caja Blanca)</h2>");
            w.write("<table><tr><th>Nombre</th><th>Esperado</th><th>Obtenido</th><th>Resultado</th></tr>");
            for (String t : testCases) {
                String[] parts = t.split("\\|");
                w.write("<tr>");
                w.write("<td>" + escape(parts[0]) + "</td>");
                w.write("<td>" + escape(parts[1]) + "</td>");
                w.write("<td>" + escape(parts[2]) + "</td>");
                w.write("<td>" + escape(parts[3]) + "</td>");
                w.write("</tr>");
            }
            w.write("</table>");

            w.write("<h2>Exploración (Caja Negra)</h2>");
            w.write("<table><tr><th>Idea</th><th>Edad</th><th>Ingresos</th><th>Deuda</th><th>Resultado</th></tr>");
            for (String e : explorations) {
                String[] p = e.split("\\|");
                w.write("<tr>");
                w.write("<td>" + escape(p[0]) + "</td>");
                w.write("<td>" + escape(p[1]) + "</td>");
                w.write("<td>" + escape(p[2]) + "</td>");
                w.write("<td>" + escape(p[3]) + "</td>");
                w.write("<td>" + escape(p[4]) + "</td>");
                w.write("</tr>");
            }
            w.write("</table>");

            if (!notes.isEmpty()) {
                w.write("<h3>Notas y hallazgos</h3><ul>");
                for (String n : notes) w.write("<li>" + escape(n) + "</li>");
                w.write("</ul>");
            }

            w.write("<h2>Cobertura de condición</h2>");
            w.write("<table><tr><th>Condición</th><th>Visto Verdadero</th><th>Visto Falso</th></tr>");
            for (Map.Entry<String, boolean[]> en : conditions.entrySet()) {
                boolean[] v = en.getValue();
                w.write("<tr>");
                w.write("<td>" + escape(en.getKey()) + "</td>");
                w.write("<td>" + (v[0] ? "SI" : "NO") + "</td>");
                w.write("<td>" + (v[1] ? "SI" : "NO") + "</td>");
                w.write("</tr>");
            }
            w.write("</table>");

            w.write("</body></html>");
        } catch (IOException ex) {
            System.err.println("No se pudo escribir el informe HTML: " + ex.getMessage());
        }
        System.out.println("HTML report generado en: reports/report.html");
    }

    private static String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
