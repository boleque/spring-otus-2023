package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.dto.ReadBookDto;
import ru.otus.library.dto.GenreDto;
import ru.otus.library.dto.AuthorDto;
import ru.otus.library.dto.SaveBookDto;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.GenreService;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String listBookPage(Model model) {
        List<ReadBookDto> books = bookService.getAll();
        model.addAttribute("books", books);
        return "books-list";
    }

    @GetMapping("/edit")
    public String editBookPage(@RequestParam("id") long id, Model model) {
        ReadBookDto book = bookService.getById(id);
        List<AuthorDto> authors = authorService.getAll();
        List<GenreDto> genres = genreService.getAll();
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "book-save";
    }

    @GetMapping("/add")
    public String addBookPage(Model model) {
        ReadBookDto book = new ReadBookDto();
        List<AuthorDto> authors = authorService.getAll();
        List<GenreDto> genres = genreService.getAll();
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "book-save";
    }

    @GetMapping("/delete")
    public String deleteBookPage(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }


    @PostMapping("/edit")
    public String saveBook(SaveBookDto book) {
        bookService.saveBook(book);
        return "redirect:/";
    }
}
