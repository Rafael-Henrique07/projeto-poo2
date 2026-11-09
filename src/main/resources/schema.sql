CREATE TABLE IF NOT EXISTS usuario (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    nome           VARCHAR(100) NOT NULL,
    email          VARCHAR(150) NOT NULL,
    senha_hash     VARCHAR(100) NOT NULL,
    telefone       VARCHAR(20)  NOT NULL,
    cidade         VARCHAR(100) NOT NULL,
    uf             CHAR(2)      NOT NULL,
    perfil         ENUM('COMUM','ADMINISTRADOR') NOT NULL DEFAULT 'COMUM',
    ativo          BOOLEAN      NOT NULL DEFAULT TRUE,
    data_cadastro  DATETIME     NOT NULL,
    CONSTRAINT uk_usuario_email UNIQUE (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS categoria (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    nome       VARCHAR(60)  NOT NULL,
    descricao  VARCHAR(255) NULL,
    CONSTRAINT uk_categoria_nome UNIQUE (nome)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS veiculo (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    categoria_id    INT         NOT NULL,
    marca           VARCHAR(60) NOT NULL,
    modelo          VARCHAR(80) NOT NULL,
    ano_fabricacao  INT         NOT NULL,
    ano_modelo      INT         NOT NULL,
    quilometragem   INT         NOT NULL,
    cor             VARCHAR(40) NOT NULL,
    motor           VARCHAR(60) NOT NULL,
    potencia_cv     INT         NOT NULL,
    combustivel     ENUM('GASOLINA','ETANOL','FLEX','DIESEL') NOT NULL,
    cambio          ENUM('MANUAL','AUTOMATICO') NOT NULL,
    conservacao     ENUM('ORIGINAL','RESTAURADO','PREPARADO','PARA_RESTAURAR') NOT NULL,
    CONSTRAINT fk_veiculo_categoria FOREIGN KEY (categoria_id) REFERENCES categoria (id),
    CONSTRAINT ck_veiculo_km CHECK (quilometragem >= 0),
    CONSTRAINT ck_veiculo_potencia CHECK (potencia_cv > 0),
    INDEX idx_veiculo_marca (marca)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS anuncio (
    id                INT AUTO_INCREMENT PRIMARY KEY,
    anunciante_id     INT           NOT NULL,
    veiculo_id        INT           NOT NULL,
    titulo            VARCHAR(120)  NOT NULL,
    descricao         VARCHAR(2000) NOT NULL,
    preco             DECIMAL(12,2) NOT NULL,
    status            ENUM('ATIVO','PAUSADO','VENDIDO','REMOVIDO') NOT NULL DEFAULT 'ATIVO',
    selo              ENUM('ANTIGO','CLASSICO','PREPARADO') NULL,
    data_publicacao   DATETIME      NOT NULL,
    data_atualizacao  DATETIME      NOT NULL,
    CONSTRAINT uk_anuncio_veiculo UNIQUE (veiculo_id),
    CONSTRAINT fk_anuncio_anunciante FOREIGN KEY (anunciante_id) REFERENCES usuario (id),
    CONSTRAINT fk_anuncio_veiculo FOREIGN KEY (veiculo_id) REFERENCES veiculo (id),
    CONSTRAINT ck_anuncio_preco CHECK (preco > 0),
    INDEX idx_anuncio_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS foto (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    anuncio_id       INT          NOT NULL,
    caminho_arquivo  VARCHAR(255) NOT NULL,
    ordem            INT          NOT NULL,
    principal        BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_foto_anuncio FOREIGN KEY (anuncio_id) REFERENCES anuncio (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS negociacao (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    anuncio_id    INT      NOT NULL,
    comprador_id  INT      NOT NULL,
    data_inicio   DATETIME NOT NULL,
    status        ENUM('ABERTA','ENCERRADA') NOT NULL DEFAULT 'ABERTA',
    CONSTRAINT uk_negociacao UNIQUE (anuncio_id, comprador_id),
    CONSTRAINT fk_negociacao_anuncio FOREIGN KEY (anuncio_id) REFERENCES anuncio (id),
    CONSTRAINT fk_negociacao_comprador FOREIGN KEY (comprador_id) REFERENCES usuario (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS proposta (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    negociacao_id  INT           NOT NULL,
    valor          DECIMAL(12,2) NOT NULL,
    data_proposta  DATETIME      NOT NULL,
    status         ENUM('PENDENTE','ACEITA','RECUSADA','CANCELADA') NOT NULL DEFAULT 'PENDENTE',
    CONSTRAINT fk_proposta_negociacao FOREIGN KEY (negociacao_id) REFERENCES negociacao (id) ON DELETE CASCADE,
    CONSTRAINT ck_proposta_valor CHECK (valor > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS mensagem (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    negociacao_id  INT           NOT NULL,
    remetente_id   INT           NOT NULL,
    conteudo       VARCHAR(1000) NOT NULL,
    data_envio     DATETIME      NOT NULL,
    lida           BOOLEAN       NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_mensagem_negociacao FOREIGN KEY (negociacao_id) REFERENCES negociacao (id) ON DELETE CASCADE,
    CONSTRAINT fk_mensagem_remetente FOREIGN KEY (remetente_id) REFERENCES usuario (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
