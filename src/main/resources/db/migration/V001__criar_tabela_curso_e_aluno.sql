 CREATE TABLE curso (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    sigla VARCHAR(80) NOT NULL,
    tipo_curso VARCHAR(40) NOT NULL
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE aluno (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(80) NOT NULL,
    matricula VARCHAR(7) NOT NULL,
    regime VARCHAR(20) NOT NULL,
    codigo_curso BIGINT(20) NOT NULL,
    nome_social VARCHAR(80),
    sexo VARCHAR (15),
    alojamento VARCHAR(25),
    apartamento INTEGER,
    email VARCHAR (80),
    serie_turma VARCHAR(20),
    data_nascimento DATE,
    foto VARCHAR(200),
    content_type VARCHAR(100),
    contato VARCHAR(20),
    whatsapp BOOLEAN default false,
    saida BOOLEAN default false,
    status VARCHAR(30),
    FOREIGN KEY (codigo_curso) REFERENCES curso(codigo)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
