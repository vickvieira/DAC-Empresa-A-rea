DO
$do$
BEGIN
   IF EXISTS (
      SELECT FROM pg_catalog.pg_roles
      WHERE rolname = 'admin') THEN
      RAISE NOTICE 'Role "admin" already exists. Skipping.';
   ELSE
      CREATE ROLE admin LOGIN PASSWORD '123456';
   END IF;
END
$do$;

-- Criação dos bancos de dados
CREATE DATABASE "clientesdb";          -- Para dados de clientes
CREATE DATABASE "reservac";            -- Para comandos de reservas (CQRS)
CREATE DATABASE "reservaq";            -- Para consultas de reservas (CQRS)
CREATE DATABASE "funcionariodb";       -- Para dados de funcionários
CREATE DATABASE "voosdb";              -- Para gerenciamento de voos

-- Grant privileges para o usuário admin
GRANT ALL PRIVILEGES ON DATABASE "clientesdb" TO admin;
GRANT ALL PRIVILEGES ON DATABASE "reservac" TO admin;
GRANT ALL PRIVILEGES ON DATABASE "reservaq" TO admin;
GRANT ALL PRIVILEGES ON DATABASE "funcionariodb" TO admin;
GRANT ALL PRIVILEGES ON DATABASE "voosdb" TO admin;