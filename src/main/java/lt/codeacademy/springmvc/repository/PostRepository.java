package lt.codeacademy.springmvc.repository;

import lt.codeacademy.springmvc.repository.entity.Post;
import lt.codeacademy.springmvc.repository.entity.SubPost;
import lt.codeacademy.springmvc.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
        List<Post> findByUser(User user);

         List<Post> findAllBySubPost(SubPost suPost);
    }

