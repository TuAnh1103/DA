package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Friend;
import com.viuniteam.socialviuni.entity.User;
import com.viuniteam.socialviuni.entity.mapper.UserFollowings;
import com.viuniteam.socialviuni.entity.mapper.UserFriends;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface FriendRepository extends JpaRepository<Friend,Long> {


    @Modifying
    @Query(value = "delete from user_friends where user_id=?1 and friends_id = ?2",nativeQuery = true)
    void deleteUserFriendByUserIdAndFriendId(Long userId, Long friendId);


    @Modifying
    @Query(value = "delete from friend where id=?1",nativeQuery = true)
    void deleteFriendById(Long id);


    @Modifying
    @Query(value = "delete from user_friends where friends_id = ?1",nativeQuery = true)
    void deleteUserFriends(Long id);

    Page<Friend> findByUserOrderByIdDesc(User user,Pageable pageable);


    @Query(value = "insert into user_friends(user_id,friends_id) values(?1,?2)", nativeQuery = true)
    @Modifying(clearAutomatically = true)
    @Transactional
    void insertUserFriend(Long userId, Long friendId);

    @Query(value = "select fr.id, fr.user_id as userId, fr.created_date as createdDate from friend fr join user_friends u_fr on fr.id=u_fr.friends_id join user u on u.id = u_fr.user_id and u_fr.user_id=?1 order by fr.id desc",
            countQuery = "select count(*) from friend fr join user_friends u_fr on fr.id=u_fr.friends_id join user u on u.id = u_fr.user_id and u_fr.user_id=?1",
            nativeQuery = true)
    Page<UserFriends> findByUserOrderByIdDesc(Long userId, Pageable pageable);

    @Query(value = "select fr.id, fr.user_id as userId, fr.created_date as createdDate from friend fr join user_friends u_fr on fr.id=u_fr.friends_id join user u on u.id = u_fr.user_id and u_fr.user_id=?1 order by fr.id desc",
            countQuery = "select count(*) from friend fr join user_friends u_fr on fr.id=u_fr.friends_id join user u on u.id = u_fr.user_id and u_fr.user_id=?1",
            nativeQuery = true)
    List<UserFriends> findByUserOrderByIdDesc(Long userId);
}
