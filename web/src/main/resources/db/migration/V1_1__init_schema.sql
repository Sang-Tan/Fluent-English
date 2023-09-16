create table if not exists public.users
(
    id           bigserial
        primary key,
    created_date timestamp(6) not null,
    email        varchar(255)
        constraint uk6dotkott2kjsp8vw4d0m25fb7
            unique,
    enabled      boolean      not null,
    full_name    varchar(255),
    password     varchar(255)
);

create table if not exists public.admins
(
    id           bigserial
        primary key,
    created_date timestamp(6) not null,
    email        varchar(255)
        constraint uk47bvqemyk6vlm0w7crc3opdd4
            unique,
    enabled      boolean      not null,
    full_name    varchar(255),
    password     varchar(255)
);

alter table public.admins
    owner to dev;

alter table public.users
    owner to dev;