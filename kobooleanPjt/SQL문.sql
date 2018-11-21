create table users(
    user_id varchar2(50) not null,
    user_pw varchar2(50) not null,
    user_name varchar2(50) not null,
    user_phone varchar2(50) not null,
    primary key (user_id) 
)

create table orders(
    order_id varchar2(50) not null,
    order_num varchar2(50) not null,
    order_name varchar2(200) not null,
    order_address varchar2(200) not null,
    order_table number(20) not null,
    order_phone varchar2(50) not null,
    order_latis varchar2(100),
    order_longs varchar2(100),
    primary key(order_id)
)


drop table orders;

