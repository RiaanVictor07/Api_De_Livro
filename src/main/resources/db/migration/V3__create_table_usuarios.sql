CREATE TABLE usuarios (
                          id BIGSERIAL PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL,
                          login VARCHAR(255) NOT NULL UNIQUE,
                          senha VARCHAR(255) NOT NULL
);