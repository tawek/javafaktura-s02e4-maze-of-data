package org.javafaktura.springdatademo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Persistent
@Data
public class Post {

    @Id
    Long id;

    @CreatedDate
    Instant date;

    String title;

    String content;

    String author;

    @MappedCollection(keyColumn = "name")
    Map<String, PostProperty> properties;

}
