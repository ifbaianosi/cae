  
CREATE TABLE responsavel_aluno (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    codigo_responsavel BIGINT(20) NOT NULL,
    codigo_aluno BIGINT(20) NOT NULL,
    parentesco VARCHAR(20) NOT NULL,
    FOREIGN KEY (codigo_responsavel) REFERENCES responsavel(codigo),
    FOREIGN KEY (codigo_aluno) REFERENCES aluno(codigo)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
