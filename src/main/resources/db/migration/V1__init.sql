CREATE TABLE pedidoitem (
    codigo BIGINT NOT NULL AUTO_INCREMENT,
    pedidocodigo BIGINT NULL,
    produtocodigo BIGINT NULL,
    quantidade INT NULL,
    precounitario DECIMAL(10,2) NULL,
    precototal DECIMAL(10,2) NULL,
    PRIMARY KEY (codigo)
);

CREATE TABLE pedidos (
    codigo BIGINT NOT NULL AUTO_INCREMENT,
    usuariocodigo BIGINT NULL,
    status VARCHAR(255) NULL,
    valortotal DECIMAL(10,2) NULL,
    datahorasolicitacao DATETIME NULL,
    tempototalpreparo TIME NULL,
    PRIMARY KEY (codigo)
);

ALTER TABLE pedidoitem
    ADD CONSTRAINT FK_PEDIDOITEM_ON_PEDIDOCODIGO FOREIGN KEY (pedidocodigo) REFERENCES pedidos (codigo);
