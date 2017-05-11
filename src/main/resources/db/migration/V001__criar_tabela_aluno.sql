  CREATE TABLE aluno (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    matricula VARCHAR(7) NOT NULL,
    email VARCHAR (80),
    sexo VARCHAR (15) NOT NULL,
    data_nascimento DATE NOT NULL,
    apartamento INTEGER,
    identificacao VARCHAR(20) NOT NULL,
    serie_turma VARCHAR(20),
    foto VARCHAR(200),
    content_type VARCHAR(100)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
