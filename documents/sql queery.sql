 -- =========================
-- CREATE DATABASE
-- =========================
-- CREATE DATABASE hackathon_evaluation;
USE hackathon_evaluation;

-- =========================
-- ADMIN TABLE (ONLY LOGIN)
-- =========================
CREATE TABLE admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- =========================
-- TEAMS TABLE
-- =========================
-- CREATE TABLE teams (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     team_name VARCHAR(150) NOT NULL UNIQUE,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
-- );

-- =========================
-- SPRINTS TABLE
-- =========================
-- CREATE TABLE sprints (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     sprint_number INT NOT NULL UNIQUE
-- );


-- =========================
-- SCORES TABLE
-- (Junction Table: Teams <-> Sprints)
-- =========================
-- CREATE TABLE scores (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     team_id INT NOT NULL,
--     sprint_id INT NOT NULL,
--     marks INT NOT NULL CHECK (marks >= 0),

--     UNIQUE (team_id, sprint_id),

--     FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE,
--     FOREIGN KEY (sprint_id) REFERENCES sprints(id) ON DELETE CASCADE
-- );


-- =========================
-- INSERT DEFAULT DATA
-- =========================

-- Admin (password should be hashed in real app)
-- INSERT INTO admins (username, password)
-- VALUES ('admin', 'admin123');

-- Default sprints
-- INSERT INTO sprints (sprint_number) VALUES (0), (1), (2);