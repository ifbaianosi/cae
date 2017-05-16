  
CREATE TABLE responsavel (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    contato VARCHAR(20) NOT NULL,
    whatsapp BOOLEAN default false
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
