CREATE DATABASE IF NOT EXISTS bf_project2;
USE bf_project2;

drop table if exists permission;
drop table if exists watchlist;
drop table if exists order_item;
drop table if exists order_table;
drop table if exists user;
drop table if exists product;

create table user(
    user_id bigint primary key not null auto_increment,
    email varchar(255) unique not null,
    password varchar(255) not null,
    role INT not null default 1, -- 1 user, 2 seller
    username varchar(255) unique not null,
    icon_url varchar(1000)
);

create table permission(
    permission_id BIGINT primary key unique not null auto_increment,
    value VARCHAR(255) not null,
    user_id BIGINT,

    foreign key (user_id) references user(user_id)
);

create table order_table(
    order_id BIGINT primary key unique not null auto_increment,
    date_placed DATETIME(6) not null, -- seconds precision decimal 6
    order_status VARCHAR(255) not null, -- PROCESSING, COMPLETED, CANCELLED
    user_id BIGINT,

    foreign key (user_id) references user(user_id)
);

create table product(
    product_id BIGINT primary key unique not null auto_increment,
    description VARCHAR(255) not null ,
    name VARCHAR(255) not null,
    quantity INT not null,
    retail_price DOUBLE not null,
    wholesale_price DOUBLE not null
);

create table watchlist(
    user_id BIGINT,
    product_id BIGINT,

    PRIMARY KEY (user_id, product_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);

create table order_item(
    item_id BIGINT primary key auto_increment,
    purchased_price DOUBLE not null,
    quantity INT not null,
    wholesale_price DOUBLE not null,
    order_id BIGINT,
    product_id BIGINT,

    foreign key (order_id) references order_table(order_id),
    foreign key (product_id) references product(product_id)
);
insert into user (user_id, email, password, role, username, icon_url) VALUES
    (1, 'seller@sdm.com', 'seller', 1, 'seller', 'https://cdn.corporate.walmart.com/dims4/WMT/15870a4/2147483647/strip/true/crop/1224x792+0+0/resize/870x563!/quality/90/?url=https%3A%2F%2Fcdn.corporate.walmart.com%2F0e%2F78%2F1c0917c94ce29c76e21e59934d25%2Flogo-walamrtspark-blue-transparent-background.png');

insert into user (user_id, email, password, role, username) VALUES
    (2, 'zyx@buy.com', 'zyx', 2, 'zyx');

insert into permission (permission_id, value, user_id) VALUES
    (1, 'SELLER', 1);
insert into permission (permission_id, value, user_id) VALUES
    (2, 'USER', 2);

insert into product (product_id, description, name, quantity, retail_price, wholesale_price) VALUES
    (1, 'Phone designed by Apple', 'iphone13', 200, '700', '500');
insert into product (product_id, description, name, quantity, retail_price, wholesale_price) VALUES
    (2, 'Charmin Ultra Strong Clean Touch Toilet Paper', '24 Family Mega Rolls', 50, 35, 20);
