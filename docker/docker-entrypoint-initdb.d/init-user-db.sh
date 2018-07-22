#!/bin/bash
set -e

echo "create database..."
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE dvdrental;
EOSQL

echo "restore dvd rental database"
pg_restore -U postgres -d dvdrental /tmp/dumpfile/dvdrental.tar
