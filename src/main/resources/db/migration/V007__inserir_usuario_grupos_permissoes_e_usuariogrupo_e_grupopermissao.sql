INSERT INTO usuario (codigo, nome, email, senha, ativo) VALUES
(1, 'CAE', 'cae@si.ifbaiano.edu.br', '$2a$10$5ct2yU3JsLkO40HDDYTjWuTNjMWxCG7XZfObfu0yXqoUgZII6VEce', true);

INSERT INTO grupo (codigo, nome) VALUES
(1, 'CAE'),
(2, 'DA'),
(3, 'Assistente de alunos'),
(4, 'Vigilante');

INSERT INTO permissao (codigo, nome) VALUES
(1, 'ROLE_PESQUISAR_ALUNO'),
(2, 'ROLE_NOVO_ALUNO'),
(3, 'ROLE_EDITAR_ALUNO'),
(4, 'ROLE_SALVAR_ALUNO'),
(5, 'ROLE_EXCLUIR_ALUNO'),
(6, 'ROLE_ATUALIZAR_SERIE_ALUNO'),
(7, 'ROLE_CONSULTAR_DADOS_ALUNO'),
(8, 'ROLE_CONSULTAR_HISTORICO_ALUNO'),

(9, 'ROLE_PESQUISAR_USUARIO'),
(10,'ROLE_NOVO_USUARIO'),
(11,'ROLE_EDITAR_USUARIO'),
(12,'ROLE_SALVAR_USUARIO'),
(13,'ROLE_EXCLUIR_USUARIO'),
(14,'ROLE_ATUALIZAR_STATUS_USUARIO'),

(15,'ROLE_PESQUISAR_OCORRENCIA'),
(16,'ROLE_NOVA_OCORRENCIA'),
(17,'ROLE_EDITAR_OCORRENCIA'),
(18,'ROLE_SALVAR_OCORRENCIA'),
(19,'ROLE_EXCLUIR_OCORRENCIA'),

(20,'ROLE_NOVO_ENCAMINHAMENTO'),
(21,'ROLE_EDITAR_ENCAMINHAMENTO'),
(22,'ROLE_SALVAR_ENCAMINHAMENTO'),
(23,'ROLE_EXCLUIR_ENCAMINHAMENTO');

INSERT INTO usuario_grupo (codigo_usuario, codigo_grupo) VALUES 
(1, 1);

INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES 
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13),
(1, 14),
(1, 15),
(1, 16),
(1, 17),
(1, 18),
(1, 19),
(1, 20),
(1, 21),
(1, 22),
(1, 23),

(2, 1),
(2, 7),
(2, 8),
(2, 9),
(2, 15),

(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 6),
(3, 7),
(3, 16),
(3, 18),

(4, 7);

