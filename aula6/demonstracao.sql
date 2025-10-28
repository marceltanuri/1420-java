-- ================================================================================= --
-- SCRIPT DE CRIAÇÃO E CONSULTA DE BANCO DE DADOS PARA UM LMS (SISTEMA DE GESTÃO DE APRENDIZAGEM)
-- ================================================================================= --

-- Apaga o banco de dados 'ada' se ele já existir, para garantir que começaremos do zero.
-- Útil durante o desenvolvimento para testar o script de criação várias vezes.
DROP DATABASE IF EXISTS ada;

-- Cria um novo banco de dados chamado 'ada'.
-- É o nosso "contêiner" para todas as tabelas, dados e configurações.
CREATE DATABASE ada;

-- Seleciona o banco de dados 'ada' para ser o banco de dados ativo.
-- Todos os comandos a seguir serão executados dentro dele.
USE ada;

-- ================================================================================= --
-- TABELA DE USUÁRIOS
-- Armazena informações sobre todos os usuários do sistema (alunos, professores, administradores).
-- ================================================================================= --
CREATE TABLE `usuarios` (
    -- 'usuario_id': Chave primária da tabela. É um número inteiro que se auto-incrementa
    -- a cada novo usuário, garantindo que cada um tenha um identificador único.
    `usuario_id` INT AUTO_INCREMENT PRIMARY KEY,

    -- 'nome': Armazena o nome do usuário. VARCHAR(100) limita o nome a 100 caracteres.
    -- 'NOT NULL' significa que este campo é obrigatório.
    `nome` VARCHAR(100) NOT NULL,

    -- 'email': Armazena o e-mail do usuário. É obrigatório ('NOT NULL') e único ('UNIQUE'),
    -- ou seja, não pode haver dois usuários com o mesmo e-mail.
    `email` VARCHAR(100) NOT NULL UNIQUE,

    -- 'senha_hash': Campo para armazenar a senha do usuário.
    -- IMPORTANTE: Em um sistema real, NUNCA guarde senhas em texto puro!
    -- O nome 'senha_hash' sugere que a senha deveria ser "hasheada" (criptografada)
    -- usando algoritmos como BCrypt ou SHA-256 antes de ser salva.
    `senha_hash` VARCHAR(255) NOT NULL,

    -- 'tipo_usuario': Define o papel do usuário no sistema.
    -- ENUM limita as opções a 'aluno', 'professor' ou 'admin'.
    `tipo_usuario` ENUM('aluno', 'professor', 'admin') NOT NULL,

    -- 'data_cadastro': Registra a data e hora em que o usuário foi criado.
    -- 'DEFAULT CURRENT_TIMESTAMP' preenche automaticamente com a data e hora atuais.
    `data_cadastro` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- 'ativo': Campo booleano (verdadeiro ou falso) para indicar se a conta do usuário está ativa.
    -- 'DEFAULT TRUE' define que todo novo usuário começa como ativo.
    `ativo` BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB; -- 'ENGINE=InnoDB' é o motor de armazenamento padrão do MySQL, que dá suporte a transações e chaves estrangeiras.

-- ================================================================================= --
-- INSERÇÃO DE DADOS DE EXEMPLO NA TABELA 'usuarios'
-- ================================================================================= --
INSERT INTO `usuarios` (`nome`, `email`, `senha_hash`, `tipo_usuario`) VALUES
    ('Admin Geral', 'admin@lms.com', 'senha_secreta_admin', 'admin'),
    ('Prof. Ana Silva', 'ana.silva@lms.com', 'senha_secreta_ana', 'professor'),
    ('Prof. Carlos Santos', 'carlos.santos@lms.com', 'senha_secreta_carlos', 'professor'),
    ('Prof. Laura Oliveira', 'laura.oliveira@lms.com', 'senha_secreta_laura', 'professor'),
    ('Aluno Teste 1', 'aluno1@lms.com', 'senha_secreta_aluno1', 'aluno'),
    ('Aluno Teste 2', 'aluno2@lms.com', 'senha_secreta_aluno2', 'aluno'),
    ('Aluno Teste 3', 'aluno3@lms.com', 'senha_secreta_aluno3', 'aluno');

-- ================================================================================= --
-- TABELA DE CURSOS
-- Armazena informações sobre os cursos oferecidos no LMS.
-- ================================================================================= --
CREATE TABLE `cursos` (
    -- 'curso_id': Chave primária da tabela, identificador único para cada curso.
    `curso_id` INT AUTO_INCREMENT PRIMARY KEY,

    -- 'titulo': O nome do curso. É um campo de texto obrigatório.
    `titulo` VARCHAR(255) NOT NULL,

    -- 'descricao': Uma descrição mais longa sobre o curso. TEXT permite textos longos.
    `descricao` TEXT,

    -- 'professor_id': Chave estrangeira que conecta o curso a um usuário (o professor).
    -- Este campo armazena o 'usuario_id' do professor responsável.
    `professor_id` INT,

    -- 'data_criacao': Registra quando o curso foi criado.
    `data_criacao` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- 'data_atualizacao': Atualiza automaticamente para a data e hora atuais sempre que
    -- uma linha da tabela é modificada.
    `data_atualizacao` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- 'status': O estado atual do curso. ENUM limita as opções.
    -- 'DEFAULT 'rascunho'' define que todo novo curso começa como um rascunho.
    `status` ENUM('rascunho', 'publicado', 'arquivado') DEFAULT 'rascunho',

    -- Define a relação de chave estrangeira.
    -- Conecta 'professor_id' desta tabela com 'usuario_id' da tabela 'usuarios'.
    -- 'ON DELETE SET NULL': Se um professor for deletado da tabela 'usuarios',
    -- o campo 'professor_id' nos cursos dele se tornará NULO, em vez de apagar o curso.
    FOREIGN KEY (`professor_id`) REFERENCES `usuarios`(`usuario_id`) ON DELETE SET NULL
) ENGINE=InnoDB;

-- ================================================================================= --
-- INSERÇÃO DE DADOS DE EXEMPLO NA TABELA 'cursos'
-- ================================================================================= --
INSERT INTO `cursos` (`titulo`, `descricao`, `professor_id`, `status`) VALUES
    ('Modelagem de Dados para Sistemas Web',
     'Curso prático sobre como planejar e estruturar bancos de dados relacionais complexos.',
     3, -- ID do Prof. Carlos Santos
     'publicado'),
    ('Desenvolvimento de APIs REST com Python e Flask',
     'Crie APIs eficientes e seguras, integrando-as com seu banco de dados MySQL.',
     2, -- ID da Prof. Ana Silva
     'rascunho');
    -- NOTA: Em um cenário real, para evitar cursos duplicados (mesmo título e professor),
    -- poderíamos adicionar uma restrição UNIQUE na tabela:
    -- ALTER TABLE `cursos` ADD UNIQUE `unique_curso_professor`(`titulo`, `professor_id`);

-- ================================================================================= --
-- CONSULTAS (QUERIES) - EXEMPLOS DE COMO EXTRAIR INFORMAÇÕES DO BANCO DE DADOS
-- ================================================================================= --

-- Consulta 1: Selecionar todos os usuários cujo e-mail termina com '@lms.com'.
-- O operador 'LIKE' com '%' é usado para buscar padrões em textos.
SELECT * FROM usuarios WHERE email LIKE '%@lms.com';

-- Consulta 2: Selecionar todos os usuários que são do tipo 'professor'.
SELECT * FROM usuarios WHERE tipo_usuario = 'professor';

-- Consulta 3: Selecionar todas as colunas de todos os registros na tabela 'cursos'.
SELECT * FROM cursos;

-- Consulta 4: Selecionar apenas o título e o ID do professor de cada curso.
SELECT titulo, professor_id FROM cursos;

-- Consulta 5: Buscar os dados de um usuário específico pelo seu ID.
-- Neste caso, buscando o usuário com ID = 2 (Prof. Ana Silva).
SELECT * FROM usuarios WHERE usuario_id = 2;

-- Consulta 6: Juntar informações de duas tabelas.
-- Mostra o título de cada curso e o nome do professor correspondente.
-- 'INNER JOIN' combina linhas das tabelas 'cursos' e 'usuarios' onde a condição
-- 'cursos.professor_id = usuarios.usuario_id' é verdadeira.
-- 'AS nome_professor' é um alias, que renomeia a coluna 'usuarios.nome' para 'nome_professor' no resultado.
SELECT
    cursos.titulo,
    usuarios.nome AS nome_professor
FROM
    cursos
INNER JOIN
    usuarios ON (cursos.professor_id = usuarios.usuario_id);

-- Consulta 7: Similar à anterior, mas agora ordenando o resultado em ordem alfabética pelo nome do professor.
SELECT
    cursos.titulo,
    usuarios.nome AS nome_professor
FROM
    cursos
INNER JOIN
    usuarios ON (cursos.professor_id = usuarios.usuario_id)
ORDER BY
    usuarios.nome;

-- Consulta 8: Uma consulta mais avançada para gerar um relatório.
-- Conta quantos cursos cada professor publicou.
SELECT
    u.nome AS nome_professor,         -- Seleciona o nome do professor (usando o alias 'u' para a tabela usuarios).
    COUNT(c.curso_id) AS total_cursos -- Conta o número de cursos (usando o alias 'c' para a tabela cursos) e renomeia a coluna para 'total_cursos'.
FROM
    `cursos` c                        -- Define 'c' como um apelido para a tabela 'cursos'.
INNER JOIN
    `usuarios` u ON c.professor_id = u.usuario_id -- Junta com a tabela 'usuarios' (apelidada de 'u').
WHERE
    u.tipo_usuario = 'professor'      -- Filtra para incluir apenas usuários que são professores.
GROUP BY
    u.usuario_id, u.nome              -- Agrupa os resultados por professor, para que o COUNT() conte os cursos de cada um separadamente.
ORDER BY
    total_cursos DESC, u.nome ASC;    -- Ordena o resultado: primeiro pelo maior número de cursos (DESC), e depois pelo nome do professor (ASC) como critério de desempate.
