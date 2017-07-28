
INSERT INTO `user` (`id`, `address`, `country`, `email`, `firstName`, `loginCode`, `surname`, `role_id`) VALUES(1, 'CHANDIGARH', 'INDIA', 'insonix@gmail.com', 'Ajit', 'password', 'Boparai', 1);
INSERT INTO `user` (`id`, `address`, `country`, `email`, `firstName`, `loginCode`, `surname`, `role_id`) VALUES(2, 'MOHALI', 'INDIA', 'it@gmail.com', 'IT', 'it_person', 'IT Person', 2);
INSERT INTO `user` (`id`, `address`, `country`, `email`, `firstName`, `loginCode`, `surname`, `role_id`) VALUES(3, 'CHANDIGARH', 'INDIA', 'admin@gmail.com', 'Admin', 'admin', 'Administrator', 3);
INSERT INTO `user` (`id`, `address`, `country`, `email`, `firstName`, `loginCode`, `surname`, `role_id`) VALUES(4, 'MOHALI', 'INDIA', 'embroyology@gmail.com', 'Embryology', 'embryo_s', 'Senior', 4);
INSERT INTO `user` (`id`, `address`, `country`, `email`, `firstName`, `loginCode`, `surname`, `role_id`) VALUES(5, 'CHANDIGARH', 'INDIA', 'embroyology@gmail.com', 'Embryology', 'embryo_j', 'Junior', 5);
INSERT INTO `user` (`id`, `address`, `country`, `email`, `firstName`, `loginCode`, `surname`, `role_id`) VALUES(6, 'CHANDIGARH', 'INDIA', 'embroyology@gmail.com', 'Embryology', 'embryo_t', 'Trainee', 6);
INSERT INTO `user` (`id`, `address`, `country`, `email`, `firstName`, `loginCode`, `surname`, `role_id`) VALUES(7, 'CHANDIGARH', 'INDIA', 'andro@gmail.com', 'Andro', 'andro', 'Andrology', 7);
INSERT INTO `user` (`id`, `address`, `country`, `email`, `firstName`, `loginCode`, `surname`, `role_id`) VALUES(8, 'MOHALI', 'INDIA', 'nurse@gmail.com', 'Nurse', 'nurse', 'Nurse', 8);
INSERT INTO `user` (`id`, `address`, `country`, `email`, `firstName`, `loginCode`, `surname`, `role_id`) VALUES(9, 'CHANDIGARH', 'INDIA', 'doctor@gmail.com', 'Doctor', 'doctor', 'Doctor', 9);
INSERT INTO `user` (`id`, `address`, `country`, `email`, `firstName`, `loginCode`, `surname`, `role_id`) VALUES(10, 'MOHALI', 'INDIA', 'lab@gmail.com', 'Lab', 'lab_manager', 'Manager', 10);
INSERT INTO `user` (`id`, `address`, `country`, `email`, `firstName`, `loginCode`, `surname`, `role_id`) VALUES(11, 'MOHALI', 'INDIA', 'accounts@gmail.com', 'Accounts_S', 'accounts_s', 'Manager', 11);
INSERT INTO `user` (`id`, `address`, `country`, `email`, `firstName`, `loginCode`, `surname`, `role_id`) VALUES(12, 'CHANDIGARH', 'INDIA', 'accountj@gmail.com', 'Accounts_J', 'accounts_j', 'Manager', 12);
INSERT INTO `user` (`id`, `address`, `country`, `email`, `firstName`, `loginCode`, `surname`, `role_id`) VALUES(13, 'CHANDIGARH', 'INDIA', 'accountt@gmail.com', 'Accounts_T', 'accounts_t', 'Manager', 13);
INSERT INTO `user` (`id`, `address`, `country`, `email`, `firstName`, `loginCode`, `surname`, `role_id`) VALUES(14, 'CHANDIGARH', 'INDIA', 'billing@gmail.com', 'Billing', 'billing', 'Officer', 14);
INSERT INTO `user` (`id`, `address`, `country`, `email`, `firstName`, `loginCode`, `surname`, `role_id`) VALUES(15, 'MOHALI', 'INDIA', 'client@gmail.com', 'Client_Liason', 'client_liason', 'Liason', 15);





