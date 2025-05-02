CREATE TABLE coindesk (
    code VARCHAR(10) PRIMARY KEY,
    symbol VARCHAR(10),
    rate VARCHAR(50),
    description VARCHAR(255),
    rate_float DOUBLE
);