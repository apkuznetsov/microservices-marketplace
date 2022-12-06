package kuznetsov.marketplace.services.user;

import kuznetsov.marketplace.services.user.dto.UserInfoDto;

public interface UserInfoService {

  UserInfoDto getUserInfoByEmailOrNull(String userEmail);

}
