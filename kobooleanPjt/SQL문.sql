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

create table areas(
    area_id varchar2(50) not null,
    area_name varchar2(100) not null,
    area_address varchar2(100) not null,
    area_num varchar2(50) not null,
    area_intro varchar2(500) not null,
    primary key(area_id)
)

drop table orders;

INSERT INTO AREAS(area_id, area_name, area_address, area_num, area_intro) VALUES(?,?,?,?,?);
