package ru.otus.spring.integration.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.PhraseItem;
import ru.otus.spring.integration.domain.Subtitle;


@Service
@Slf4j
public class SubtitlingServiceImpl implements SubtitlingService {
    @Override
    public Subtitle process(PhraseItem phrase) {
        String originalText = phrase.getText();
        log.info("Creating subtitle for phrase: {}", originalText);
        Utils.makeDelay(3000);
        log.info("Subtitle for phrase: {} created", originalText);

        return new Subtitle(originalText, originalText);
    }
}
