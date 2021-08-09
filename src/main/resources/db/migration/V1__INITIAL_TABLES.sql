create table category
(
    id bigserial not null
        constraint category_pkey primary key,
    name varchar(255),
    description varchar(255),
    created_at timestamp,
    last_modified timestamp
);

create table product
(
    id bigserial not null
        constraint product_pkey primary key,
    name varchar(255),
    description varchar(255),
    quantity bigint,
    created_at timestamp,
    last_modified timestamp
);

create table category_products
(
    category_id bigint not null,
    constraint fk_category foreign key (category_id) references category(id),
    product_id bigint not null,
    constraint fk_product foreign key (product_id) references product(id),
    primary key (category_id, product_id)
);
