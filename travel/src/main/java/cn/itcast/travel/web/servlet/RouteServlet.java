package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wtf
 * @date 2019/6/5 10:26
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    /**
     * @Description: 分页查询
     * @Param: [request, response]
     * @return: void
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        //接收rname路线名称
        String rname = request.getParameter("rname");
        //Tomcat7 会导致中文乱码
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");

        //2.处理参数
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }
        int pageSize = 0;  //每页显示的条数，不传默认每页显示5条数据
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }

        int currentPage = 0;  //当前页码，如果不传，默认为第一页
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            //第一次访问时设置默认页面为1
            currentPage = 1;
        }
        //3.调用service查询PageBean对象
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize, rname);
        //4.将pagebean序列化为json
        writeValue(pb, response);

    }

    /**
     * @Description: 根据id查询一个旅游线路的详细信息
     * @Param: [request, response]
     * @return: void
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收参数id
        String rid = request.getParameter("rid");
        //2.调用service查询route对象
        Route route = routeService.findOne(rid);
        //3将route写回到客户端
        writeValue(route, response);
    }

    /**
     * @Description: 判断当前登录用户是否收藏过该线路
     * @Param: [request, response]
     * @return: void
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取线路id
        String rid = request.getParameter("rid");
        //2.获取当前登录的用户user
        User user = (User) request.getSession().getAttribute("user");
        int uid; //用户的id
        if (user == null) {
            //用户尚未登录
            uid = 0;
        } else {
            //用户已经登录
            uid = user.getUid();
        }
        //3.调用FavoriteService 查询是否收藏
        boolean flag = favoriteService.ifFavorite(rid, uid);
        //写回客户端flag
        writeValue(flag, response);

    }

    /**
     * @Description: 添加收藏
     * @Param: [request, response]
     * @return: void
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取线路id
        String rid = request.getParameter("rid");
        //2.获取登录用户
        User user = (User) request.getSession().getAttribute("user");
        int uid; //用户的id
        if (user == null) {
            //用户尚未登录
            return;
        } else {
            //用户已经登录
            uid = user.getUid();
        }
        //3.根据rid 获取route对象
        favoriteService.add(rid, uid);
        //4.写回客户端

    }
}
