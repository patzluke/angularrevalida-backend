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
    email varchar(70) unique,
	issued_time time,
	expiry_time time,
	otp_code varchar(10),
	tries int,
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
    cart_id serial primary key,
	user_id int,
	product_id int,
	quantity int,
	price float,
	product_name varchar(50),
	variation varchar(50),
	total_product_price float,
	image varchar(100),
	foreign key(user_id) references users(user_id) on delete cascade,
	foreign key(product_id) references product(product_id) on delete cascade
);

drop table if exists orders;
create table orders (
	order_id serial primary key,
	user_id int,
	total_price float,
	created_at timestamp,
	status varchar(20) default 'Pending',
	foreign key(user_id) references users(user_id) on delete cascade
);

drop table if exists order_details;
create table order_details (
	order_id int,
	user_id int,
	product_id int,
	quantity int,
	product_name varchar(50),
	variation varchar(50),
	total_product_price float,
	image varchar(100),
	foreign key(user_id) references users(user_id) on delete cascade,
	foreign key(order_id) references orders(order_id) on delete cascade
);

--create extension pgcrypto;

insert into category(category_name, image) values ('Best Sellers', 'icon-1.png');
insert into category(category_name, image) values ('Value Meals', 'value-meals.png');
insert into category(category_name, image) values ('Family Meals', 'icon-2.png');
insert into category(category_name, image) values ('For Solo', 'icon-5.png');
insert into category(category_name, image) values ('For the Barkada', 'icon-6.png');
insert into category(category_name, image) values ('New Products', 'icon-3.png');
insert into category(category_name, image) values ('Pizza', 'food-1.jpg');
insert into category(category_name, image) values ('Meat Balls', 'food-2.jpg');
insert into category(category_name, image) values ('Burgers', 'food-3.jpg');
insert into category(category_name, image) values ('Fries', 'food-4.jpg');
insert into category(category_name, image) values ('Soups', 'food-5.jpg');
insert into category(category_name, image) values ('Vegetarian', 'food-6.jpg');

    
insert into product(product_name, product_details, ingredients, quantity, price, category_id, image)
values ('Saang Chicken', 'Chicken with rice, fries and Drinks', 'chicken, rice, potato', 1000, 109.00, 1, 'chicken_with_fries.jpg');

insert into product(product_name, product_details, ingredients, quantity, price, category_id, image)
values ('Saang special spaghetti', 'Sweet and Creamy pasta meal', 'tomato sauce, pasta, special ingredient', 1000, 85.00, 2, 'Saang_special_spaghetti.jpg');

insert into product(product_name, product_details, ingredients, quantity, price, category_id, image)
values ('Saang Burger Meal', 'Cheese Burger with juicy meat patty', 'cheese, bread, patty', 1000, 99.00, 2, 'food-3.jpg');

insert into product(product_name, product_details, ingredients, quantity, price, category_id, image)
values ('whole roasted chicken','Special Roasted chicken with no need for sauce', '1 whole chicken', 1000, 480.00, 3, 'whole_roasted_chicken.jpg');

insert into product(product_name, product_details, ingredients, quantity, price, category_id, image)
values ('3 way roasted duck','Saang Special Roasted duck', '1 whole duck', 1000, 780.00, 3, '3_way_roasted_duck.jpg');

insert into product(product_name, product_details, ingredients, quantity, price, category_id, image)
values ('Saang breakfast meal','Healthy and tasty fried rice with egg', 'egg, rice, mix of vegetables', 1000, 99.00, 4, 'Saang_breakfast_meal.jpg');

insert into product(product_name, product_details, ingredients, quantity, price, category_id, image)
values ('Heavy Burger','Special Meaty and heavy burger breakfast', 'patty, special sauce, bread', 1000, 99.00, 4, 'Heavy_Burger.jpg');

insert into product(product_name, product_details, ingredients, quantity, price, category_id, image)
values ('Pizza Barkada','Thin crust pizza with a mix of vegetable and meat', 'cheese, potato, dough', 1000, 560.00, 5, 'food-6.jpg');

insert into product(product_name, product_details, ingredients, quantity, price, category_id, image)
values ('Kahit Saang Nachos','Nachos with meat toppings and vegetables', 'nacho, cheese, vegetables', 1000, 310.00, 5, 'Kahit_Saang_Nachos.jpg');

insert into product(product_name, product_details, ingredients, quantity, price, category_id, image)
values ('The Pizza','Cheesy Pizza with ham', 'cheese, ham, dough', 1000, 178.00, 7, 'food-1.jpg');

insert into product(product_name, product_details, ingredients, quantity, price, category_id, image)
values ('Salty Fries', 'Your plain old clasic regular fries but better', 'potato, catsup', 1000, 65.00, 10, 'food-4.jpg');


insert into users(username, password, first_name, middle_name, last_name, email, address, contact_no, birth_date, user_type, is_active) 
values('pastrero', '123456', initcap('patrick luke'), initcap('artuz'), initcap('astrero'), initcap('patzluke@gmail.com'), 'Merida Vista Verde Cainta', '9055261296', '2003-07-25', initcap('Admin'), true);

insert into users(username, password, first_name, middle_name, last_name, email, address, contact_no, birth_date, user_type, is_active) 
values('nikaastrero', '123456', initcap('nika ondria'), initcap('artuz'), initcap('astrero'), initcap('nika@gmail.com'), 'Merida Vista Verde Cainta', '9178192726', '2001-07-25', initcap('customer'), true);
