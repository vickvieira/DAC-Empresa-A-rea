-- Create schemas
CREATE SCHEMA IF NOT EXISTS autenticacao;
CREATE SCHEMA IF NOT EXISTS cliente;
CREATE SCHEMA IF NOT EXISTS voos;
CREATE SCHEMA IF NOT EXISTS reservas;
CREATE SCHEMA IF NOT EXISTS funcionario;

-- Create tables in autenticacao schema
CREATE TABLE IF NOT EXISTS autenticacao.login (
    id VARCHAR(50) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

-- Create tables in cliente schema
CREATE TABLE IF NOT EXISTS cliente.clientes (
    id VARCHAR(50) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL,
    tipo CHAR(1) NOT NULL
);

CREATE TABLE IF NOT EXISTS cliente.milhas (
    id VARCHAR(50) PRIMARY KEY,
    cliente_id VARCHAR(50),
    saldo DECIMAL(12,2) NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS cliente.usuarios (
    id VARCHAR(50) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS cliente.endereco (
    id VARCHAR(50) PRIMARY KEY,
    rua VARCHAR(255) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    complemento VARCHAR(255),
    cidade VARCHAR(255) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    cep VARCHAR(9) NOT NULL
);

CREATE TABLE IF NOT EXISTS cliente.transacoes_milhas (
    id VARCHAR(50) PRIMARY KEY,
    cliente_id VARCHAR(50) NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    operacao DECIMAL(12,2) NOT NULL,
    saldo DECIMAL(12,2) NOT NULL,
    descricao VARCHAR(255) NOT NULL
);

-- Create tables in voos schema
CREATE TABLE IF NOT EXISTS voos.aeroportos (
    id VARCHAR(50) PRIMARY KEY,
    codigo VARCHAR(3) NOT NULL UNIQUE,
    nome_aero VARCHAR(255) NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    estado VARCHAR(2) NOT NULL
);

CREATE TABLE IF NOT EXISTS voos.voos (
    id VARCHAR(50) PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    data_hora TIMESTAMP NOT NULL,
    origem VARCHAR(3) NOT NULL,
    destino VARCHAR(3) NOT NULL,
    valor_passagem DECIMAL(10,2) NOT NULL,
    quantidade_poltronas INTEGER NOT NULL,
    poltronas_ocupadas INTEGER NOT NULL,
    FOREIGN KEY (origem) REFERENCES voos.aeroportos(codigo),
    FOREIGN KEY (destino) REFERENCES voos.aeroportos(codigo)
);

-- Create tables in reservas schema
CREATE TABLE IF NOT EXISTS reservas.reservas (
    id VARCHAR(50) PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    data_hora_reserva TIMESTAMP NOT NULL,
    cliente_id VARCHAR(50) NOT NULL,
    voo_id VARCHAR(50) NOT NULL,
    valor_gasto DECIMAL(10,2) NOT NULL,
    milhas_utilizadas INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (voo_id) REFERENCES voos.voos(id),
    FOREIGN KEY (cliente_id) REFERENCES cliente.clientes(id)
);

-- Create tables in funcionario schema
CREATE TABLE IF NOT EXISTS funcionario.funcionario (
	id VARCHAR(50) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(20) NOT NULL
);

-- Insert data into funcionario.funcionario
INSERT INTO funcionario.funcionario (id, nome,cpf, email, telefone) VALUES 
('100', 'Funca Silva', '49564663075', 'funca@email.com', '41777777777'),
('101', 'Nonono Doe', '64501850078', 'nonono@email.com', '415555555555');  
	
-- Insert data into autenticacao.login
INSERT INTO autenticacao.login (id, email, senha) VALUES
('99ff', 'rqreewrwq@email.com', 'senha');

-- Insert data into cliente.clientes
INSERT INTO cliente.clientes (id, nome, email, cpf, telefone, tipo) VALUES
('1', 'é o testas nao tem como', 'rqreewrwq@email.com', '00000000000', '1234567891', 'C'),
('2', 'cliente2', 'cliente2@email.com', '99999999999', '1234587891', 'C'),
('3', 'cliente3', 'cliente3@email.com', '88888888888', '1234567891', 'C');

-- Insert data into cliente.milhas
INSERT INTO cliente.milhas (id, cliente_id, saldo) VALUES
('1', '1', 65350),
('2', '2', 5),
('3', '3', 550000);

-- Insert data into cliente.endereco
INSERT INTO cliente.endereco (id, rua, numero, complemento, cidade, estado, cep) VALUES
('90bd', 'rua', 'teste', 'complementas', 'cidade', 'estado', '80000-000'),
('e8e2', 'rua', 'teste', 'complementas', 'cidade', 'estado', '80000-000'),
('de1a', 'rua', '989', 'complementas', 'cidade', 'estado', '80000-000'),
('1e40', 'rua', '989', 'complementas', 'cidade', 'estado', '80000-000'),
('177c', 'rua', '989', 'complementas', 'cidade', 'estado', '80000-000'),
('6a02', 'rua', '989', 'complementas', 'cidade', 'estado', '80000-000'),
('da7f', 'rua', '989', 'complementas', 'cidade', 'estado', '80000-000');

-- Insert data into voos.aeroportos
INSERT INTO voos.aeroportos (id, codigo, nome_aero, cidade, estado) VALUES
('8297', 'CWB', 'Aeroporto Afonso Pena', 'Curitiba', 'PR'),
('2377', 'GRU', 'Aeroporto Internacional de São Paulo', 'Guarulhos', 'SP'),
('6d62', 'BSB', 'Aeroporto Internacional de Brasília', 'Brasília', 'DF'),
('78ce', 'GIG', 'Aeroporto Internacional do Galeão', 'Rio de Janeiro', 'RJ');

-- Insert data into voos.voos
INSERT INTO voos.voos (id, codigo, data_hora, origem, destino, valor_passagem, quantidade_poltronas, poltronas_ocupadas) VALUES
('0fa0', 'XYZ1234', '2024-12-15 14:00:00', 'CWB', 'GRU', 1090.50, 200, 150),
('7ec2', 'ABC5678', '2024-12-12 10:30:00', 'BSB', 'CWB', 750.20, 180, 130),
('b714', 'DEF9101', '2024-12-01 08:00:00', 'CWB', 'GIG', 950.00, 150, 50),
('ccfd', 'GHI2345', '2024-12-03 15:30:00', 'GRU', 'BSB', 1200.75, 220, 180),
('0c42', 'JKL6789', '2024-12-25 20:00:00', 'GIG', 'CWB', 1000.60, 170, 90);

-- Insert data into reservas.reservas
INSERT INTO reservas.reservas (id, codigo, data_hora_reserva, cliente_id, voo_id, valor_gasto, milhas_utilizadas, status) VALUES
('1cec', 'RES1234', '2024-09-15 14:00:00', '1', '0fa0', 1090.50, 3200, 'Aguardando check-in'),
('f4cd', 'RES5678', '2024-12-12 10:30:00', '1', '7ec2', 750.20, 2500, 'Voo realizado'),
('b9c1', 'RRW332', '2024-11-07 04:07:02.526', '3', '0fa0', 1090.50, 0, 'Aguardando check-in'),
('4a5d', 'NRX041', '2024-11-07 04:19:50.218', '3', '0fa0', 1090.50, 0, 'Aguardando check-in'),
('5e78', 'FUB638', '2024-11-07 04:20:24.892', '3', '0fa0', 1090.50, 0, 'Aguardando check-in'),
('c2be', 'ZDP617', '2024-11-07 04:21:15.387', '1', '0fa0', 1090.50, 100, 'Aguardando check-in'),
('7fcd', 'MTP686', '2024-11-07 04:22:30.503', '1', '0c42', 3001.80, 900, 'Aguardando check-in'),
('0bae', 'KRV957', '2024-11-07 04:24:00.154', '1', 'b714', 1900.00, 2, 'Aguardando check-in'),
('53c5', 'XVL904', '2024-11-07 04:30:27.979', '1', '0fa0', 2181.00, 100, 'Aguardando check-in'),
('3a33', 'ZFJ143', '2024-11-07 04:31:09.782', '1', 'b714', 950.00, 100, 'Aguardando check-in'),
('6b10', 'BGH499', '2024-11-07 04:31:41.124', '1', 'ccfd', 1200.75, 200, 'Aguardando check-in'),
('c550', 'QVX601', '2024-11-07 04:32:33.880', '1', '7ec2', 750.20, 5, 'Aguardando check-in'),
('13c9', 'TWZ403', '2024-11-07 04:35:58.792', '1', '7ec2', 750.20, 1, 'Aguardando check-in');

-- Insert data into cliente.transacoes_milhas
INSERT INTO cliente.transacoes_milhas (id, cliente_id, data_hora, operacao, saldo, descricao) VALUES
('7f6f', '1', '2024-05-11 12:15:00', 12450, 12450, 'compra de milhas'),
('db5b', '1', '2024-06-11 23:35:00', 4000, 16450, 'compra de milhas'),
('2b03', '1', '2024-08-11 10:35:00', 300, 16750, 'compra de milhas'),
('94db', '1', '2024-09-12 10:30:00', -2500, 14250, 'compra de passagem'),
('b585', '1', '2024-09-15 14:00:00', -3200, 11050, 'compra de passagem'),
('aaa4', '2', '2024-05-11 12:15:00', 1245045454, 1245045454, 'compra de milhas'),
('S2zYlg4', '421', '2023-05-11 12:15:00', 124, 0, 'compra de milhas'),
('T3PL009', 'ars', '2024-11-01 20:22:17.621', 80, 0, 'compra de milhas'),
('S19Qe6M', '3', '2024-05-11 12:15:00', 12450, 12450, 'compra de milhas'),
('Bsx17Sl', '3', '2024-08-11 10:35:00', 300, 16750, 'compra de milhas'),
('WEqByxw', '3', '2024-09-12 10:30:00', -2500, 14250, 'compra de reserva'),
('UNaooF1', '3', '2024-09-15 14:00:00', -3200, 11050, 'compra de reserva'),
('AFDDAD', '3', '2024-09-15 14:04:00', 538950, 550000, 'compra de milhas'),
('2Kr9joj', '3', '2024-11-07 22:23:06.644', 8555, 558555, 'compra de milhas'),
('0RQUHOU', '1', '2024-11-08 20:11:49.276', 50000, 61050, 'compra de milhas'),
('asfasff', '1', '2024-12-15 14:00:00', -3200, 57850, 'compra de reserva'),
('asfgtas', '1', '2024-12-12 10:30:00', -2500, 55350, 'compra de reserva'),
('ThDL3zA', '1', '2024-11-09 13:35:40.318', 10000, 65350, 'compra de milhas');


