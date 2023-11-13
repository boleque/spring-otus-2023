package ru.otus.library.service.processors;

import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;
import ru.otus.library.service.menu.MenuOption;


@Component
public class MigrateMongoToH2CommandProcessor implements CommandProcessor {

    private final JobLauncher jobLauncher;

    private final Job migrateMongoToH2;

    private final MenuOption getMigrateMongoToSqlMenuOption;

    public MigrateMongoToH2CommandProcessor(
            JobLauncher jobLauncher,
            Job migrateMongoToH2,
            MenuOption getMigrateMongoToSqlMenuOption) {
        this.jobLauncher = jobLauncher;
        this.migrateMongoToH2 = migrateMongoToH2;
        this.getMigrateMongoToSqlMenuOption = getMigrateMongoToSqlMenuOption;
    }

    @SneakyThrows
    @Override
    public void processCommand() {
        jobLauncher.run(migrateMongoToH2, new JobParameters());
    }

    @Override
    public MenuOption getProcessedCommandOption() {
        return this.getMigrateMongoToSqlMenuOption;
    }
}
