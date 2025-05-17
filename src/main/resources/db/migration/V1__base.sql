create schema sus;

create table sus.users(
    id serial primary key,
    name text not null
);

create table sus.subscriptions(
    id serial primary key,
    title text not null
);

create table sus.user_subs(
    user_id bigint references sus.users(id),
    sub_id bigint references sus.subscriptions(id)
);

create unique index idx_user_subs_id on sus.user_subs(user_id, sub_id);

insert into sus.subscriptions (title) values ('YouTube Premium'), ('VK Музыка'), ('Яндекс.Плюс'), ('Netflix');