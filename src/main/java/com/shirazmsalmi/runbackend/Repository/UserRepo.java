package com.shirazmsalmi.runbackend.Repository;


import com.shirazmsalmi.runbackend.Entity.Defi;
import com.shirazmsalmi.runbackend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);


    User findIdByUsername(String username);
@Query("select d from Defi d where d.status=false ")
    Set<Defi> getdefi();
}
