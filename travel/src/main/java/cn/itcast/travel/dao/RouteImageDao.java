package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * @author wtf
 * @date 2019/6/6 16:18
 */
public interface RouteImageDao {
    /** 
    * @Description: 根据route对象得到rid查询其图片信息集合
    * @Param: [rid] 
    * @return: java.util.List<cn.itcast.travel.domain.RouteImg> 
    */
    public List<RouteImg> findByRid(int rid);
}
