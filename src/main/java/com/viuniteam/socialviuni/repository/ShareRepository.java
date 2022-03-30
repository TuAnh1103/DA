package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareRepository extends JpaRepository<Share,Long> {
    Share findOneById(Long id);
    void deleteById(Long id);
    Long countByPost(Post post);
}
