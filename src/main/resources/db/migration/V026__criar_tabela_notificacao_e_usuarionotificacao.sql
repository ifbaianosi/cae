  
CREATE TABLE notificacao (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(180) NOT NULL,
    data DATETIME NOT NULL,
    uri VARCHAR(80) NOT NULL,
    tipo VARCHAR(50) NOT NULL
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_notificacao (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    codigo_usuario BIGINT(20) NOT NULL,
    codigo_notificacao BIGINT(20) NOT NULL,
    data_visualizacao DATETIME NOT NULL,
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
    FOREIGN KEY (codigo_notificacao) REFERENCES notificacao(codigo)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
