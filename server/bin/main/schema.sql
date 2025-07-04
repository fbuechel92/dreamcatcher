-- Create database
CREATE DATABASE IF NOT EXISTS dreamdb;

-- Use the created database
USE dreamdb;

-- User table
CREATE TABLE dreamdb.user (
                              user_id INT AUTO_INCREMENT PRIMARY KEY,
                              email VARCHAR(255) NOT NULL,
                              password VARCHAR(255) NOT NULL,
                              name VARCHAR(255),
                              gender VARCHAR(50),
                              birthdate DATE,
                              country VARCHAR(100),
                              occupation VARCHAR(100),
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Theory table
CREATE TABLE dreamdb.theory (
                                theory_id INT AUTO_INCREMENT PRIMARY KEY,
                                theory_name VARCHAR(255),
                                theory_description TEXT,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- DreamAnalysis table
CREATE TABLE dreamdb.dream_analysis (
                                        dream_analysis_id INT AUTO_INCREMENT PRIMARY KEY,
                                        user_id INT NOT NULL,
                                        dream_title VARCHAR(255),
                                        dream_theme VARCHAR(255),
                                        interpretation TEXT,
                                        implications TEXT,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- DreamTrendAnalysis table
CREATE TABLE dreamdb.dream_trend_analysis (
                                              dream_trend_analysis_id INT AUTO_INCREMENT PRIMARY KEY,
                                              user_id INT NOT NULL,
                                              trend VARCHAR(255),
                                              interpretation TEXT,
                                              implications TEXT,
                                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                              FOREIGN KEY (user_id) REFERENCES user(user_id)
);

-- Dream table
CREATE TABLE dreamdb.dream (
                               dream_id INT AUTO_INCREMENT PRIMARY KEY,
                               user_id INT NOT NULL,
                               dream_analysis_id INT NULL,
                               theory_id INT NOT NULL,
                               visitor VARCHAR(255),
                               plot TEXT,
                               location VARCHAR(255),
                               mood VARCHAR(100),
                               sleepQuality VARCHAR(255),
                               additional_info TEXT,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               FOREIGN KEY (user_id) REFERENCES user(user_id),
                               FOREIGN KEY (dream_analysis_id) REFERENCES dream_analysis(dream_analysis_id),
                               FOREIGN KEY (theory_id) REFERENCES theory(theory_id)
);