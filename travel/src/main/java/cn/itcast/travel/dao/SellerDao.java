package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

/**
 * @author wtf
 * @date 2019/6/6 16:31
 */
public interface SellerDao {
    public Seller findById(int id);
}
