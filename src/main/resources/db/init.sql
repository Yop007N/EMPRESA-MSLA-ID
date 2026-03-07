-- Script de inicialización de base de datos
-- Exchange Rate Microservice

CREATE TABLE IF NOT EXISTS exchange_history (
    id               BIGSERIAL PRIMARY KEY,
    from_currency    VARCHAR(3)         NOT NULL,
    to_currency      VARCHAR(3)         NOT NULL,
    amount           DOUBLE PRECISION   NOT NULL,
    converted_amount DOUBLE PRECISION   NOT NULL,
    rate             DOUBLE PRECISION   NOT NULL,
    date             DATE               NOT NULL,
    created_at       TIMESTAMP          NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_exchange_history_date ON exchange_history (date);
CREATE INDEX IF NOT EXISTS idx_exchange_history_from_currency ON exchange_history (from_currency);
CREATE INDEX IF NOT EXISTS idx_exchange_history_to_currency ON exchange_history (to_currency);

-- Datos de ejemplo
INSERT INTO exchange_history (from_currency, to_currency, amount, converted_amount, rate, date, created_at)
VALUES
    ('USD', 'PEN', 100.0, 374.0,  3.74,   '2025-04-14', NOW()),
    ('PEN', 'USD', 500.0, 133.69, 0.2674, '2025-04-14', NOW()),
    ('USD', 'PEN', 250.0, 934.79, 3.739,  '2025-04-15', NOW());
