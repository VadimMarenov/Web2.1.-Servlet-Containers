package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private AtomicLong idCounter = new AtomicLong();
    private ConcurrentMap<Long, Post> allPosts;

    public PostRepository() {
        this.allPosts = new ConcurrentHashMap<>();
    }

    public Collection<Post> all() {
        return allPosts.values();
    }

    public Optional<Post> getById(long id) {
        //return Optional.of(allPosts.get(id));
        return allPosts.get(id) != null ? Optional.of(allPosts.get(id)) : Optional.empty();
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long id = idCounter.incrementAndGet();
            post.setId(id);
            allPosts.put(id, post);
        } else {
            allPosts.put(post.getId(), post);
        }
        return post;

    }

    public void removeById(long id) {
        if (allPosts.get(id) != null) {
            allPosts.remove(id);
        }
    }
}
