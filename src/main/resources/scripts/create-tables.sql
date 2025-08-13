
-- DROP TABLE bao.produtos_ingredientes;
-- DROP TABLE bao.ingredientes;
-- DROP TABLE bao.produtos_caracteristicas;
-- DROP TABLE bao.caracteristicas;
-- DROP TABLE bao.produtos;
-- DROP TABLE bao.usuarios;


CREATE TABLE bao.usuarios (
	id uuid NOT NULL,
	nome varchar(255) NOT NULL,
	email varchar(255) NULL,
	telefone varchar(255) NULL,
	login varchar(255) NOT NULL,
	senha varchar(255) NOT NULL,
	permissao varchar(255) NOT NULL,
	CONSTRAINT usuario_pkey PRIMARY KEY (id),
	CONSTRAINT usuario_usuario_key UNIQUE (login)
);

CREATE TABLE bao.produtos (
	id uuid NOT NULL,
	nome varchar(255) NOT NULL,
	categoria varchar(255) NOT NULL,
	descricao varchar(255) NULL,
	preco numeric(12, 2) NOT NULL,
	disponivel bool NOT NULL,
	imagem bytea NULL,
	imagemtipo varchar(255) NULL,
	CONSTRAINT produto_pkey PRIMARY KEY (id)
);

CREATE TABLE bao.caracteristicas (
	id uuid NOT NULL,
	nome varchar(255) NOT NULL,
	CONSTRAINT caracteristicas_pkey PRIMARY KEY (id)
);

CREATE TABLE bao.produtos_caracteristicas (
	produtos_id uuid NOT NULL,
	caracteristicas_id uuid NOT NULL,
	CONSTRAINT produtos_caracteristicas_pkey PRIMARY KEY (produtos_id, caracteristicas_id),
	CONSTRAINT produtos_caracteristicas_caracteristicas_id_fkey FOREIGN KEY (caracteristicas_id) REFERENCES bao.caracteristicas(id),
	CONSTRAINT produtos_caracteristicas_produtos_id_fkey FOREIGN KEY (produtos_id) REFERENCES bao.produtos(id)
);

CREATE TABLE bao.ingredientes (
	id uuid NOT NULL,
	nome varchar(255) NOT NULL,
	CONSTRAINT ingredientes_pkey PRIMARY KEY (id)
);

CREATE TABLE bao.produtos_ingredientes (
	produtos_id uuid NOT NULL,
	ingredientes_id uuid NOT NULL,
	quantidade int4 NULL,
	unidade varchar(50) NULL,
	CONSTRAINT produtos_ingredientes_pkey PRIMARY KEY (produtos_id, ingredientes_id),
	CONSTRAINT produtos_ingredientes_ingredientes_id_fkey FOREIGN KEY (ingredientes_id) REFERENCES bao.ingredientes(id),
	CONSTRAINT produtos_ingredientes_produtos_id_fkey FOREIGN KEY (produtos_id) REFERENCES bao.produtos(id)
);

