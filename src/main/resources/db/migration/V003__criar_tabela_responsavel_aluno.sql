  
CREATE TABLE responsavel_aluno (
    codigo_responsavel BIGINT(20) NOT NULL,
    codigo_aluno BIGINT(20) NOT NULL,
    parentesco VARCHAR(20) NOT NULL,
    PRIMARY KEY (codigo_responsavel, codigo_aluno),
    FOREIGN KEY (codigo_responsavel) REFERENCES responsavel(codigo),
    FOREIGN KEY (codigo_aluno) REFERENCES aluno(codigo)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
