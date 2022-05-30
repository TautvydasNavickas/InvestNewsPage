package lt.codeacademy.springmvc.repository.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
@Table(name="vote")
@Data
@Entity
@Builder
public class Vote{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private VoteType type;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "id")
    private Post post;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
}
