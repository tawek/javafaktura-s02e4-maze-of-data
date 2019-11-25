package org.javafaktura.springdatademo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByTitle(String title);

    @Query("update Post p set p.author = :to where p.author = :from")
    @Modifying
    @Transactional
    int changeAuthor(String from, String to);

    List<Post> findByTitleLike(String title);

    Page<Post> findByTitleLike(String title, Pageable pageable);

    @Async
    CompletableFuture<List<Post>> findByContentIgnoreCaseLike(String search);


}
