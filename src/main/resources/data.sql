INSERT INTO usuario (id, nome, email, senha, habilitado, versao, data_criacao, data_ultima_modificacao)
SELECT 0, 'Luiza', 'luiza@gmail.com', '$2a$10$CSr6UGs410gkpwhBmPpp4uWtse5P9R1WSusc7XrbqaT77xYilfRTW', true, 0, CURRENT_DATE, CURRENT_DATE
    WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE email = 'luiza@gmail.com');

/*
    Senha da ADMIN = 123456
*/

INSERT INTO perfil (id, habilitado, nome)
SELECT 0, true, 'ADMIN'
    WHERE NOT EXISTS (SELECT 1 FROM perfil WHERE nome = 'ADMIN');

INSERT INTO usuario_roles (usuario_id, roles_id)
SELECT 0, 0
    WHERE NOT EXISTS (SELECT 1 FROM usuario_roles WHERE usuario_id = 0 AND roles_id = 0);