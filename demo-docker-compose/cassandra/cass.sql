CREATE KEYSPACE test_mid WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
USE test_mid;
CREATE TABLE cardinfo (midId UUID PRIMARY KEY, cardCompany text, cardNetwork text, customerName text, customerCode text, payInterest double);
CREATE TABLE users (   username varchar PRIMARY KEY,   password varchar, isValid boolean);
INSERT INTO cardinfo  (midId, cardCompany, cardNetwork, customerName, customerCode, payInterest) values (690f3710-aa71-11e6-b5e3-498a5dbb724e, 'Citibank ', 'VISA', 'Test customer name', 'CCN', 1.12);
INSERT INTO users (username, password, isValid) values ('test', 'testp',true);
