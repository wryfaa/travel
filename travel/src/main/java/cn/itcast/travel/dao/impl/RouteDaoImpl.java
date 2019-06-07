package cn.itcast.travel.dao.impl;/**
 * @author wtf
 * @date 2019/6/5 10:48
 */

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: travel
 * @description: RouteDaoImpl的实现类
 * @author: wtf
 * @create: 2019-06-05 10:48
 **/
public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

/*    @Override
    public int findTotalCount(int cid,String rname) {
        //String sql = "select count(*) from tab_route where cid = ?";
        //1.定义sql模板
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if(cid != 0){
            sb.append( " and cid = ? "+);

            params.add(cid);//添加？对应的值
        }

        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");

            params.add("%"+rname+"%");
        }

        sql = sb.toString();


        return template.queryForObject(sql,Integer.class,params.toArray());
    }*/

  /*  @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
        //String sql = "select * from tab_route where cid = ? and rname like ?  limit ? , ?";
        String sql = " select * from tab_route where 1 = 1 ";
        //1.定义sql模板
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if(cid != 0){
            sb.append( " and cid = ? ");

            params.add(cid);//添加？对应的值
        }

        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");

            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");//分页条件

        sql = sb.toString();

        params.add(start);
        params.add(pageSize);


        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }*/

    @Override
    public int findTotalCount(int cid,String rname) {
//        String sql = "select count(*) from tab_route where cid=?";
        //组合查询，两个参数可以存在某一个或者两个都存在
        //定义sql模板  注意 1=1 后面要留空格 拼接字符串时 1=1and cid = xx
        //                                                1=1 and cid = xx
        String sql = "select count(*) from tab_route where 1 =1 ";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();  //条件的集合
        //判断参数是否有值
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);  //添加条件对应的值
        }
        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");  //模糊查询拼接字符串，，此处不能直接将rname添加到list集合中
        }
        sql = sb.toString();

        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
//        String sql = "select * from tab_route where cid = ? limit ? , ?";
        String sql = "select * from tab_route where 1 =1 ";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();  //条件的集合
        //判断参数是否有值
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);  //添加条件对应的值
        }
        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");  //模糊查询拼接字符串，，此处不能直接将rname添加到list集合中
        }

        //分页的条件
        sb.append(" limit ? , ? ");
        sql = sb.toString();

        params.add(start);
        params.add(pageSize);
        List<Route> list = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
        System.out.println(list);
        return list;
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class),rid);
    }

}
