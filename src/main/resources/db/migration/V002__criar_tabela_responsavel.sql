  
CREATE TABLE responsavel (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    codigo_contato BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_contato) REFERENCES contato(codigo)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
