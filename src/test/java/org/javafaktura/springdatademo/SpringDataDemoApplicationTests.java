package org.javafaktura.springdatademo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringDataDemoApplicationTests {

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    public void setup() {
        postRepository.deleteAll();
    }

    @Test
    void testSaveLoad() {

        Post post = newPost("Tytuł1");

        Post saved = postRepository.save(post);

        Post loadedPost = postRepository.findById(saved.getId())
                .orElseThrow(() -> notFound(saved.getId()));

        assertThat(loadedPost.getContent()).isEqualTo("Treść");
    }

    private <T> Set<T> set(T... s) {
        return Arrays.stream(s).collect(toSet());
    }

    private <T> List<T> list(T... s) {
        return Arrays.stream(s).collect(toList());
    }

    private <K, T> Map<K, T> map(List<T> elements, Function<T, K> keyExtractor) {
        return elements.stream().collect(toMap(keyExtractor, $ -> $));
    }

    @Test
    void testFindByTitleLikePaging() {

        for (int i = 0; i <15; i++) {
            Post post = newPost("Tytuł #" +i);
            Post saved = postRepository.save(post);
        }


        Slice<Post> posts = postRepository.findByTitleLike("Tytuł #%", CassandraPageRequest.of(0, 10));
        System.out.println(posts.getContent());
        assertThat(posts.getNumberOfElements()).isEqualTo(10);

        Pageable nextPage = posts.nextOrLastPageable();
        Slice<Post> posts2 = postRepository.findByTitleLike("Tytuł #%", nextPage);
        System.out.println(posts2.getContent());
        assertThat(posts2.getNumberOfElements()).isEqualTo(6); // BUG (repeated first row from previous page)


    }

//    @Test
//    void testChangeAuthor() {
//
//        Post post = newPost("Tytuł3");
//
//        Post saved = postRepository.save(post);
//
//        int modified = postRepository.changeAuthor("Tomek", "Rychu");
//
//        assertThat(modified).isEqualTo(1);
//
//        Post loadedPost = postRepository.findById(saved.getId())
//                .orElseThrow(() -> notFound(saved.getId()));
//
//        assertThat(loadedPost.getAuthor()).isEqualTo("Rychu");
//
//    }


    private Post newPost(String title) {
        Post post = new Post();
        post.setAuthor("Tomek");
        post.setContent("Treść");
        post.setTitle(title);
        return post;
    }

    private <X extends Throwable> RuntimeException notFound(Object id) {
        return new RuntimeException("Cannot find '" + id + "'");
    }

}
