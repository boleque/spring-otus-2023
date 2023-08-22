package ru.otus.quizapp.service;

import org.springframework.stereotype.Service;
import ru.otus.quizapp.config.ApplicationProperties;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ApplicationStopServiceImpl implements ApplicationStopService {

    private final IOServiceStreams ioService;

    private final ApplicationProperties properties;

    private final AtomicBoolean executionFlag;

    private final LocalizationService localizationService;

    public ApplicationStopServiceImpl(
            IOServiceStreams ioService,
            ApplicationProperties properties,
            LocalizationService localizationService) {
        this.ioService = ioService;
        this.properties = properties;
        this.executionFlag = new AtomicBoolean(true);
        this.localizationService = localizationService;
    }

    @Override
    public boolean isApplicationRunning() {
        return executionFlag.get();
    }

    @Override
    public void stopApplication() {
        if (properties.isConfirmExit()) {
            var quitConfirmationAction = localizationService.getMessage("quitConfirmationAction");
            var exitConfirmation = ioService.readStringWithPrompt(quitConfirmationAction);
            var noConfirmationAction = localizationService.getMessage("noConfirmationAction");
            if (exitConfirmation.equalsIgnoreCase(noConfirmationAction)) {
                return;
            }
        }
        executionFlag.set(false);
    }
}
