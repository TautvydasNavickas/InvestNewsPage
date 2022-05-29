package lt.codeacademy.springmvc.repository.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;


import lombok.Data;

import java.time.Instant;


@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    @Email
    private String email;
    private Instant time;
    private Instant created;
    private boolean enabled;
}
