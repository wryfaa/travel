package cn.itcast.travel.service.impl;/**
 * @author wtf
 * @date 2019/6/5 10:39
 */

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImageDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImageDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

/**
 * @program: travel
 * @description: RouteService的实现类
 * @author: wtf
 * @create: 2019-06-05 10:39
 **/
public class RouteServiceImpl implements RouteService {
    private RouteDao dao = new RouteDaoImpl();
    private RouteImageDao imageDao = new RouteImageDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        //封装PageBean对象
        PageBean<Route> pb = new PageBean<Route>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        //设置总记录数
        int totalCount = dao.findTotalCount(cid, rname);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;  //开始的记录数。，（当前页码-1）*每页显示的条数
        List<Route> routeList = dao.findByPage(cid, start, pageSize, rname);
        pb.setList(routeList);
        //设置总页数..   总记录数%每页显示的条数 == 0 ？  总记录数/每页显示的条数:  总记录数/每页显示的条数+1
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);

//        System.out.println(pb);

        return pb;
    }

    /** 
    * @Description: 根据id查询旅游线路的详细信息 
    * @Param: [rid] 
    * @return: cn.itcast.travel.domain.Route 
    */
    @Override
    public Route findOne(String rid) {
        //1.根据id查询tab_route表中对应的route对象
        Route route = dao.findOne(Integer.parseInt(rid));
        //2.根据route的rid查询图片信息的集合  tab_route_img 表
        List<RouteImg> routeImgList = imageDao.findByRid(route.getRid());
        //3.将集合设置到route对象中
        route.setRouteImgList(routeImgList);
        //3.根据route的sid查询卖家信息  tab_seller表
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);
        //4.查询收藏次数  tab_favorite表
        int count = favoriteDao.findCoutByRid(route.getRid());
        route.setCount(count);

        return route;
    }
}
