package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Address;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    @Override
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);


    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    User findOneByEmail(String email);

    User findOneByUsername(String username);

    User findOneById(Long id);

    List<User> findByHomeTown(Address address);

    List<User> findByCurrentCity(Address address);

    Page<User> findAll(Specification specification, Pageable pageable);


    @Query(value = "select * from user where id not in (?1)",nativeQuery = true)
    Page<User> getListFriendSuggestion(String listUserId,Pageable pageable);

    @Query(value = "select * from user where id not in (?1) limit 1",nativeQuery = true)
    List<User> getListFriendSuggestion(String listUserId);
}
