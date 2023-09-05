package ru.otus.library.service.shell;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static ru.otus.library.service.utils.MenuCommandConstants.UPDATE_BOOK_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_BOOK_BY_TITLE_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_BOOK_BY_ID_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_ALL_BOOKS_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.DELETE_BOOK_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.CREATE_BOOK_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_ALL_AUTHORS_KEY;
import static ru.otus.library.service.utils.MenuCommandConstants.GET_ALL_GENRES_KEY;


@Component
public class ShellOptionConfiguration {

    @Bean
    public ShellOption updateBookMenuOption() {
        return new ShellOption(1, UPDATE_BOOK_KEY);
    }

    @Bean
    public ShellOption getBookByTitleMenuOption() {
        return new ShellOption(2, GET_BOOK_BY_TITLE_KEY);
    }

    @Bean
    public ShellOption getBookByIdMenuOption() {
        return new ShellOption(3, GET_BOOK_BY_ID_KEY);
    }

    @Bean
    public ShellOption getAllBooksMenuOption() {
        return new ShellOption(4, GET_ALL_BOOKS_KEY);
    }

    @Bean
    public ShellOption deleteBookMenuOption() {
        return new ShellOption(5, DELETE_BOOK_KEY);
    }

    @Bean
    public ShellOption createBookMenuOption() {
        return new ShellOption(6, CREATE_BOOK_KEY);
    }

    @Bean
    public ShellOption getAllAuthorsMenuOption() {
        return new ShellOption(7, GET_ALL_AUTHORS_KEY);
    }

    @Bean
    public ShellOption getAllGenresMenuOption() {
        return new ShellOption(8, GET_ALL_GENRES_KEY);
    }
}
