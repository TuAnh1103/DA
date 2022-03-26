package com.viuniteam.socialviuni.repository;

import com.viuniteam.socialviuni.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findAllByIdIn(List<Long> ids);
    Role findOneByName(String name);
}
