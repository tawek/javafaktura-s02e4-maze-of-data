package org.javafaktura.springdatademo;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.UUID;

public interface PostRepository extends CassandraRepository<Post, UUID> {

    Slice<Post> findByTitleLike(String title, Pageable pageable);


}
