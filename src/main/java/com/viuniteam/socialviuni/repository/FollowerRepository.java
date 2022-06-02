package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Follower;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.entity.mapper.UserFollowers;
import com.viuniteam.socialviuni.entity.mapper.UserFollowings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface FollowerRepository extends JpaRepository<Follower,Long> {
    @Override
    void deleteById(Long id);

    Follower findOneById(Long id);

    Page<Follower> findByUserOrderByIdDesc(User user, Pageable pageable);

    @Query(value = "select flw.id, flw.user_id as userId, flw.created_date as createdDate from follower flw join user_followers u_flw on flw.id=u_flw.followers_id join user u on u.id = u_flw.user_id and u_flw.user_id=?1 order by flw.id desc",
            countQuery = "select count(*) from follower flw join user_followers u_flw on flw.id=u_flw.followers_id join user u on u.id = u_flw.user_id and u_flw.user_id=?1",
            nativeQuery = true)
    Page<UserFollowers> findByUserOrderByIdDesc(Long userId, Pageable pageable);


    @Query(value = "insert into user_followers(user_id,followers_id) values(?1,?2)", nativeQuery = true)
    @Modifying(clearAutomatically = true)
    @Transactional
    void insertUserFollower(Long userId, Long followerId);


    @Query(value = "delete from user_followers where user_id=?1 and followers_id=?2", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteUserFollowerByUserIdAndFollowerId(Long userId, Long followerId);

    @Query(value = "delete from follower where id=?1",nativeQuery = true)
    @Modifying
    @Transactional
    void deleteFollowerById(Long id);
}
