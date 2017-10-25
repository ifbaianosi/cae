INSERT INTO permissao (codigo, nome) VALUES
(41,'ROLE_DASHBOARD_NUMEROS_DO_SISTEMA');


INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES 
(1, 41),
(2, 41),
(3, 41);

