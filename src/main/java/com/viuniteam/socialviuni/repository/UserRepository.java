package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Address;
import com.viuniteam.socialviuni.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

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

}
