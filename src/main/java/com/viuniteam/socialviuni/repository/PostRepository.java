package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long>{
    Post findOneByAuthorAndId(User author, Long id);
    void deleteById(Long id);
    Post save(Post post);
    Post findOneById(Long id);
    Page<Post> findAllByAuthorOrderByIdDesc(User author,Pageable pageable);

    Page<Post> findAll(Specification specification, Pageable pageable);

    List<Post> findByAuthorOrderByIdDesc(User author);
}

