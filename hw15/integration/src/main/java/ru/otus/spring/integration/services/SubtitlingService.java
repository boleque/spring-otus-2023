package ru.otus.spring.integration.services;

import ru.otus.spring.integration.domain.PhraseItem;
import ru.otus.spring.integration.domain.Subtitle;

public interface SubtitlingService {
    Subtitle process(PhraseItem phrase);
}
