package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author wtf
 * @date 2019/6/4 20:06
 */
public interface CategoryDao {
    /** 
    * @Description: 查询所有 
    * @Param: [] 
    * @return: java.util.List<cn.itcast.travel.domain.Category> 
    */
    public List<Category> findAll();
}
