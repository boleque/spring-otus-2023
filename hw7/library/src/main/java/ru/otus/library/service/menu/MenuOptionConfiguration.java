package ru.otus.library.service.menu;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static ru.otus.library.service.utils.MenuCommandConstants.UPDATE_BOOK_TITLE_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_BOOK_BY_ID_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_ALL_BOOKS_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.DELETE_BOOK_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.CREATE_BOOK_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_ALL_AUTHORS_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_ALL_GENRES_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_COMMENT_BY_ID_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_COMMENT_BY_BOOK_ID_KEY;


@Component
public class MenuOptionConfiguration {

    @Bean
    public MenuOption updateBookTitleMenuOption() {
        return new MenuOption(1, UPDATE_BOOK_TITLE_KEY);
    }

    @Bean
    public MenuOption getBookByIdMenuOption() {
        return new MenuOption(3, GET_BOOK_BY_ID_KEY);
    }

    @Bean
    public MenuOption getAllBooksMenuOption() {
        return new MenuOption(4, GET_ALL_BOOKS_KEY);
    }

    @Bean
    public MenuOption deleteBookMenuOption() {
        return new MenuOption(5, DELETE_BOOK_KEY);
    }

    @Bean
    public MenuOption createBookMenuOption() {
        return new MenuOption(6, CREATE_BOOK_KEY);
    }

    @Bean
    public MenuOption getAllAuthorsMenuOption() {
        return new MenuOption(7, GET_ALL_AUTHORS_KEY);
    }

    @Bean
    public MenuOption getAllGenresMenuOption() {
        return new MenuOption(8, GET_ALL_GENRES_KEY);
    }

    @Bean
    public MenuOption getCommentByIdMenuOption() {
        return new MenuOption(9, GET_COMMENT_BY_ID_KEY);
    }

    @Bean
    public MenuOption getCommentByBookIdMenuOption() {
        return new MenuOption(10, GET_COMMENT_BY_BOOK_ID_KEY);
    }
}
