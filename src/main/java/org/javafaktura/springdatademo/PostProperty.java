package org.javafaktura.springdatademo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;

@Persistent
@Data
public class PostProperty {

    Long post;

    String name;

    String value;
}
