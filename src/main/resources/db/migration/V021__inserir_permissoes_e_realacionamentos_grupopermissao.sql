INSERT INTO permissao (codigo, nome) VALUES
(34,'ROLE_VER_GRAFICO_OCORRENCIAS_MES'),
(35,'ROLE_VER_OCORRENCIAS_POR_LOCAL'),
(36,'ROLE_VER_OCORRENCIAS_POR_USUARIO'),
(37,'ROLE_VER_OCORRENCIAS_POR_ALUNO');


INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES 
(1, 34),
(1, 35),
(1, 36),
(1, 37),

(2, 34),
(2, 35),
(2, 36),
(2, 37),

(3, 34),
(3, 35),
(3, 37);

