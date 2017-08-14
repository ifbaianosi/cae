
CREATE TABLE ocorrencia_aluno (
    codigo_ocorrencia BIGINT(20) NOT NULL,
    codigo_aluno BIGINT(20) NOT NULL,
    PRIMARY KEY (codigo_ocorrencia, codigo_aluno),
    FOREIGN KEY (codigo_ocorrencia) REFERENCES ocorrencia(codigo),
    FOREIGN KEY (codigo_aluno) REFERENCES aluno(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;