package cn.itcast.travel.service.impl;/**
 * @author wtf
 * @date 2019/6/3 15:23
 */

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

/**
 * @program: travel
 * @description: userservice的实现类
 * @author: wtf
 * @create: 2019-06-03 15:23
 **/
public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    /**
     * @Description: 注册用户
     * @Param: [user]
     * @return: boolean
     */
    @Override
    public boolean regist(User user) {
        //1.根据输入的用户名查询用户对象（即用户是否存在）
        User user_byUseraname = dao.findByUsername(user.getUsername());
        //判断user_byUseraname是否为null
        if (user_byUseraname != null) {
            //用户名存在
            return false;
        }
        //用户名不存在
        //2.保存用户信息
        //2.1设置激活码 ,唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        dao.save(user);
        //3激活邮件的发送
        String content = "<a href='http://localhost:8888/travel/user/active?code=" + user.getCode() + " '>点击激活【旅游网】</a>";
        MailUtils.sendMail(user.getEmail(), content, "激活邮件");
        return true;
    }

    /**
     * @Description: 激活用户
     * @Param: [code]
     * @return: boolean
     */
    @Override
    public boolean active(String code) {
        User user = dao.findByCode(code);
        if (user != null) {
            //该激活码对应的用户存在，调用dao的方法修改其激活码的状态
            dao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }

    /** 
    * @Description: 登录验证的方法 
    * @Param: [user]
    * @return: cn.itcast.travel.domain.User 
    */
    @Override
    public User login(User user) {
        return dao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
