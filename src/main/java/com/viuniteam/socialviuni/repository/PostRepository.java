package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long>{
    List<Post> findByAuthor(User author);
    Post findOneByAuthorAndId(User author, Long id);
    void deleteById(Long id);
    Post save(Post post);
    Post findOneById(Long id);
    Page<Post> findAllByAuthor(User author,Pageable pageable);
}

