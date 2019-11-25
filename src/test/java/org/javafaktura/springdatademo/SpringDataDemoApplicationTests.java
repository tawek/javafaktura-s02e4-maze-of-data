package org.javafaktura.springdatademo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    @Test
    @Transactional
    @Rollback(false)
    void testSaveLoadWithTags() {

        Post post = newPost("Tytuł1");
        post.setProperties(map(
                list(
                        property("contentType", "text/plain"),
                        property("tags", "spring data demo")
                ),
                $ -> $.getName()
        ));

        Post saved = postRepository.save(post);

        Post loadedPost = postRepository.findById(saved.getId())
                .orElseThrow(() -> notFound(saved.getId()));

        assertThat(loadedPost.getContent()).isEqualTo("Treść");
        assertThat(loadedPost.getProperties().size()).isEqualTo(2);
        assertThat(loadedPost.getProperties().get("tags").getValue()).isEqualTo("spring data demo");

        System.out.println(loadedPost);
    }

    private PostProperty property(String name, String value) {
        PostProperty postProperty = new PostProperty();
        postProperty.setName(name);
        postProperty.setValue(value);
        return postProperty;

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
    void testFindByTitle() {

        Post post = newPost("Tytuł2");

        Post saved = postRepository.save(post);

        List<Post> posts = postRepository.findByTitle("Tytuł2");

        assertThat(posts.size()).isEqualTo(1);
    }


    @Test
    void testFindByTitleLike() {

        Post post = newPost("Tytuł2L");

        Post saved = postRepository.save(post);

        List<Post> posts = postRepository.findByTitleLike("Tytuł2%");

        assertThat(posts.size()).isEqualTo(1);
    }

    @Test
    void testChangeAuthor() {

        Post post = newPost("Tytuł3");

        Post saved = postRepository.save(post);

        int modified = postRepository.changeAuthor("Tomek", "Rychu");

        assertThat(modified).isEqualTo(1);

        Post loadedPost = postRepository.findById(saved.getId())
                .orElseThrow(() -> notFound(saved.getId()));

        assertThat(loadedPost.getAuthor()).isEqualTo("Rychu");

    }


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
