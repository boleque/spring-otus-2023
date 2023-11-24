package ru.otus.spring.integration.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.PhraseItem;
import ru.otus.spring.integration.domain.Subtitle;

import java.util.regex.Pattern;


@Service
@Slf4j
public class CensorshipServiceImpl implements CensorshipService {

    private final String[] wordsToCensor = {
            "fuck",
            "fucking",
            "fucked",
            "fuckin"
    };

    @Override
    public Subtitle process(PhraseItem phrase) {
        String originalText = phrase.getText();
        log.info("Censoring phrase: {}", originalText);

        Utils.makeDelay(3000);

        String censoredPhrase = phrase.getText().replaceAll(censorWords(wordsToCensor), "*");
        log.info("Censored: {}", censoredPhrase);

        return new Subtitle(originalText, censoredPhrase);
    }

    private String censorWords(String... words) {
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (!sb.isEmpty()) {
                sb.append("|");
            }
            sb.append(
                    String.format("(?<=(?=%s).{0,%d}).",
                            Pattern.quote(w),
                            w.length() - 1
                    )
            );
        }
        return sb.toString();
    }
}
