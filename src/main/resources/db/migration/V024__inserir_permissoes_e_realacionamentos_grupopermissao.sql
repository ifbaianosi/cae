INSERT INTO permissao (codigo, nome) VALUES
(39,'ROLE_PESQUISAR_ALUNO_POR_NOMEOUMATRICULA'),
(40,'ROLE_PESQUISAR_ALUNO_POR_MATRICULA');


INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES 
(1, 39),
(1, 40),
(2, 39),
(2, 40),
(3, 39),
(3, 40);

