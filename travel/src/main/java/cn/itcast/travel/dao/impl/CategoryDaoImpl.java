package cn.itcast.travel.dao.impl;/**
 * @author wtf
 * @date 2019/6/4 20:07
 */

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @program: travel
 * @description: categoryDao实现类
 * @author: wtf
 * @create: 2019-06-04 20:07
 **/
public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
//        return template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        List<Category> list = template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        System.out.println(list);

        return list;
    }
}
