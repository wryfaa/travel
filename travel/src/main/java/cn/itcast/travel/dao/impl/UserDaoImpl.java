package cn.itcast.travel.dao.impl;/**
 * @author wtf
 * @date 2019/6/3 15:24
 */

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @program: travel
 * @description: UserDao实现类
 * @author: wtf
 * @create: 2019-06-03 15:24
 **/
public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * @Description: 根据用户名查询用户对象
     * @Param: [user]
     * @return: cn.itcast.travel.domain.User
     */
    @Override
    public User findByUsername(String username) {
        User user = null;
        //对查询过程进行异常处理，无论发生任何异常，直接返回null
        try {
            String sql = "select * from tab_user where username=?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {
        }
        return user;
    }

    /**
     * @Description: 保存用户信息
     * @Param: [user]
     * @return: void
     */
    @Override
    public void save(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)" +
                " values(?,?,?,?,?,?,?,?,?)";
        template.update(sql, user.getUsername(),
                user.getPassword(), user.getName(),
                user.getBirthday(), user.getSex(),
                user.getTelephone(), user.getEmail(),
                user.getStatus(), user.getCode());
    }

    /**
     * @Description: 根据激活码查询用户对象
     * @Param: [code]
     * @return: cn.itcast.travel.domain.User
     */
    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * @Description: 修改指定用户的激活码状态
     * @Param: [user]
     * @return: void
     */
    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        template.update(sql, user.getUid());

    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            String sql = "select * from tab_user where username=? and password=?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        } catch (Exception e) {
        }
        return user;
    }
}
