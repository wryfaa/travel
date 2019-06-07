package cn.itcast.travel.service.impl;/**
 * @author wtf
 * @date 2019/6/4 20:14
 */

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @program: travel
 * @description: categoryService的实现类
 * @author: wtf
 * @create: 2019-06-04 20:14
 **/
public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //用Redis缓存分类信息
        //1.从Redis中查询
        Jedis jedis = JedisUtil.getJedis();
        //1.2使用sortedset排序查询
//        Set<String> category = jedis.zrange("category", 0, -1);
        //1.3查询sortedset中的分数（cid）和值（cname）
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        //2.判断查询的集合是否为空、
        List<Category> cs = null;
        if (category == null || category.size() == 0) {
//            System.out.println("从数据库查询。。。");
            //3.如果为空，查询数据库，并将数据保存到Redis中
            cs = dao.findAll();
            //将集合的数据存储到Redis中的category的key
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());
            }
        } else {
//            System.out.println("从Redis查询。。。。。");
            //此处Redis中有数据，不会从数据库中更新数据
            //4.如果不为空，则表明不是第一次请求该数据，直接返回
            //将set中的数据存到list中
            cs = new ArrayList<Category>();
           /* for (String name :category ) {
                Category category1 = new Category();
                category1.setCname(name);
                cs.add(category1);
            }*/
            for (Tuple tuple : category) {
                Category category1 = new Category();
                category1.setCname(tuple.getElement());
                category1.setCid((int) tuple.getScore());
                cs.add(category1);
            }
            return cs;
        }
        return cs;
    }
}
