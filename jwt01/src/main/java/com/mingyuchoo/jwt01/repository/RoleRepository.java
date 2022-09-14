package com.mingyuchoo.jwt01.repository;

import com.mingyuchoo.jwt01.models.ERole;
import com.mingyuchoo.jwt01.models.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
