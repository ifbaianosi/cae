ALTER TABLE  ocorrencia CHANGE  codigo_aluno  codigo_aluno BIGINT(20) NULL;
UPDATE  ocorrencia SET  codigo_aluno = NULL WHERE  ocorrencia.codigo > 0;
ALTER TABLE ocorrencia DROP FOREIGN KEY ocorrencia_ibfk_1;
ALTER TABLE ocorrencia DROP codigo_aluno;