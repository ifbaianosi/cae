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
    content_type VARCHAR(100),
    codigo_contato BIGINT(20) NOT NULL,
    codigo_curso BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_contato) REFERENCES contato(codigo),
    FOREIGN KEY (codigo_curso) REFERENCES curso(codigo)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
