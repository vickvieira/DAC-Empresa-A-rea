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
CREATE DATABASE "FuncionárioAutenticação";
CREATE DATABASE "Cliente";
CREATE DATABASE "Voos";
CREATE DATABASE "Reservas";
CREATE DATABASE "Funcionário";

-- Grant privileges para o usuário admin
GRANT ALL PRIVILEGES ON DATABASE "FuncionárioAutenticação" TO admin;
GRANT ALL PRIVILEGES ON DATABASE "Cliente" TO admin;
GRANT ALL PRIVILEGES ON DATABASE "Voos" TO admin;
GRANT ALL PRIVILEGES ON DATABASE "Reservas" TO admin;
GRANT ALL PRIVILEGES ON DATABASE "Funcionário" TO admin;