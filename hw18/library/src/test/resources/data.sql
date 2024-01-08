insert into genre (id, `name`)
values (1, 'adventure');

insert into author (id, `name`)
values (1, 'Jules Verne');

insert into book (`title`, `author_id`, `genre_id`)
values ('The Desert of Ice', 1, 1), ('Around the World in 80 Days', 1, 1);

insert into comment(id, `book_id`, `text`)
values (1, 1, 'good book'), (2, 1, 'bad book');