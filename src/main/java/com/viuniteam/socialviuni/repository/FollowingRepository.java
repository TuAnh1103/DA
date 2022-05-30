package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Follower;
import com.viuniteam.socialviuni.entity.Following;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowingRepository extends JpaRepository<Following,Long> {
    @Override
    void deleteById(Long id);
    Page<Following> findByUserOrderByIdDesc(User user, Pageable pageable);
}
