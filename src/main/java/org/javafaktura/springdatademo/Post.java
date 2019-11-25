package org.javafaktura.springdatademo;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.Map;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @CreatedDate
    Instant date;

    String title;

    String content;

    String author;

    @ElementCollection
    @CollectionTable(
            name = "post_property",
            joinColumns = @JoinColumn(name = "post")
    )
    @MapKeyColumn(name = "name")
    Map<String, PostProperty> properties;

}
