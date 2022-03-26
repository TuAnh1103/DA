package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FollowerRepository extends JpaRepository<Follower,Long> {
    @Override
    void deleteById(Long id);

}
