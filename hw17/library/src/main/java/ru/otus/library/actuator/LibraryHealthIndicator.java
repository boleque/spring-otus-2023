package ru.otus.library.actuator;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.library.models.Book;
import ru.otus.library.repositories.BookRepository;

import java.util.List;


@Component
@AllArgsConstructor
public class LibraryHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health getHealth(boolean includeDetails) {
        return HealthIndicator.super.getHealth(includeDetails);
    }

    @Override
    public Health health() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "ERROR. Library is empty")
                    .build();
        }
        return Health.up().withDetail("message", "OK. Books are in library").build();
    }
}
