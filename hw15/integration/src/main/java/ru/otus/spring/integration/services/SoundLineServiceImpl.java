package ru.otus.spring.integration.services;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.AgeRestriction;
import ru.otus.spring.integration.domain.PhraseItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;


@Service
@Slf4j
public class SoundLineServiceImpl implements SoundLineService {

    private static final String[] PHRASES = {
            "What the fuck does that even mean?",
            "You want me to sell you this fucking pen?",
            "I fucked her brains out... for eleven seconds.",
            "The captain tied you up, he almost fuckin tasered you!",
            "Don't you dare throw that fucking water on me! Don't you fucking dare!",
            "I'm not a scientist; I don't know what the fuck you're talking about",
            "Jesus Christ, I think you have a fuckin' drug problem"
    };

    private final CensorshipGateway censorshipGateway;

    public SoundLineServiceImpl(CensorshipGateway censorshipGateway) {
        this.censorshipGateway = censorshipGateway;
    }

    @Override
    public void startGenerateSubtitlesLoop() {
        ForkJoinPool pool = new ForkJoinPool();
        for (int i = 0; i < 10; i++) {
            pool.execute(() -> {
                censorshipGateway.process(
                        generatePhrases()
                );
            });

            Utils.makeDelay(1000);
        }
    }

    private static List<PhraseItem> generatePhrases() {
        List<PhraseItem> items = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(1, 7); ++i) {
            items.add(generatePhrase());
        }
        return items;
    }

    private static PhraseItem generatePhrase() {
        AgeRestriction restriction = Utils.getRandomAgeRestriction();
        return new PhraseItem(restriction, PHRASES[RandomUtils.nextInt(0, PHRASES.length)]);
    }
}
