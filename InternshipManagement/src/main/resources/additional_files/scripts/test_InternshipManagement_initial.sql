create database InternshipManagement;
use InternshipManagement;

CREATE TABLE user (
id BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(20) NOT NULL,
last_name VARCHAR(20) NOT NULL,
company_id BIGINT
);

CREATE TABLE company (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(30) NOT NULL UNIQUE,
city VARCHAR(30) NOT NULL
);

ALTER TABLE user
ADD CONSTRAINT user_company_id_fk
FOREIGN KEY (company_id) REFERENCES company(id);


INSERT INTO company(name, city)
	VALUES ('Effortel', 'Sofia');
INSERT INTO user(first_name, last_name, company_id)
	VALUES ('Antoan', 'Stoilov', 1), ('Pavel', 'Ganov', 1), ('Ivan', 'Mishev', 1);



DROP TABLE user;
DROP TABLE company;