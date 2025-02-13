create table "order" (
    "id" serial primary key,
    "date_time_millis_created" bigint not null
);

create table "line_item" (
    "id" serial primary key,
    "order_id" int not null references "order" ("id"),
    "product_name" text not null,
    "quantity" int not null,
    "price_usd" numeric not null
);
