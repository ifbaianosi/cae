  CREATE TABLE curso (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

  CREATE TABLE contato (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    numero VARCHAR(20) NOT NULL,
    whatsapp BOOLEAN default false,
    numero_whatsapp VARCHAR(20)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;