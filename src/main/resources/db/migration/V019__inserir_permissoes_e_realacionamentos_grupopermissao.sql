INSERT INTO permissao (codigo, nome) VALUES
(31,'ROLE_MENU_RELATORIOS'),
(32,'ROLE_RELATORIO_OCORRENCIAS'),
(33,'ROLE_IMPRIMIR_MEDIDA_DISCIPLINAR');


INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES 
(1, 31),
(1, 32),
(1, 33),

(2, 31),
(2, 32),
(2, 33);

