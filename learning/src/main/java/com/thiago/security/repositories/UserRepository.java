package com.thiago.security.repositories;

import com.thiago.security.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("SELECT u from User u where u.userName = :userName")
    Optional<User> findByUserName(@Param("userName") String username);
//    User findByUserName(@Param("userName") String userName);
}
