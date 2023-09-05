insert into genre (id, `name`)
values (1, 'adventure');
insert into author (id, `name`)
values (1, 'Jules Verne');
insert into book (`title`, `author_id`, `genre_id`)
values ('The Desert of Ice', 1, 1);
