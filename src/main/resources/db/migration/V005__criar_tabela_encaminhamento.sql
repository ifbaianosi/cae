
CREATE TABLE encaminhamento (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(250),
    tipo_encaminhamento VARCHAR(30) NOT NULL,
    data_encaminhamento DATETIME NOT NULL,
    codigo_ocorrencia BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_ocorrencia) REFERENCES ocorrencia(codigo)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
