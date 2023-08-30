package ru.otus.quizapp.service;

import org.springframework.stereotype.Service;
import ru.otus.quizapp.config.IOStreamsProvider;

@Service
public class IOServiceStreams implements IOService {

    private final IOStreamsProvider ioStreamsProvider;

    public IOServiceStreams(IOStreamsProvider ioStreamsProvider) {
        this.ioStreamsProvider = ioStreamsProvider;
    }

    @Override
    public void outputString(String s) {
        ioStreamsProvider.outputStream().println(s);
    }

    @Override
    public int readInt() {
        return Integer.parseInt(ioStreamsProvider.inputStream().nextLine());
    }

    @Override
    public int readIntWithPrompt(String prompt) {
        outputString(prompt);
        return Integer.parseInt(ioStreamsProvider.inputStream().nextLine());
    }

    @Override
    public String readStringWithPrompt(String prompt) {
        outputString(prompt);
        return ioStreamsProvider.inputStream().nextLine();
    }
}
