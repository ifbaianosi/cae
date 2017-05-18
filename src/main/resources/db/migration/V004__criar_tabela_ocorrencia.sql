
CREATE TABLE ocorrencia (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    data_registro DATETIME NOT NULL,
    data_ocorrido DATETIME NOT NULL,
    descricao VARCHAR(254) NOT NULL,
    local_ocorrencia VARCHAR(200),
    codigo_aluno BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_aluno) REFERENCES aluno(codigo)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
