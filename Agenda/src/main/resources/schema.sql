CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,  
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS contatos (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    usuario_id INT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    endereco VARCHAR(255),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS telefones (
    id INT AUTO_INCREMENT PRIMARY KEY,  
    contato_id INT NOT NULL,
    numero VARCHAR(20) NOT NULL,
    FOREIGN KEY (contato_id) REFERENCES contatos(id)
);