package ru.otus.library.service.processors;

import org.springframework.stereotype.Component;
import ru.otus.library.converters.BookConverter;
import ru.otus.library.exceptions.EntityNotFoundException;
import ru.otus.library.models.Book;
import ru.otus.library.service.BookService;
import ru.otus.library.service.IOService;
import ru.otus.library.service.menu.MenuOption;

@Component
public class GetBookByTitleCommandProcessor implements CommandProcessor {

    private final BookService bookService;

    private final BookConverter converter;

    private final IOService ioService;

    private final MenuOption getBookByTitleMenuOption;

    public GetBookByTitleCommandProcessor(
            BookService bookService,
            IOService ioService,
            BookConverter converter,
            MenuOption getBookByTitleMenuOption) {
        this.bookService = bookService;
        this.ioService = ioService;
        this.converter = converter;
        this.getBookByTitleMenuOption = getBookByTitleMenuOption;
    }

    @Override
    public void processCommand() {
        String bookTitle = ioService.readStringWithPrompt("Input book title");
        try {
            Book book = bookService.getByTitle(bookTitle);
            ioService.outputString(converter.convertBookToString(book));
        } catch (EntityNotFoundException ex) {
            ioService.outputString(ex.toString());
        }
    }

    @Override
    public MenuOption getProcessedCommandOption() {
        return getBookByTitleMenuOption;
    }
}
