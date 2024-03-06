package kuznetsov.gists.securitymocking;

import org.springframework.stereotype.Service;

@Service
public class SomeUserServiceImpl implements SomeUserService {

    @Override
    public String findUserOrNullByEmail(String email) {
        return "example@example.com";
    }

}
