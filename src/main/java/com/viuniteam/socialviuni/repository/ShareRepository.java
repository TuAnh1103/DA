package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.Share;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShareRepository extends JpaRepository<Share,Long> {
    Share findOneById(Long id);
    void deleteById(Long id);
    Long countByPost(Post post);
    Share findTop1ByPostOrderByCreatedDateDesc(Post post);

    @Query(value = "select count(*) from (select count(s.id) from Share s where s.post_id=:postId group by s.user_id) t", nativeQuery = true)
    Long countByPostGroupByUser(@Param("postId") Long postId);

    Page<Share> findAllByUserOrderByIdDesc(User user, Pageable pageable);

    List<Share> findByUserOrderByIdDesc(User user);
}
