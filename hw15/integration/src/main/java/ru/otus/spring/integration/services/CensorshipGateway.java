package ru.otus.spring.integration.services;


import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.integration.domain.PhraseItem;
import ru.otus.spring.integration.domain.Subtitle;

import java.util.Collection;
import java.util.List;


@MessagingGateway
public interface CensorshipGateway {
    @Gateway(requestChannel = "phrasesChannel", replyChannel = "subtitlesChannel")
    List<Subtitle> process(Collection<PhraseItem> phrases);
}
