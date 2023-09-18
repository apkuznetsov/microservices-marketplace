package kuznetsov.marketplace.backend.auth.service;

import kuznetsov.marketplace.backend.users.domain.User;
import kuznetsov.marketplace.backend.users.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        if (!user.isEmailConfirmed()) {
            throw new UsernameNotFoundException("user email not confirmed");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Set.of(
                        new SimpleGrantedAuthority(user.getRole().toString())
                )
        );
    }

}
