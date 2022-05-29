package lt.codeacademy.springmvc.repository;

import lt.codeacademy.springmvc.repository.entity.Comment;
import lt.codeacademy.springmvc.repository.entity.Post;
import lt.codeacademy.springmvc.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost (Post post);

    List<Comment> findAllByUser (User user);
}
