package ru.otus.spring.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.integration.domain.AgeRestriction;
import ru.otus.spring.integration.domain.PhraseItem;
import ru.otus.spring.integration.services.CensorshipService;
import ru.otus.spring.integration.services.SubtitlingService;


@Configuration
public class IntegrationConfig {

    private final int channelCapacity = 10;

    @Bean
    public MessageChannelSpec<?, ?> phrasesChannel() {
        return MessageChannels.queue(channelCapacity);
    }

    @Bean
    public MessageChannelSpec<?, ?> subtitlesChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2);
    }

    @Bean
    public IntegrationFlow censorshipFlow(CensorshipService censorshipService, SubtitlingService subtitlingService) {
        return IntegrationFlow.from(phrasesChannel())
                .split()
                .<PhraseItem, Boolean>route(
                        phrase -> phrase.getAgeRestriction().equals(AgeRestriction.CHILD),
                        ar -> ar.subFlowMapping(true, c -> c.handle(censorshipService, "process"))
                                .subFlowMapping(false, s -> s.handle(subtitlingService, "process"))
                )
                .aggregate()
                .get();
    }

}
