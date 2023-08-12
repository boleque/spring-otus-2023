package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.config.ApplicationStopServiceSettingsProvider;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ApplicationStopServiceImpl implements ApplicationStopService {

    private final IOServiceStreams ioService;

    private final ApplicationStopServiceSettingsProvider settingsProvider;

    private final AtomicBoolean executionFlag;

    public ApplicationStopServiceImpl(
            IOServiceStreams ioService,
            ApplicationStopServiceSettingsProvider settingsProvider) {
        this.ioService = ioService;
        this.settingsProvider = settingsProvider;
        this.executionFlag = new AtomicBoolean(true);
    }

    @Override
    public boolean isApplicationRunning() {
        return executionFlag.get();
    }

    @Override
    public void stopApplication() {
        if (settingsProvider.isConfirmExit()) {
            var exitConfirmation = ioService.readStringWithPrompt("Do you want yo quit? (y/n)");
            if (exitConfirmation.equalsIgnoreCase("n")) {
                return;
            }
        }
        executionFlag.set(false);
    }
}
