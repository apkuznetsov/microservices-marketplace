package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.database.user.UserRepository;
import kuznetsov.marketplace.domain.user.User;
import kuznetsov.marketplace.services.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

  private final UserInfoMapper userInfoMapper;

  private final UserRepository userRepo;

  public UserInfoDto getUserInfoByEmailOrNull(String userEmail) {
    User user = userRepo.findByEmail(userEmail)
        .orElse(null);

    return user != null
        ? userInfoMapper.toUserInfo(user)
        : null;
  }

}
