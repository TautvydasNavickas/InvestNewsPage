package lt.codeacademy.springmvc.repository;

import lt.codeacademy.springmvc.repository.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationRepository  extends JpaRepository<Verification, Long> {
    Optional<Verification> findByToken(String verification);
}
