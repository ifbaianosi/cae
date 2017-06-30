  
CREATE TABLE responsavel (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    contato VARCHAR(20) NOT NULL,
    contato_whatsapp BOOLEAN default false,
    contato2 VARCHAR(20) NOT NULL,
    contato2_whatsapp BOOLEAN default false,
    email VARCHAR(80)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
