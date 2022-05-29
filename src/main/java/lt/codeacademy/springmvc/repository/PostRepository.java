package lt.codeacademy.springmvc.repository;

import lt.codeacademy.springmvc.repository.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
        List<Post> findByUser();
    }

