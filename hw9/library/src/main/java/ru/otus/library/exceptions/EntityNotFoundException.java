package ru.otus.library.exceptions;

import java.util.function.Supplier;

public class EntityNotFoundException extends RuntimeException implements Supplier<EntityNotFoundException> {
    private static final String DEFAULT_MESSAGE_FOR_ID_AND_CLASS_FORMAT = "[%s] with id [%s] not found.";

    public EntityNotFoundException(final String message) {
        super(message);
    }

    public static EntityNotFoundException withIdAndClass(final long id, final Class<?> clazz) {
        return new EntityNotFoundException(String.format(DEFAULT_MESSAGE_FOR_ID_AND_CLASS_FORMAT, clazz, id));
    }

    @Override
    public EntityNotFoundException get() {
        return new EntityNotFoundException(getMessage());
    }
}
