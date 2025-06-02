CREATE DATABASE solardragons DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE solardragons;

CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    administrador BOOLEAN DEFAULT FALSE,
    raiz BOOLEAN DEFAULT FALSE,
    senha VARCHAR(100) NOT NULL
);

INSERT INTO usuario (cpf, nome, email, senha, administrador, raiz)
VALUES ('777', 'ADM RAIZ', 'adm@solar.com', 'adm123', 1, 1);

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    cpf CHAR(11) NOT NULL,
    logradouro VARCHAR(100),
    numero VARCHAR(10),
    bairro VARCHAR(50),
    cidade VARCHAR(50),
    estado VARCHAR(2),
    cep VARCHAR(10),
    cpf_usuario CHAR(11) NOT NULL,
    CONSTRAINT unique_cliente_usuario UNIQUE (cpf, cpf_usuario),
    CONSTRAINT fk_usuario_cliente FOREIGN KEY (cpf_usuario) REFERENCES usuario(cpf) ON DELETE CASCADE
);

CREATE TABLE simulacao_energia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    valor_conta_reais DOUBLE NOT NULL,
    tarifa DOUBLE NOT NULL,
    consumo_estimado_kwh DOUBLE NOT NULL,
    geracao_estimada_kwh DOUBLE NOT NULL,
    economia_mensal DOUBLE NOT NULL,
    economia_anual DOUBLE NOT NULL,
    potencia_sistema_kw DOUBLE NOT NULL,
    quantidade_modulos INT NOT NULL,
    investimento_total DOUBLE NOT NULL,
    tempo_payback_meses INT NOT NULL,
    data_simulacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id) ON DELETE CASCADE
);



