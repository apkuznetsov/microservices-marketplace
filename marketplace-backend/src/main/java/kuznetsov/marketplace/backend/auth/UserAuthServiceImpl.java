package kuznetsov.marketplace.backend.auth;

import kuznetsov.marketplace.backend.domain.Customer;
import kuznetsov.marketplace.backend.exception.ServiceException;
import kuznetsov.marketplace.backend.repository.CustomerRepository;
import kuznetsov.marketplace.backend.repository.UserRepository;
import kuznetsov.marketplace.backend.service.CustomerMapper;
import kuznetsov.marketplace.backend.service.CustomerPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static kuznetsov.marketplace.backend.auth.UserErrorCode.USER_ALREADY_EXISTS;
import static kuznetsov.marketplace.backend.auth.UserErrorCode.USER_EMAIL_ALREADY_CONFIRMED;
import static kuznetsov.marketplace.backend.auth.UserErrorCode.USER_EMAIL_NOT_CONFIRMED;
import static kuznetsov.marketplace.backend.auth.UserErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserAuthServiceImpl implements UserAuthService, UserDetailsService {

    private final UserAuthMapper userAuthMapper;
    private final CustomerMapper customerMapper;
    private final CustomerPublisher customerPublisher;

    private final UserRepository userRepo;
    private final CustomerRepository customerRepo;

    private final PasswordEncoder passwordEncoder;

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

    @Override
    @Transactional(readOnly = true)
    public UserAuthDto getUserAuthByEmail(String userEmail) {
        User user = userRepo
                .findByEmail(userEmail)
                .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));

        return userAuthMapper.toUserDto(user);
    }

    @Override
    public UserAuthDto confirmUserEmail(String userEmail) {
        User user = userRepo
                .findByEmail(userEmail)
                .orElseThrow(() -> new ServiceException(USER_NOT_FOUND));

        if (user.isEmailConfirmed()) {
            throw new ServiceException(USER_EMAIL_ALREADY_CONFIRMED);
        }

        user.setEmailConfirmed(true);
        User updatedUser = userRepo.saveAndFlush(user);

        return userAuthMapper.toUserDto(updatedUser);
    }

    @Override
    @Transactional
    public UserAuthDto registerCustomer(String email, String password) {
        Optional<User> optUser = userRepo.findByEmail(email);
        if (optUser.isPresent()) {
            throw new ServiceException(USER_ALREADY_EXISTS);
        }

        User newUser = userAuthMapper.toCustomerUser(email);
        newUser.setPassword(passwordEncoder.encode(password));
        User savedUser = userRepo.saveAndFlush(newUser);

        Customer customer = customerMapper.toCustomer(savedUser);
        customerRepo.saveAndFlush(customer);

        customerPublisher.publishCustomerRegistrationEvent(
                savedUser.getEmail(), savedUser.getRole().name());
        return userAuthMapper.toUserDto(savedUser);
    }

}
