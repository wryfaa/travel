package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @author wtf
 * @date 2019/6/5 10:42
 */
public interface RouteDao {
    /*
    * 根据cid查询总记录数
    * */
    public int findTotalCount(int cid, String rname);

    /*
    * 根据cid ，start ，pageSize查询当前页面的数据集合*/
    public List<Route> findByPage(int cid, int start, int pageSize, String rname);

    /*根据id查询旅游线路的详细信息*/
    public Route findOne(int rid);
}
