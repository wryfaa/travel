package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * @author wtf
 * @date 2019/6/4 20:13
 */
public interface CategoryService {
    public List<Category> findAll();
}
