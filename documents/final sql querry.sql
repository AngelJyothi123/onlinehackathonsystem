-- CREATE DATABASE hackathon_system;
USE hackathon_system;

-- CREATE TABLE admins (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     username VARCHAR(100) NOT NULL UNIQUE,
--     password VARCHAR(255) NOT NULL
-- );

-- CREATE TABLE teams (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     team_name VARCHAR(150) NOT NULL UNIQUE,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
-- );

-- CREATE TABLE team_members (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     team_id INT NOT NULL,
--     member_name VARCHAR(100) NOT NULL,

--     FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE
-- );

-- CREATE TABLE sprints (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     sprint_number INT NOT NULL UNIQUE
-- );
-- CREATE TABLE scores (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     team_id INT NOT NULL,
--     sprint_id INT NOT NULL,
--     marks INT NOT NULL DEFAULT 0 CHECK (marks >= 0),

--     UNIQUE (team_id, sprint_id),

--     FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE,
--     FOREIGN KEY (sprint_id) REFERENCES sprints(id) ON DELETE CASCADE
-- );


-- Default Admin (password must be hashed in backend using BCrypt)
INSERT INTO admins (username, password)
VALUES ('admin', 'admin123');

-- Default Sprints
INSERT INTO sprints (sprint_number) VALUES (0), (1), (2);
