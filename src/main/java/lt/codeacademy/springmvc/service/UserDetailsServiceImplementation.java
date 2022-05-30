package lt.codeacademy.springmvc.service;

import lombok.AllArgsConstructor;
import lt.codeacademy.springmvc.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;


@Service
@AllArgsConstructor
public class UserDetailsServiceImplementation implements UserDetailsService {
    private  UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        Optional<lt.codeacademy.springmvc.repository.entity.User> userOptional = userRepository.findByUsername(username);
        lt.codeacademy.springmvc.repository.entity.User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No user " +
                        "Found with username : " + username));

        return new org.springframework.security
                .core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true,
                true, getAuthorities());
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        return singletonList(new SimpleGrantedAuthority("USER"));
    }
}
