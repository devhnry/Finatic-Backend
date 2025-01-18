package dev.finaticbackend.repositories;

import dev.finaticbackend.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Optional<Company> findByEmailAddress(String emailAddress);
}
