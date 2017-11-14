INSERT INTO grupo (codigo, nome, descricao) VALUES (5, 'ADMNISTRADOR', 'Administrador do sistema');

UPDATE  usuario_grupo SET  codigo_grupo =  5 WHERE  usuario_grupo.codigo_usuario =1 AND  usuario_grupo.codigo_grupo =1;