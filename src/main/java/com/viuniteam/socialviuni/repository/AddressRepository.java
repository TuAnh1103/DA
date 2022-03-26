package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AddressRepository extends JpaRepository<Address,Long> {
    Address findOneById(Long id);
    Address findOneByName(String name);
    @Override
    List<Address> findAll();
    void deleteById(Long id);
}
