DROP TABLE IF EXISTS USER;
CREATE TABLE USER (
                      Id INT AUTO_INCREMENT  PRIMARY KEY,
                      First_name VARCHAR(50) NOT NULL,
                      Last_name VARCHAR(50) NOT NULL,
                      Email VARCHAR(50) NOT NULL,
                      Password VARCHAR(50) NOT NULL,
                      Confirm_password VARCHAR(50) NOT NULL,
                      Embg VARCHAR(50) NOT NULL,
                      Is_admin_or_user BOOLEAN NOT NULL,
                      Finish_voting BOOLEAN NOT NULL,
                      Is_logged BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS CANDIDATE;
CREATE TABLE CANDIDATE (
                      Id INT AUTO_INCREMENT  PRIMARY KEY,
                      Name VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS BLOCK;
CREATE TABLE BLOCK (
                           Id INT AUTO_INCREMENT  PRIMARY KEY,
                           Hash VARCHAR(100) NOT NULL,
                           Nonce INT NOT NULL,
                           Previous_hash VARCHAR(1000) NOT NULL,
                           Timestamp INT NOT NULL
);

DROP TABLE IF EXISTS TRANSACTIONS;
CREATE TABLE TRANSACTIONS (
                       Id INT AUTO_INCREMENT  PRIMARY KEY,
                       Amount INT NOT NULL,
                       From_address VARCHAR(1000),
                       Timestamp INT NOT NULL,
                       To_address VARCHAR(1000) NOT NULL,
                       Block_id INT NOT NULL
);

DROP TABLE IF EXISTS FREQUENCY_FINAL;
CREATE TABLE FREQUENCY_FINAL (
                              Id INT AUTO_INCREMENT  PRIMARY KEY,
                              Candidate_Id INT NOT NULL,
                              Counter INT NOT NULL
);

DROP TABLE IF EXISTS USERPRIVATEKEY;
CREATE TABLE USERPRIVATEKEY (
                           Id INT AUTO_INCREMENT  PRIMARY KEY,
                           User_Id INT NOT NULL,
                           Private_Key BIGINT NOT NULL
);