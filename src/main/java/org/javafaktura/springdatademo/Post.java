package org.javafaktura.springdatademo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.cassandra.core.mapping.Indexed;

import java.time.Instant;
import java.util.UUID;

@Persistent
@Data
public class Post {

    @Id
    UUID id;

    @CreatedDate
    Instant date;

    String title;

    String content;

    String author;

}
