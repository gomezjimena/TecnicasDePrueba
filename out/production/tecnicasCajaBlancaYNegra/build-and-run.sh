#!/usr/bin/env bash
set -euo pipefail

echo "Compilando..."
javac *.java

echo "Ejecutando Main..."
java Main

if [ -f reports/report.html ]; then
  echo "Informe HTML generado en reports/report.html"
else
  echo "No se generó el informe HTML. Revise la ejecución." >&2
fi
