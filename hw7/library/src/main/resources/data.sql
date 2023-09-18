insert into genre (id, `name`)
values (default, 'fiction'),
       (default, 'adventure'),
       (default, 'novels'),
       (default, 'detective');

insert into author (id, `name`)
values (default, 'Jane Austen'),
       (default, 'George Orwell'),
       (default, 'Jules Verne'),
       (default, 'Agatha Christie');

insert into book (id, `title`, `author_id`, `genre_id`)
values (default, 'Pride & Prejudice', 1, 3),
       (default, 'The Adventure of the Lost Meat‑card', 2, 1),
       (default, 'The Adventures of Captain Hatteras', 3, 2),
       (default, 'The Mysterious Affair at Styles', 4, 3);

insert into comment (id, `text`, `book_id`)
values (default, 'Boring', 1),
       (default, 'Good for a scholar program', 1),
       (default, 'Cool, but depressing', 2),
       (default, 'Entertainment', 3),
       (default, 'Read during the holiday', 4);