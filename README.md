# Exposición: Técnicas de Prueba (Caja negra y Caja blanca)

Proyecto Java sin dependencias externas (solo JDK). Sirve para mostrar en vivo:

- **Caja blanca → Cobertura de condición** (`CoberturaCondicionTest.java`)
- **Caja negra → Pruebas de exploración / SBTM** (`ExploracionSesion.java`)

## Archivos

| Archivo | Rol |
|---|---|
| `Validador.java` | Sistema bajo prueba (SUT): regla de elegibilidad con 3 condiciones combinadas con `&&` |
| `CoverageTracker.java` | Instrumentador que registra si cada condición tomó V y F, y calcula el % de cobertura |
| `CoberturaCondicionTest.java` | Casos de prueba de caja blanca, diseñados mirando el código |
| `ExploracionSesion.java` | Sesión exploratoria de caja negra (charter + pruebas improvisadas + notas/hallazgos) |
| `Main.java` | Punto de entrada: corre ambas técnicas y muestra un resumen final |

## Cómo compilar y ejecutar

```bash
javac *.java
java Main
```

Esto imprime en consola:
1. Los 3 casos de caja blanca (PASA/FALLA) + el reporte de cobertura de condición (llega a 100%).
2. La sesión exploratoria de caja negra, con cada "idea de prueba" y las notas/hallazgos que van surgiendo.
3. Un resumen final comparando ambas técnicas, ideal para mostrarle al profesor como cierre.

## Informe HTML

Además de la salida por consola, el proyecto ahora genera un informe HTML en `reports/report.html` que resume:

- Casos de prueba de caja blanca con resultado esperado/obtenido
- Entradas y resultados de la sesión exploratoria (caja negra)
- Notas y hallazgos recogidos durante la exploración
- Cobertura de condición (cada condición: visto V / visto F)

Para compilar y ejecutar con el script incluido:

```bash
chmod +x build-and-run.sh
./build-and-run.sh
```

Al finalizar, abra `reports/report.html` en su navegador para mostrar el informe durante la exposición.

## Cómo presentarlo

1. Muestra primero `Validador.java` y explica que tiene una decisión con 3 condiciones.
2. Corre `Main` en vivo y muestra el bloque de **cobertura de condición**: resalta que con solo 3 casos se llega al 100%, pero que eso no cubre todas las combinaciones posibles de la decisión (gancho para hablar de MC/DC si el profesor pregunta).
3. Sigue con el bloque de **exploración**: explica que aquí no hay casos fijos, que el charter guía la sesión, y que las notas al final son el verdadero "producto" de la exploración (preguntas de diseño, no solo defectos).
4. Cierra con el resumen: cobertura de condición mide qué tanto del código se ejecutó; la exploración evalúa si el comportamiento tiene sentido para el usuario. Son complementarias.
