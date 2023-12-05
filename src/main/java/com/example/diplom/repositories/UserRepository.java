package com.example.diplom.repositories;

import com.example.diplom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, String> {


    Optional<User> findByLogin(String login);

    void deleteByLogin(String login);
}
