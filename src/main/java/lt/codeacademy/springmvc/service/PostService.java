package lt.codeacademy.springmvc.service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.springmvc.DTO.PostRequest;
import lt.codeacademy.springmvc.DTO.PostResponse;
import lt.codeacademy.springmvc.exception.PostNotFoundException;
import lt.codeacademy.springmvc.exception.SubPostNotFoundException;
import lt.codeacademy.springmvc.mapper.PostMapper;
import lt.codeacademy.springmvc.repository.PostRepository;
import lt.codeacademy.springmvc.repository.SubPostRepository;
import lt.codeacademy.springmvc.repository.UserRepository;
import lt.codeacademy.springmvc.repository.entity.Post;
import lt.codeacademy.springmvc.repository.entity.SubPost;
import lt.codeacademy.springmvc.repository.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

        private PostRepository postRepository;
        private UserRepository userRepository;
        private AuthorizationService authorizationService;
        private PostMapper postMapper;
        private SubPostRepository subPostRepository;

        public void save(PostRequest postRequest) {
            SubPost subPost = subPostRepository.findByName(postRequest.getSubredditName())
                    .orElseThrow(() -> new SubPostNotFoundException(postRequest.getSubredditName()));
            postRepository.save(postMapper.map(postRequest, subPost, authorizationService.getCurrentUser()));
        }

        @Transactional(readOnly = true)
        public PostResponse getPost(Long id) {
            Post post = postRepository.findById(id)
                    .orElseThrow(() -> new PostNotFoundException(id.toString()));
            return postMapper.mapToDto(post);
        }

        @Transactional(readOnly = true)
        public List<PostResponse> getAllPosts() {
            return postRepository.findAll()
                    .stream()
                    .map(postMapper::mapToDto)
                    .collect(toList());
        }

        @Transactional(readOnly = true)
        public List<PostResponse> getPostsBySubreddit(Long subPostId) {
            SubPost subPost = subPostRepository.findById(subPostId)
                    .orElseThrow(() -> new SubPostNotFoundException(subPostId.toString()));
            List<Post> posts = postRepository.findAllBySubPost(subPost);
            return posts.stream().map(postMapper::mapToDto).collect(toList());
        }

        @Transactional(readOnly = true)
        public List<PostResponse> getPostsByUsername(String username) {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));
            return postRepository.findByUser(user)
                    .stream()
                    .map(postMapper::mapToDto)
                    .collect(toList());
        }
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername((String) principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }
}
