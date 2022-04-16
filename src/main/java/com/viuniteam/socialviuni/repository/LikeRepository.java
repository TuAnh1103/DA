package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Like;
import com.viuniteam.socialviuni.entity.Post;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface LikeRepository extends JpaRepository<Like,Long> {
    Long countByPostAndStatus(Post post,boolean status);
    Like findOneByPostAndUser(Post post, User user);
    Boolean existsByPostAndUserAndStatus(Post post, User user, boolean status);
    Like findTop1ByPostAndStatusOrderByCreatedDateDesc(Post post, boolean status);
}
