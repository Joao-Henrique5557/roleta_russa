-- Criando a tabela de usuários
CREATE TABLE IF NOT EXISTS usuarios (    
    id SERIAL PRIMARY KEY,    
    nome VARCHAR(100) NOT NULL,    
    email VARCHAR(150) NOT NULL UNIQUE,    
    senha VARCHAR(255) NOT NULL,    
    pontos INT DEFAULT 0
);

-- Criando o tipo ENUM para a tabela novidade
CREATE TYPE tipo_novidade AS ENUM ('BUG FIX', 'NOVA FUNCAO', 'MELHORIA', 'AVISO');

-- Criando a tabela de novidades
CREATE TABLE IF NOT EXISTS novidade (    
    id SERIAL PRIMARY KEY,    
    titulo VARCHAR(100) NOT NULL,    
    descricao TEXT NOT NULL,    
    tipo tipo_novidade NOT NULL
);
