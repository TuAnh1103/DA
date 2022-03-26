package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByAuthor(User author);
    void deleteById(Long id);
    Post save(Post post);
    Post findOneById(Long id);
}

