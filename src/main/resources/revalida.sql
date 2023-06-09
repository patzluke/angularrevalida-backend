drop database if exists revalida;
create database revalida;

\c revalida

drop sequence if exists users_sequence;
create sequence users_sequence as int increment by 1 start with 227001;

drop table if exists users cascade;
create table users (
    user_id int default nextval('users_sequence') not null primary key,
    username varchar(70) unique,
    password varchar(200),
    first_name varchar(70),
    middle_name varchar(70),
    last_name varchar(70),
    email varchar(70) unique,
    address text,
    contact_no varchar(30) unique,
    birth_date date,    
    user_type varchar(70),
    is_active boolean
);

drop table if exists user_tokens;
create table user_tokens (
	user_id int,
	token varchar(250),
	foreign key(user_id) references users(user_id) on delete cascade
);

drop table if exists list_of_interest;
create table list_of_interest (
	user_id int,
	interest varchar(100),
	foreign key(user_id) references users(user_id) on delete cascade
);

drop table if exists otp;
create table otp (
	otp_id serial,
	user_id int,
	issued_time time,
	expiry_time time,
	otp_code varchar(10),
	foreign key(user_id) references users(user_id) on delete cascade
);

drop table if exists category;
create table category (
	category_id serial primary key,
	category_name varchar(50),
	image varchar(200)
);

drop table if exists product;
create table product (
	product_id serial primary key,
	product_name varchar(50) unique,
	product_details varchar(50),
	ingredients varchar(50),
	quantity int,
	price float,
	category_id int,
	sales int default 0,
	image varchar(100),
	foreign key(category_id) references category(category_id) on delete cascade
);

drop table if exists cart;
create table cart (
	user_id int,
	product_id int,
	quantity int,
	total_product_price float,
	image varchar(100),
	foreign key(user_id) references users(user_id) on delete cascade,
	foreign key(product_id) references product(product_id) on delete cascade
);

--create extension pgcrypto;

insert into category(category_name, image) values ('Best Sellers', 'best-seller-orange.png');
insert into category(category_name, image) values ('Value Meals', 'best-seller.png');
insert into category(category_name, image) values ('For Solo', 'best-seller.png');
insert into category(category_name, image) values ('For the Barkada', 'best-seller-orange.png');
insert into category(category_name, image) values ('New Products', 'best-seller.png');
insert into category(category_name, image) values ('Pizza', 'food-1.jpg');
insert into category(category_name, image) values ('Meat Balls', 'food-2.jpg');
insert into category(category_name, image) values ('Burgers', 'food-3.jpg');
insert into category(category_name, image) values ('Fries', 'food-4.jpg');
insert into category(category_name, image) values ('Soups', 'food-5.jpg');
insert into category(category_name, image) values ('Vegetarian', 'food-6.jpg');


insert into product(product_name, product_details, ingredients, quantity, price, category_id)
values ('ChickenJoy', '1 pc. Chicken with rice, fries and Drinks', 'chicken, potato, rice', 100, 109.00, 1);

insert into product(product_name, product_details, ingredients, quantity, price, category_id)
values ('Burger Meal', '1 pc. Cheese Burger, fries and Drinks', 'cheese, potato, patty', 100, 102.00, 2);

insert into product(product_name, product_details, ingredients, quantity, price, category_id)
values ('Pizza Meal','1 pc. Pizza, fries and Drinks', 'cheese, potato, dough', 100, 178.00, 1);

insert into product(product_name, product_details, ingredients, quantity, price, category_id)
values ('Pizza Meal Large','1 pc. 20inch Pizza', 'cheese, potato, dough', 100, 560.00, 4);

insert into product(product_name, product_details, ingredients, quantity, price, category_id)
values ('Pizza Folat','1 pc. 5inch Pizza with ice cream on top', 'cheese, potato, dough, ice cream', 100, 95.00, 5);

insert into product(product_name, product_details, ingredients, quantity, price, category_id)
values ('Pizza','1 pc. 10inch Pizza', 'cheese, potato, dough, ice cream', 100, 95.00, 6);

insert into users(username, password, first_name, middle_name, last_name, email, address, contact_no, birth_date, user_type, is_active) 
values('pastrero', '123456', initcap('patrick luke'), initcap('artuz'), initcap('astrero'), initcap('patzluke@gmail.com'), 'Merida Vista Verde Cainta', '9055261296', '2015-07-25', initcap('Admin'), true);

insert into users(username, password, first_name, middle_name, last_name, email, address, contact_no, birth_date, user_type, is_active) 
values('nikaastrero', '123456', initcap('nika ondria'), initcap('artuz'), initcap('astrero'), initcap('nika@gmail.com'), 'Merida Vista Verde Cainta', '9178192726', '2015-07-25', initcap('customer'), true);
