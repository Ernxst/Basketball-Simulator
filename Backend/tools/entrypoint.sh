#!/bin/bash
set -e
# Check if application is compiled before running
#ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"
#JAR="$ROOT/app-1.0-SNAPSHOT.jar"
#
#if [ ! -f "$JAR" ]; then
#  echo "[ERROR] Application is not built" && exit 1
#fi

# Then exec the container's main process (what's set as CMD in the Dockerfile).
exec "$@"
