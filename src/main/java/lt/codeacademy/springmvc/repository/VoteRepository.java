package lt.codeacademy.springmvc.repository;

import lt.codeacademy.springmvc.repository.entity.Post;
import lt.codeacademy.springmvc.repository.entity.User;
import lt.codeacademy.springmvc.repository.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
