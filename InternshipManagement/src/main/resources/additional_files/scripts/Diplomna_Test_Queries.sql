use InternshipManagement;

select * from job_description;

select * from offer;

delete from offer
where id = 5;

select * from user;
select * from credentials;
select * from company;

select * from request;
select * from internship;

select * from student_information;

select * from job_description;

select * from request
where approved is null;

INSERT INTO request(made_on, approved, user_id, offer_id)
	VALUES (CURDATE(), null, 2, 1);
    
INSERT INTO request(made_on, approved, user_id, offer_id)
	VALUES (CURDATE(), null, 2, 5);

select * from request
where approved is null;

update request
set approved = 1
where id = 1;

update offer
set active = 1
where id in (4, 5, 6);

update offer
set active = 1,
remaining_positions = 1
where id in (2);


select * from offer where active_until_date <= CURDATE();
select CURDATE();

select * from credentials;

select * from setting;

INSERT INTO user(first_name, last_name, phone_number, email,
				 company_id, student_information_id, role_id)
	VALUES ('Dimitar', 'Dimitrov', '0876324509', 'antoan.stoilov@gmail.com', 1, null, 3),
           ('Georgi', 'Bozukov', '0899886754', 'antoan.stoilov@gmail.com', 3, null, 3);

INSERT INTO credentials(username, password, user_id)
	VALUES ('dimitar123', '9dedb72c247ba5ac3c7c9db65c72e43516008e2b17d9ceca11a88756101a2c21', 8),
		   ('georgi23', '2c58f4e7e81fe03bfff1fafc17cdf39a0471e3502addb6d1e71989482770936d', 9);

           
delete from setting where id in (3, 4);