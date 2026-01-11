-- 1️⃣ Nom de la base de données
CREATE DATABASE IF NOT EXISTS signalement_urbain;
USE signalement_urbain;

-- 2️⃣ Tables de la base de données
-- Table utilisateur
CREATE TABLE IF NOT EXISTS utilisateur (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL
);

-- Table signalement
CREATE TABLE IF NOT EXISTS signalement (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(150) NOT NULL,
    description TEXT NOT NULL,
    type_probleme VARCHAR(50) NOT NULL,
    statut VARCHAR(30) NOT NULL,
    date_signalement DATE NOT NULL,
    utilisateur_id BIGINT,
    CONSTRAINT fk_utilisateur
        FOREIGN KEY (utilisateur_id)
        REFERENCES utilisateur(id)
        ON DELETE SET NULL
);

-- 3️⃣ Données de test
INSERT INTO utilisateur (nom, email) VALUES 
('Marwa Elomari', 'marwa@gmail.com'),
('Amira Zahra', 'amira@gmail.com');

INSERT INTO signalement 
(titre, description, type_probleme, statut, date_signalement, utilisateur_id)
VALUES
('Route cassée', 'Nids de poule dangereux', 'Route', 'EN_ATTENTE', CURDATE(), 1),
('Lampadaire HS', 'Pas d’éclairage la nuit', 'Éclairage', 'EN_COURS', CURDATE(), 2);
