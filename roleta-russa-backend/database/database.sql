use roleta_russa;

drop table usuario;

CREATE TABLE if not exists usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    pontos INT DEFAULT 0
);

CREATE TABLE if not exists novidade (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT NOT NULL,
    tipo ENUM('BUG_FIX', 'NOVA_FUNCAO', 'MELHORIA', 'AVISO') NOT NULL,
    autor VARCHAR(100) NOT NULL,
    data_publicacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    versao VARCHAR(20),
    ativo BOOLEAN DEFAULT TRUE
);

select * from usuario;
select * from novidade;

INSERT INTO novidade
(titulo, descricao, tipo, autor, versao)
VALUES
(
    'Correção de Login',
    'Corrigido erro que impedia alguns usuários de acessar a conta.',
    'BUG_FIX',
    'João Henrique',
    'v1.0.2'
),
(
    'Sistema de Ranking',
    'Adicionado ranking global dos jogadores.',
    'NOVA_FUNCAO',
    'João Henrique',
    'v1.1.0'
);