INSERT INTO `investigation` (`id`, `description`, `_group`, `name`) VALUES(1,'BLOOD WORK',1,'HBV');
INSERT INTO `investigation` (`id`, `description`, `_group`, `name`) VALUES(2,'BLOOD WORK',1,'CMV');
INSERT INTO `investigation` (`id`, `description`, `_group`, `name`) VALUES(3,'BLOOD WORK',1,'SYPHILIS');
INSERT INTO `investigation` (`id`, `description`, `_group`, `name`) VALUES(4,'BLOOD WORK',1,'B.Group');
INSERT INTO `investigation` (`id`, `description`, `_group`, `name`) VALUES(5,'BLOOD WORK',1,'HBC');
INSERT INTO `investigation` (`id`, `description`, `_group`, `name`) VALUES(6,'BLOOD WORK',1,'HIV I & II');
INSERT INTO `investigation` (`id`, `description`, `_group`, `name`) VALUES(7,'MICROBIOLOGY',2,'Urea for Mycoplasma');
INSERT INTO `investigation` (`id`, `description`, `_group`, `name`) VALUES(8,'MICROBIOLOGY',2,'Urine for Chlamydia');
INSERT INTO `investigation` (`id`, `description`, `_group`, `name`) VALUES(9,'HORMONAL ASSAY',3,'AMH');
INSERT INTO `investigation` (`id`, `description`, `_group`, `name`) VALUES(10,'HORMONAL ASSAY',3,'FSH');
INSERT INTO `investigation` (`id`, `description`, `_group`, `name`) VALUES(11,'HORMONAL ASSAY',3,'LH');
INSERT INTO `investigation` (`id`, `description`, `_group`, `name`) VALUES(12,'HORMONAL ASSAY',3,'ESTRADIOL');


INSERT INTO `client` (`id`, `dOB`, `email`, `first_name`, `gender`, `middle_name`, `surname`, `phone_number`, `couple_id`) VALUES(1, '2009-03-05 00:00:00', 'male@gmail.com', 'Partner', 'Male', 'Demo', 'Test', '9859584586', null);
INSERT INTO `client` (`id`, `dOB`, `email`, `first_name`, `gender`, `middle_name`, `surname`, `phone_number`, `couple_id`) VALUES(2, '2011-03-05 00:00:00', 'female@gmail.com', 'Partner','Female', 'Demo', 'Test','9885522114',  null);

INSERT INTO `clipart` (`id`, `source`, `couple_id`) VALUES(1, '1.jpg', null);
INSERT INTO `couple` (`id`, `clipart_id`, `man_id`, `woman_id`) VALUES(1, 1, 1, 2);

UPDATE client set couple_id = 1 where id =1;
UPDATE client set couple_id = 1 where id =2;
UPDATE clipart set couple_id = 1 where id =1;

--INSERT INTO `codes` (`id`, `code`, `client_id`, `treatment_id`, `codes_id`) VALUES (1, 'A1', 2, null, 2);
--INSERT INTO `codes` (`id`, `code`, `client_id`, `treatment_id`, `codes_id`) VALUES (2, 'A2', 1, null, 1);

INSERT INTO `codes`(`id`,`code`,`client_id`,`patient_investigation_id`,`treatment_id`,`codes_id`)VALUES(1, 'AAA',2,null,null, 2);
INSERT INTO `codes`(`id`,`code`,`client_id`,`patient_investigation_id`,`treatment_id`,`codes_id`)VALUES(2, 'AAB',1,null,null, 1);

INSERT INTO `treatment` (`id`,`end_date`,`name`,`start_date`,`codes_id`, `cycle_type`) VALUES (1, DATE_ADD(now(),INTERVAL 9 DAY), '', DATE_ADD(now(),INTERVAL -1 DAY), null, 1);
INSERT INTO `treatment` (`id`,`end_date`,`name`,`start_date`,`codes_id`, `cycle_type`) VALUES (2, DATE_ADD(now(),INTERVAL 9 DAY), '', DATE_ADD(now(),INTERVAL -1 DAY), null, 1);

UPDATE `codes` set treatment_id = 1 where id = 1;
UPDATE `codes` set treatment_id = 2 where id = 2;

UPDATE `treatment` set codes_id = 1 where id = 1;
UPDATE `treatment` set codes_id = 2 where id = 2;

--INSERT INTO `remark` (`id`, `remark`, `_type`) VALUES (1,'General remarks','g');
--INSERT INTO `remark` (`id`, `remark`, `_type`) VALUES (2,'General remarks','g');

INSERT INTO `patient_investigation` (`id`, `bmi`, `scan`, `code_id`, `nurse_id`) VALUES (1, 1, 'scan',1, 8);
INSERT INTO `patient_investigation` (`id`, `bmi`, `scan`, `code_id`, `nurse_id`) VALUES (2, 1, 'scan',2, 8);

UPDATE `codes` set patient_investigation_id = 1 where id = 1;
UPDATE `codes` set patient_investigation_id = 2 where id = 2;

INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (1,'-',1,1);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (2,'+',2,1);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (3,'-',3,1);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (4,'AB+',4,1);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (5,'-',5,1);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (6,'-',6,1);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (7,'UREA',7,1);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (8,'URINE',8,1);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (9,'25',9,1);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (10,'22',10,1);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (11,'56',11,1);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (12,'65',12,1);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (13,'+',1,2);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (14,'-',2,2);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (15,'-',3,2);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (16,'O+',4,2);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (17,'+',5,2);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (18,'-',6,2);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (19,'UREA',7,2);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (20,'URINE',8,2);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (21,'12',9,2);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (22,'56',10,2);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (23,'32',11,2);
INSERT INTO `investigatin_value` (`id`, `_value`, `investigation_id`, `patient_investigation`) VALUES (24,'22',12,2);

