package dev.finaticbackend.repositories;

import dev.finaticbackend.entities.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BaseUser, String> {
    Optional<BaseUser> findByEmailAddress(String username);
}
