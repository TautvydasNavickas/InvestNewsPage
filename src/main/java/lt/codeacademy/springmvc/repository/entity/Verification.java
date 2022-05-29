package lt.codeacademy.springmvc.repository.entity;

import lombok.Data;

import javax.persistence.*;

import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name = "token")
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @OneToOne(fetch = LAZY)
    private User user;
    private Instant expiryDate;

}
