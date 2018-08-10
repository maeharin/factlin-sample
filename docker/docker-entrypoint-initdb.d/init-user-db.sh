#!/bin/bash
set -e

echo "create database..."
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE dvdrental;
EOSQL

echo "restore dvd rental database"
pg_restore -U postgres -d dvdrental /tmp/dumpfile/dvdrental.tar

echo "create another tables..."
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" dvdrental <<-EOSQL
    DROP TABLE IF EXISTS users;

    CREATE TABLE users (
      id SERIAL PRIMARY KEY,
      name VARCHAR(256) NOT NULL,
      job VARCHAR(256) NOT NULL DEFAULT 'engineer',
      status VARCHAR(256) NOT NULL DEFAULT 'ACTIVE',
      age INTEGER NOT NULL,
      score NUMERIC NOT NULL,
      is_admin BOOLEAN NOT NULL,
      birth_day DATE NOT NULL,
      nick_name VARCHAR(256),
      created_timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,
      updated_timestamp TIMESTAMP WITHOUT TIME ZONE
    );

    COMMENT ON TABLE users IS 'user table';
    COMMENT ON COLUMN users.id IS 'primary key';
    COMMENT ON COLUMN users.name IS 'user name';
    COMMENT ON COLUMN users.job IS 'job name';
    COMMENT ON COLUMN users.status IS 'activate status';
    COMMENT ON COLUMN users.age IS 'user age';
    COMMENT ON COLUMN users.score IS 'game score';
    COMMENT ON COLUMN users.is_admin IS 'user is admin user or not';
    COMMENT ON COLUMN users.birth_day IS 'user birth day';
    COMMENT ON COLUMN users.nick_name IS 'nick name';
EOSQL
