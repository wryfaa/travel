package cn.itcast.travel.web.servlet;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author wtf
 * @date 2019/6/4 16:33
 */
public class BaseServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        System.out.println("baseservlet被执行了");
        //完成方法分发 例如：http://localhost:8888/travel/user/add
        //1.获取请求路径
        String uri = req.getRequestURI();  //  /travel/user/add
//        System.out.println("请求的uri:" + uri);
        //2.获取方法名称  add
        //从最后一个斜杠的位置截取
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
//        System.out.println("方法名称:" + methodName);
        //3.获取方法对象Method(反射)
//        System.out.println(this);   谁调用service方法this代表谁，service方法是userServlet调用的
        //cn.itcast.travel.web.servlet.UserServlet@5a07beda
        try {
            //获取方法
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //4.执行方法
            //暴力反射
            //method.setAccessible(true);
            method.invoke(this, req, res);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        try {
            //获取方法
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //4.执行方法
            //暴力反射
            //method.setAccessible(true);
            method.invoke(this,req,res);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 将传入的对象序列化为json，并写会给客户端
     * @Param: [object]
     * @return: void
     */
    public void writeValue(Object object, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), object);
    }

    /**
     * @Description: 将传入的对象，序列化为string，返还给调用者
     * @Param: [object]
     * @return: java.lang.String
     */
    public String writeAsString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
