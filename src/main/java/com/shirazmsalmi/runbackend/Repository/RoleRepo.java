package com.shirazmsalmi.runbackend.Repository;




import com.shirazmsalmi.runbackend.Entity.Role;
import com.shirazmsalmi.runbackend.Enum.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,String> {
    Optional<Role> findByName (ERole name);
    boolean existsByName(ERole r1);
}
