package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author wtf
 * @date 2019/6/4 16:31
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();

    /**
     * @Description: 注册功能
     * @Param: [request, response]
     * @return: void
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码的校验
        String check = request.getParameter("check");
        //获取程序生成的验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //为了避免验证码的复用，设置验证码的时效
        session.removeAttribute("CHECKCODE_SERVER");
        //判断验证码是否正确

       /* if (check != null && checkcode_server != null) {
            if (!checkcode_server.equalsIgnoreCase(check)) {
                //验证码错误,添加错误信息
                ResultInfo info = new ResultInfo();
                info.setFlag(false);
                info.setErrorMsg("验证码错误");
                //将info序列化为json
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(info);
                //将json数据响应给客户端
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(json);
                return;
            }
        }*/
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误,添加错误信息
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //将json数据响应给客户端
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }
        //验证码正确继续执行下面的代码

        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用service方法
//        UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        //将后端返回的数据封装为对象，将其转换为json发送给浏览器
        ResultInfo info = new ResultInfo();
        //4.响应结果
        if (flag) {
            //注册成功
            info.setFlag(true);
        } else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }
        //将info序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json数据响应给客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }

    /**
     * @Description: 登录功能
     * @Param: [request, response]
     * @return: void
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取输入的用户名和密码
        Map<String, String[]> map = request.getParameterMap();
        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用service方法
//        UserService service = new UserServiceImpl();
        User u = service.login(user);
        //4.判断用户u是否为null
        ResultInfo info = new ResultInfo();
        if (u == null) {
            //用户名或密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        //5.判断用户是否激活
        if (u != null && !"Y".equals(u.getStatus())) {
            //用户未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请登录邮箱激活");
        }
        //6.判断登录成功
        if (u != null && "Y".equals(u.getStatus())) {
            //将数据存入到session中
            request.getSession().setAttribute("user", u);
            //登录成功
            info.setFlag(true);
        }
        //7.响应数据
        //将错误数据对象转换为json
        //响应该jso
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }

    /**
     * @Description: 查询单个对象
     * @Param: [request, response]
     * @return: void
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.从session中获取登录用户 错误：获取后的user为nul
        Object user = request.getSession().getAttribute("user");
//        System.out.println(user);

        //2.将user写会给客户端
       /* ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), user);*/
        writeValue(user, response);
    }

    /**
     * @Description: 退出功能
     * @Param: [request, response]
     * @return: void
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.销毁session
        request.getSession().invalidate();
        //2.页面跳转到登录页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * @Description: 激活功能
     * @Param: [request, response]
     * @return: void
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        if (code != null) {
            //2.激活码存在,调用service方法
//            UserService service = new UserServiceImpl();
            boolean flag = service.active(code);

            //3.判断标记
            String msg = null;
            if (flag) {
                //激活成功
                msg = "激活成功,请<a href='login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败,请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}
