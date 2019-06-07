package cn.itcast.travel.service;

import cn.itcast.travel.domain.User; /**
 * @author wtf
 * @date 2019/6/3 15:23
 */
public interface UserService {
    boolean regist(User user);

    boolean active(String code);

    User login(User user);
}
