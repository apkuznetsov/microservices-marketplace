package kuznetsov.marketplace.services.user;

import static kuznetsov.marketplace.services.user.UserErrorCode.USER_EMAIL_NOT_CONFIRMED;
import static kuznetsov.marketplace.services.user.UserErrorCode.USER_NOT_FOUND;

import java.util.Set;
import kuznetsov.marketplace.database.user.UserRepository;
import kuznetsov.marketplace.domain.user.User;
import kuznetsov.marketplace.services.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
    User user = userRepo.findByEmail(userEmail)
        .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));
    if (!user.isEmailConfirmed()) {
      throw new ServiceException(USER_EMAIL_NOT_CONFIRMED);
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
