-- Criar a tabela EstadoReserva
CREATE TABLE IF NOT EXISTS EstadoReserva (
    id SERIAL PRIMARY KEY,
    codigo_estado VARCHAR(20) NOT NULL UNIQUE,
    sigla_estado VARCHAR(5) NOT NULL UNIQUE,
    descricao_estado VARCHAR(100) NOT NULL
);

-- Inserir os estados na tabela EstadoReserva
INSERT INTO EstadoReserva (codigo_estado, sigla_estado, descricao_estado) VALUES
    ('AGUARD_CHECKIN', 'CHKIN', 'Aguardando Check-in'),
    ('CHECKED_IN', 'CIN', 'Check-in Realizado'),
    ('EMBARCADO', 'EMB', 'Passageiro Embarcado'),
    ('REALIZADO', 'REAL', 'Viagem Realizada'),
    ('NAO_REALIZADO', 'NREAL', 'Viagem Não Realizada'),
    ('CANCELADO_VOO', 'CVOO', 'Voo Cancelado'),
    ('CANCELADO_RESERVA', 'CRES', 'Reserva Cancelada')
ON CONFLICT (codigo_estado) DO NOTHING;
