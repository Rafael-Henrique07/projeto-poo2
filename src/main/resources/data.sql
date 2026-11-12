INSERT IGNORE INTO categoria (id, nome, descricao) VALUES
    (1, 'Sedã', 'Carros de passeio com porta-malas separado'),
    (2, 'Cupê', 'Carros de duas portas com perfil esportivo'),
    (3, 'Conversível', 'Carros com capota removível'),
    (4, 'Picape', 'Veículos com caçamba'),
    (5, 'Esportivo', 'Carros voltados ao desempenho'),
    (6, 'Utilitário', 'Veículos de carga e transporte');

INSERT IGNORE INTO usuario (id, nome, email, senha_hash, telefone, cidade, uf, perfil, data_cadastro) VALUES
    (1, 'Ana Anunciante', 'anunciante@demo.com', '$2a$10$VOvo2Jfzzi66bZtklASRXOjboT4SfL6exfFvUIRu/yHDMhJ4V0YSe', '43999990001', 'Cornélio Procópio', 'PR', 'COMUM', NOW()),
    (2, 'Caio Comprador', 'comprador@demo.com', '$2a$10$VOvo2Jfzzi66bZtklASRXOjboT4SfL6exfFvUIRu/yHDMhJ4V0YSe', '43999990002', 'Londrina', 'PR', 'COMUM', NOW()),
    (3, 'Administrador', 'admin@demo.com', '$2a$10$VOvo2Jfzzi66bZtklASRXOjboT4SfL6exfFvUIRu/yHDMhJ4V0YSe', '43999990003', 'Cornélio Procópio', 'PR', 'ADMINISTRADOR', NOW());

INSERT IGNORE INTO veiculo (id, categoria_id, marca, modelo, ano_fabricacao, ano_modelo, quilometragem, cor, motor, potencia_cv, combustivel, cambio, conservacao) VALUES
    (1, 2, 'Chevrolet', 'Opala SS', 1976, 1976, 92000, 'Preto', '4.1 seis cilindros', 148, 'GASOLINA', 'MANUAL', 'RESTAURADO'),
    (2, 1, 'Volkswagen', 'Fusca 1300', 1972, 1972, 78000, 'Azul', '1.3 boxer', 46, 'GASOLINA', 'MANUAL', 'ORIGINAL'),
    (3, 5, 'Ford', 'Maverick GT', 1975, 1975, 110000, 'Laranja', '5.0 V8', 199, 'GASOLINA', 'MANUAL', 'PREPARADO'),
    (4, 5, 'Puma', 'GTE', 1974, 1974, 65000, 'Vermelho', '1.6 boxer', 70, 'GASOLINA', 'MANUAL', 'RESTAURADO'),
    (5, 6, 'Volkswagen', 'Kombi Standard', 1969, 1969, 134000, 'Branco', '1.5 boxer', 52, 'GASOLINA', 'MANUAL', 'PARA_RESTAURAR'),
    (6, 4, 'Chevrolet', 'C10', 1968, 1968, 121000, 'Verde', '4.3 seis cilindros', 151, 'GASOLINA', 'MANUAL', 'ORIGINAL');

INSERT IGNORE INTO anuncio (id, anunciante_id, veiculo_id, titulo, descricao, preco, status, selo, data_publicacao, data_atualizacao) VALUES
    (1, 1, 1, 'Opala SS 1976 restaurado', 'Cupê com bancos reclinados de época e painel completo. Documentação em dia.', 145000.00, 'ATIVO', 'CLASSICO', NOW(), NOW()),
    (2, 1, 2, 'Fusca 1300 de segundo dono', 'Veículo mantido em garagem e com pintura original.', 42000.00, 'ATIVO', 'ANTIGO', NOW(), NOW()),
    (3, 1, 3, 'Maverick GT V8 preparado', 'Motor retificado, suspensão refeita e escapamento esportivo.', 189000.00, 'ATIVO', 'PREPARADO', NOW(), NOW()),
    (4, 1, 4, 'Puma GTE em fibra revisada', 'Carroceria em fibra revisada e interior refeito conforme o original.', 128000.00, 'ATIVO', 'CLASSICO', NOW(), NOW()),
    (5, 1, 5, 'Kombi Standard para restaurar', 'Boa base para restauração. Motor funcionando e chassi sem trincas.', 35000.00, 'ATIVO', NULL, NOW(), NOW()),
    (6, 1, 6, 'C10 original com caçamba de madeira', 'Picape de uso em fazenda, com caçamba em madeira refeita.', 76000.00, 'ATIVO', 'ANTIGO', NOW(), NOW());
