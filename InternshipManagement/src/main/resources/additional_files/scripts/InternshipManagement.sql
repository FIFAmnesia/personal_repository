create database InternshipManagement;
use InternshipManagement;

CREATE TABLE setting (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL UNIQUE,
value VARCHAR(50) NOT NULL
);

CREATE TABLE student_information (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
egn VARCHAR(10) NOT NULL UNIQUE,
faculty_number VARCHAR(10) NOT NULL UNIQUE,
group_number BIGINT NOT NULL,
current_academic_year BIGINT NOT NULL,
speciality VARCHAR(30) NOT NULL,
CONSTRAINT check_speciality CHECK (speciality IN ('KSI', 'ITI'))
);

CREATE TABLE job_description (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
position VARCHAR(10) NOT NULL,
main_technology VARCHAR(30),
CONSTRAINT check_position CHECK (position IN ('BE', 'FE', 'DEVOPS', 'QA', 'SD', 'SA', 'NOC'))
);

CREATE TABLE company (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(30) NOT NULL UNIQUE,
city VARCHAR(30) NOT NULL,
address VARCHAR(255) NOT NULL
);

CREATE TABLE role (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(30) NOT NULL UNIQUE,
description VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE permission (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
resource_name VARCHAR(50) NOT NULL,
value TINYINT NOT NULL,
role_id BIGINT
);

CREATE TABLE user (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(20) NOT NULL,
last_name VARCHAR(20) NOT NULL,
phone_number VARCHAR(20) NOT NULL UNIQUE,
email VARCHAR(50) NOT NULL,
company_id BIGINT,
student_information_id BIGINT UNIQUE,
role_id BIGINT NOT NULL
);

CREATE TABLE credentials (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(30) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
user_id BIGINT NOT NULL UNIQUE
);

CREATE TABLE offer (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
start_date DATE NOT NULL,
end_date DATE NOT NULL,
active_until_date DATE NOT NULL,
remaining_positions BIGINT NOT NULL,
active BIT(1) NOT NULL DEFAULT 1,
company_id BIGINT NOT NULL,
job_description_id BIGINT NOT NULL
);

CREATE TABLE request (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
made_on DATE NOT NULL,
approved BIT(1),
user_id BIGINT NOT NULL,
offer_id BIGINT NOT NULL,
UNIQUE KEY unique_user_id_offer_id (user_id, offer_id)
);

CREATE TABLE internship (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
academic_year BIGINT NOT NULL,
completed BIT(1) DEFAULT 0,
note VARCHAR(255),
user_id BIGINT NOT NULL UNIQUE,
offer_id BIGINT NOT NULL
);












INSERT INTO student_information(egn, faculty_number, group_number, current_academic_year, speciality)
	VALUES ('9804231254', '121217034', 37, 2,'KSI'), ('9810215428', '121217101', 41, 2,'ITI'), ('9801012345' ,'121217150', 38, 3,'KSI');

INSERT INTO job_description(position, main_technology)
	VALUES ('be', 'java'), ('fe', 'angluar'), ('be', 'c#');

INSERT INTO company(name, city, address)
	VALUES ('VMware', 'Sofia', 'Garitage Park building 2, st. Donka Ushlinova 2, 1766'),
		   ('SAP', 'Sofia', 'bul. „Tzar Boris III“ 136A, 1618 Ovcha Kupel'), 
           ('Scalefocus', 'Plovdiv', 'Park Office Building, block „Iztochen“ 59, Floor 1, 4000 Kamenitza 1');

INSERT INTO role(name, description)
	VALUES ('admin', 'an admin is any worker in FKST faculty in TU, who is given access to the web app; they can make the needed check, whether a student has (not) completed an internships'),
		   ('student', 'any student, who can request internships and accept the approved ones'),
           ('employer', 'any employer who can register offers, approve/decline request, and be responsible for manually completing the internships');

INSERT INTO user(first_name, last_name, phone_number, email,
				 company_id, student_information_id, role_id)
	VALUES ('Petko', 'Georgiev', '0895432187', 'antoan.stoilov@gmail.com', null, null, 1),
		   ('Antoan', 'Stoilov', '0889786453', 'antoan.stoilov@gmail.com', null, 1, 2),
           ('Aleks', 'Kehayov', '0875389503', 'antoan.stoilov@gmail.com', null, 2, 2),
           ('Teodor', 'Dobrev', '0879523163', 'antoan.stoilov@gmail.com', null, 3, 2),
		   ('Dimitar', 'Dimitrov', '0876324509', 'antoan.stoilov@gmail.com', 1, null, 3),
           ('Ivan', 'Dimitrov', '0888563091', 'antoan.stoilov@gmail.com', 2, null, 3),
           ('Georgi', 'Bozukov', '0899886754', 'antoan.stoilov@gmail.com', 3, null, 3);

-- passwords are the same as the usernames; algorithm is SHA256
-- passwords are generated with the usernames like: petko123, antoan123 etc.
INSERT INTO credentials(username, password, user_id)
	VALUES ('petko.g_123', '84250052fb5e9af4e9855160cddcfa595d3248c6541f9387f4342668c58eb333', 1),
		   ('antoan.s_123', 'a2a148e74778a5ab96b3f41ccf08b7240f92eca1ee236e0df273132807d9aabc', 2),
           ('aleks.k_123', '35fd1db7365a8933f645ad68d1b56218feb3e10a66400179111aa8210254daa9', 3),
           ('teodor.d_123', '6e17058efa287b8736d9d1546c007b674ee33d55938914fe9409538f6733df1a', 4),
		   ('dimitar.d_123', '9dedb72c247ba5ac3c7c9db65c72e43516008e2b17d9ceca11a88756101a2c21', 5),
           ('ivan.d_123', '6c84b1086e558a0b9dad7623979f6ddf9f337084f281d9b3a07273d04b425344', 6),
		   ('georgi.b_123', 'e56962fd3c981e803b5c6dc71ab47320e4964060bbc7aefc3232bdf80bf5cbd9', 7);

INSERT INTO offer(start_date, end_date, active_until_date,
				  remaining_positions, company_id, job_description_id)
	VALUES ('2021-07-12', '2021-07-23', '2023-07-07', 2, 1, 1),
		   ('2021-07-12', '2021-07-23', '2023-07-07', 1, 2, 2),
           ('2021-07-12', '2021-07-23', '2023-07-07', 5, 3, 3),
           -- these below are for testig the expiring timer;
		   ('2021-07-12', '2021-07-23', '2021-07-07', 2, 1, 1),
		   ('2021-07-12', '2021-07-23', '2021-07-07', 1, 2, 2),
           ('2021-07-12', '2021-07-23', '2021-07-07', 5, 3, 3);



-- should be present as a constraint, but want to add the same emails to check email sending;
-- it would be the easier to user mine as sender and receiver;
-- ALTER TABLE user
-- ADD UNIQUE (email);

-- added bidirectional relation;
ALTER TABLE permission
ADD CONSTRAINT permission_role_id_fk
FOREIGN KEY (role_id) REFERENCES role(id);

-- added bidirectional relation;
ALTER TABLE user
ADD CONSTRAINT user_company_id_fk
FOREIGN KEY (company_id) REFERENCES company(id);

-- added bidirectional relation;
ALTER TABLE user
ADD CONSTRAINT user_student_information_id_fk
FOREIGN KEY (student_information_id) REFERENCES student_information(id);

-- added bidirectional relation;
ALTER TABLE user
ADD CONSTRAINT user_role_id_fk
FOREIGN KEY (role_id) REFERENCES role(id);

-- added bidirectional relation;
ALTER TABLE credentials
ADD CONSTRAINT credentials_user_id_fk
FOREIGN KEY (user_id) REFERENCES user(id);

-- added bidirectional relation;
ALTER TABLE offer
ADD CONSTRAINT offer_company_id_fk
FOREIGN KEY (company_id) REFERENCES company(id);

-- added bidirectional relation;
ALTER TABLE offer
ADD CONSTRAINT offer_job_description_id_fk
FOREIGN KEY (job_description_id) REFERENCES job_description(id);

-- added bidirectional relation;
ALTER TABLE request
ADD CONSTRAINT request_user_id_fk
FOREIGN KEY (user_id) REFERENCES user(id);

-- added bidirectional relation;
ALTER TABLE request
ADD CONSTRAINT request_offer_id_fk
FOREIGN KEY (offer_id) REFERENCES offer(id);

-- added bidirectional relation;
ALTER TABLE internship
ADD CONSTRAINT internship_user_id_fk
FOREIGN KEY (user_id) REFERENCES user(id);

-- added bidirectional relation;
ALTER TABLE internship
ADD CONSTRAINT internship_offer_id_fk
FOREIGN KEY (offer_id) REFERENCES offer(id);














select u.first_name, u.last_name, si.faculty_number from user u
join student_information si on u.student_information_id = si.id;


CREATE TABLE company (
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(30) NOT NULL UNIQUE,
city VARCHAR(30) NOT NULL
);

CREATE TABLE user (
id BIGINT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(20) NOT NULL,
last_name VARCHAR(20) NOT NULL,
company_id BIGINT
);

INSERT INTO company(name, city)
	VALUES ('Effortel', 'Sofia');
INSERT INTO user(first_name, last_name, company_id)
	VALUES ('Antoan', 'Stoilov', 1), ('Pavel', 'Ganov', 1), ('Ivan', 'Mishev', 1);
    
ALTER TABLE user
ADD CONSTRAINT user_company_id_fk
FOREIGN KEY (company_id) REFERENCES company(id);

DROP TABLE user;
DROP TABLE company;




ALTER TABLE permission
	DROP FOREIGN KEY permission_role_id_fk;
ALTER TABLE user
	DROP FOREIGN KEY user_company_id_fk;
ALTER TABLE user
	DROP FOREIGN KEY user_student_information_id_fk;
ALTER TABLE user
	DROP FOREIGN KEY user_role_id_fk;
ALTER TABLE credentials
	DROP FOREIGN KEY credentials_user_id_fk;
ALTER TABLE offer
	DROP FOREIGN KEY offer_company_id_fk;
ALTER TABLE offer
	DROP FOREIGN KEY offer_job_description_id_fk;
ALTER TABLE request
	DROP FOREIGN KEY request_user_id_fk;
ALTER TABLE request
	DROP FOREIGN KEY request_offer_id_fk;
ALTER TABLE internship
	DROP FOREIGN KEY internship_user_id_fk;
ALTER TABLE internship
	DROP FOREIGN KEY internship_offer_id_fk;



DROP TABLE setting;
DROP TABLE student_information;
DROP TABLE job_description;
DROP TABLE company;
DROP TABLE role;
DROP TABLE permission;
DROP TABLE user;
DROP TABLE credentials;
DROP TABLE offer;
DROP TABLE request;
DROP TABLE internship;

TRUNCATE TABLE setting;
TRUNCATE TABLE student_information;
TRUNCATE TABLE job_description;
TRUNCATE TABLE company;
TRUNCATE TABLE role;
TRUNCATE TABLE permission;
TRUNCATE TABLE user;
TRUNCATE TABLE credentials;
TRUNCATE TABLE offer;
TRUNCATE TABLE request;
TRUNCATE TABLE internship;

DROP DATABASE InternshipManagement;