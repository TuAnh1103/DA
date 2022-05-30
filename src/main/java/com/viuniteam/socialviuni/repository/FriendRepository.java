package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Friend;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface FriendRepository extends JpaRepository<Friend,Long> {

    void deleteFriendById(Long id);

    @Modifying
    @Query(value = "delete from user_friends where friends_id = ?1",nativeQuery = true)
    void deleteUserFriends(Long id);

    Page<Friend> findByUserOrderByIdDesc(User user,Pageable pageable);

}
