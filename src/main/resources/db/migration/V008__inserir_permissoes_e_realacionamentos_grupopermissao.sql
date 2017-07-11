INSERT INTO permissao (codigo, nome) VALUES
(24,'ROLE_MENU_ALUNOS'),
(25,'ROLE_MENU_REGISTROS');


INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES 

(1, 24),
(1, 25),

(2, 24),
(2, 25),

(3, 24),
(3, 25);

