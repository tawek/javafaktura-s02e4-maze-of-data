package org.javafaktura.springdatademo;

import lombok.Data;
import org.hibernate.annotations.Parent;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MapKey;

@Embeddable
@Data
public class PostProperty {

    @Parent
    Long post;

    @Column(insertable = false, updatable = false)
    String name;

    String value;
}
