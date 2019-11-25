package org.javafaktura.springdatademo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    @Query("update Post p set p.author = :to where p.author = :from")
    @Modifying
    @Transactional
    int changeAuthor(String from, String to);

    Page<Post> findByTitleLike(String title, Pageable pageable);


}
