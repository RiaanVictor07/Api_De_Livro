CREATE TABLE editoras (
                          id BIGSERIAL PRIMARY KEY,
                          nome VARCHAR(255) NOT NULL
);

CREATE TABLE autoras (
                         id BIGSERIAL PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL
);

CREATE TABLE livros (
                        id BIGSERIAL PRIMARY KEY,
                        titulo VARCHAR(255) NOT NULL,
                        ano_publicacao INTEGER NOT NULL,
                        sinopse TEXT,
                        genero VARCHAR(50) NOT NULL,
                        editora_id BIGINT,
                        autora_id BIGINT,
                        CONSTRAINT fk_livro_editora FOREIGN KEY (editora_id) REFERENCES editoras(id),
                        CONSTRAINT fk_livro_autora FOREIGN KEY (autora_id) REFERENCES autoras(id)
);