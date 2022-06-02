package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Following;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.entity.mapper.UserFollowings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;


public interface FollowingRepository extends JpaRepository<Following,Long> {
    @Override
    void deleteById(Long id);

    Page<Following> findByUserOrderByIdDesc(User user, Pageable pageable);

    @Query(value = "select flw.id, flw.user_id as userId, flw.created_date as createdDate from following flw join user_followings u_flw on flw.id=u_flw.followings_id join user u on u.id = u_flw.user_id and u_flw.user_id=?1 order by flw.id desc",
            countQuery = "select count(*) from following flw join user_followings u_flw on flw.id=u_flw.followings_id join user u on u.id = u_flw.user_id and u_flw.user_id=?1",
            nativeQuery = true)
    Page<UserFollowings> findByUserOrderByIdDesc(Long userId, Pageable pageable);


    @Query(value = "insert into user_followings(user_id,followings_id) values(?1,?2)", nativeQuery = true)
    @Modifying(clearAutomatically = true)
    @Transactional
    void insertUserFollowing(Long userId, Long followingId);

    @Query(value = "delete from user_followings where user_id=?1 and followings_id=?2", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteUserFollowingByUserIdAndFollowingId(Long userId, Long followingId);

    @Query(value = "delete from following where id=?1",nativeQuery = true)
    @Modifying
    @Transactional
    void deleteFollowingById(Long id);
}
