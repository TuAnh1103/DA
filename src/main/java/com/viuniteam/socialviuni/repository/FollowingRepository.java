package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Following;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowingRepository extends JpaRepository<Following,Long> {
    @Override
    void deleteById(Long id);
}
