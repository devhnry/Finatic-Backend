package dev.finaticbackend.repositories;

import dev.finaticbackend.entities.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<AuthToken, Long> {
    Optional<AuthToken> findByAccessToken(String token);
}
