INSERT INTO permissao (codigo, nome) VALUES
(26,'ROLE_MENU_USUARIOS'),
(27,'ROLE_MENU_OCORRENCIAS');


INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES 

(1, 26),
(1, 27),

(2, 27),

(3, 27);

