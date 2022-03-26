package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

@Transactional
public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long> {
    void deleteById(Long id);

    void deleteFriendRequestById(Long id);

    @Modifying
    @Query(value = "delete from user_friend_requests where friend_requests_id = ?1",nativeQuery = true)
    void deleteUserFriendRequests(Long id);
}
