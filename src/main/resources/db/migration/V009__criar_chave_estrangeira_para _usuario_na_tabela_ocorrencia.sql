ALTER TABLE ocorrencia ADD  codigo_usuario BIGINT(20) NOT NULL ;
ALTER TABLE  ocorrencia ADD INDEX (codigo_usuario) ;
ALTER TABLE  ocorrencia ADD CONSTRAINT  usuario_ibfk_1 FOREIGN KEY (  codigo_usuario ) REFERENCES  trino_desenv.usuario (codigo) ON DELETE RESTRICT ON UPDATE RESTRICT ;
