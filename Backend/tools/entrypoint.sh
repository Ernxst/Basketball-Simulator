#!/bin/bash
set -e

if [[ "$ENV" != "production" ]]; then
  echo "== Waiting for Database =="
  while ! pg_isready -h db -p 5432 -q -U "${POSTGRES_USER}"; do
    echo >&2 "Postgres DB is unavailable - waiting"
    sleep 1
  done
fi

# Then exec the container's main process (what's set as CMD in the Dockerfile).
exec "$@"
