package ru.otus.library.repositories;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.library.models.Book;


@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void removeCommentArrayElementsById(String commentId) {
        val query = Query.query(Criteria.where("$id").is(new ObjectId(commentId)));
        val update = new Update().pull("comments", query);
        mongoTemplate.updateMulti(new Query(), update, Book.class);
    }
}
