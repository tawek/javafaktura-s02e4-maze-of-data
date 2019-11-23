package org.javafaktura.springdatademo;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {

    @Query("select * from post where title = :title")
    List<Post> findByTitle(String title);

    @Query("update post set author = :to where author = :from")
    @Modifying
    int changeAuthor(String from, String to);

    @Query("select * from post where title like :title")
    List<Post> findByTitleLike(String title);


}
