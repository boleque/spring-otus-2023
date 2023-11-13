package ru.otus.library.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.models.Genre;

import javax.sql.DataSource;
import java.util.HashMap;


@Configuration
public class JobConfig {

    private static final int CHUNK_SIZE = 5;

    private static final String COMMENT_READER = "commentReader";

    private static final String GENRE_READER = "genreReader";

    private static final String AUTHOR_READER = "authorReader";

    private static final String BOOK_READER = "bookReader";

    private static final String MIGRATE_MONGO_TO_H2 = "migrateMongoToH2";

    private static final String IMPORT_GENRE_JOB_NAME = "importGenreJob";

    private static final String IMPORT_AUTHOR_JOB_NAME = "importAuthorJob";

    private static final String IMPORT_BOOK_JOB_NAME = "importBookJob";

    private static final String IMPORT_COMMENT_JOB_NAME = "importCommentJob";

    private final Logger logger = LoggerFactory.getLogger("Batch migration mongo to h2");

    private final MongoTemplate mongoTemplate;

    private final DataSource dataSource;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    public JobConfig(
            MongoTemplate mongoTemplate,
            DataSource dataSource,
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager
    ) {
        this.mongoTemplate = mongoTemplate;
        this.dataSource = dataSource;
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Bean
    public Job migrateMongoToH2(Step migrateGenreStep, Step migrateAuthorStep) {
        return new JobBuilder(MIGRATE_MONGO_TO_H2, jobRepository)
                .start(migrateGenreStep)
                .next(migrateAuthorStep)
                .next(migrateBookStep())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Genre> genreMongoReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Genre>()
                .name(GENRE_READER)
                .template(mongoTemplate)
                .targetType(Genre.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Author> authorMongoReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Author>()
                .name(AUTHOR_READER)
                .template(mongoTemplate)
                .targetType(Author.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Book> bookMongoReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Book>()
                .name(BOOK_READER)
                .template(mongoTemplate)
                .targetType(Book.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Comment> commentMongoReader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Comment>()
                .name(COMMENT_READER)
                .template(mongoTemplate)
                .targetType(Comment.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Comment> commentJdbcWriter() {
        JdbcBatchItemWriter<Comment> commenteWriter = new JdbcBatchItemWriter<>();
        commenteWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        commenteWriter.setSql("INSERT INTO comment (id,text,book_id) VALUES (:id,:text,:book_id)");
        commenteWriter.setDataSource(dataSource);
        return commenteWriter;
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Genre> genreJdbcWriter() {
        JdbcBatchItemWriter<Genre> genreWriter = new JdbcBatchItemWriter<>();
        genreWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        genreWriter.setSql("INSERT INTO genre (id,name) VALUES (:id,:name)");
        genreWriter.setDataSource(dataSource);
        return genreWriter;
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Author> authorJdbcWriter() {
        JdbcBatchItemWriter<Author> authorWriter = new JdbcBatchItemWriter<>();
        authorWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        authorWriter.setSql("INSERT INTO author (id,name) VALUES (:id,:name)");
        authorWriter.setDataSource(dataSource);
        return authorWriter;
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<Book> bookJdbcWriter() {
        JdbcBatchItemWriter<Book> bookWriter = new JdbcBatchItemWriter<>();
        bookWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        bookWriter.setSql("INSERT INTO book (id,title,author_id,genre_id) VALUES (:id,:title,:author.id,:genre.id)");
        bookWriter.setDataSource(dataSource);
        return bookWriter;
    }

    @Bean
    public Step migrateGenreStep() {
        return new StepBuilder(IMPORT_GENRE_JOB_NAME, jobRepository)
                .<Genre, Genre>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(genreMongoReader(mongoTemplate))
                .writer(genreJdbcWriter())
                .listener(new ItemReadListener<>() {
                    @Override
                    public void afterRead(@NonNull Genre genre) {
                        logger.info("Read Genre: {}", genre);
                    }
                })
                .listener(new ItemWriteListener<>() {
                    @Override
                    public void afterWrite(@NonNull Chunk<? extends Genre> items) {
                        for (Genre a : items) {
                            logger.info("Wrote genre: {}", a.toString());
                        }
                    }
                })
                .build();
    }

    @Bean
    public Step migrateAuthorStep() {
        return new StepBuilder(IMPORT_AUTHOR_JOB_NAME, jobRepository)
                .<Author, Author>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(authorMongoReader(mongoTemplate))
                .writer(authorJdbcWriter())
                .allowStartIfComplete(true)
                .listener(new ItemReadListener<>() {
                    @Override
                    public void afterRead(@NonNull Author author) {
                        logger.info("Read Author: {}", author);
                    }
                })
                .listener(new ItemWriteListener<>() {
                    @Override
                    public void afterWrite(@NonNull Chunk<? extends Author> items) {
                        for (Author a : items) {
                            logger.info("Wrote author: {}", a.toString());
                        }
                    }
                })
                .build();
    }

    @Bean
    public Step migrateBookStep() {
        return new StepBuilder(IMPORT_BOOK_JOB_NAME, jobRepository)
                .<Book, Book>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(bookMongoReader(mongoTemplate))
                .writer(bookJdbcWriter())
                .allowStartIfComplete(true)
                .listener(new ItemReadListener<>() {
                    public void afterRead(@NonNull Book book) {
                        logger.info("Read Book: {}", book);
                    }
                })
                .listener(new ItemWriteListener<>() {
                    @Override
                    public void afterWrite(@NonNull Chunk<? extends Book> items) {
                        for (Book a : items) {
                            logger.info("Wrote Book: {}", a.toString());
                        }
                    }
                })
                .build();
    }

    @Bean
    public Step migrateCommentStep() {
        return new StepBuilder(IMPORT_COMMENT_JOB_NAME, jobRepository)
                .<Comment, Comment>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(commentMongoReader(mongoTemplate))
                .writer(commentJdbcWriter())
                .allowStartIfComplete(true)
                .listener(new ItemReadListener<>() {
                    public void afterRead(@NonNull Comment comment) {
                        logger.info("Read Comment: {}", comment);
                    }
                })
                .listener(new ItemWriteListener<>() {
                    @Override
                    public void afterWrite(@NonNull Chunk<? extends Comment> items) {
                        for (Comment a : items) {
                            logger.info("Wrote Comment: {}", a.toString());
                        }
                    }
                })
                .build();
    }
}
