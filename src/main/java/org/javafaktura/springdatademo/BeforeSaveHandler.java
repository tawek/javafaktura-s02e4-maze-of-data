package org.javafaktura.springdatademo;

import org.springframework.data.cassandra.core.cql.CqlIdentifier;
import org.springframework.data.cassandra.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BeforeSaveHandler implements BeforeConvertCallback {

    @Override
    public Object onBeforeConvert(Object entity, CqlIdentifier tableName) {
        if (entity instanceof Post) {
            Post post = (Post) entity;
            if (post.getId() == null) {
                post.setId(UUID.randomUUID());
            }
        }
        return entity;
    }
}
