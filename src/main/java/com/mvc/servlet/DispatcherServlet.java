package com.mvc.servlet;

import com.mvc.annotation.RequestMapping;
import com.mvc.annotation.RequestParam;
import com.mvc.context.WebApplicationContext;
import com.mvc.controller.UserController;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DispatcherServlet extends HttpServlet {
    private WebApplicationContext webApplicationContext;
    @Override
    public void init() throws ServletException {
        //servelet初始化的时候读取参数 classpath:springmvc.xml
        String contextConfigLocation = this.getServletConfig().getInitParameter("contextConfigLocation");
        //创建spring容器
        webApplicationContext = new WebApplicationContext(contextConfigLocation);
        //初始化spring容器
        webApplicationContext.refresh();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //requestURI-------- /user/query
        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        String uri = requestURI.replace(contextPath, "");
        Method method = (Method) webApplicationContext.handlerMap.get(uri);
        if (method == null) {
            return;
        }
        UserController instance = (UserController) webApplicationContext.iocMap.get("/" + uri.split("/")[1]);
        try {
            method.invoke(instance, hand(req, resp, method));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
    private static Object[] hand(HttpServletRequest req, HttpServletResponse res, Method method) {
        //拿到待执行方法有哪些参数
        Class<?>[] parameterTypes = method.getParameterTypes();
        //将方法里的所有参数复制到args
        Object[] args = new Object[parameterTypes.length];
        int args_i = 0;
        //index为参数下表
        int index = 0;
        for (Class<?> parameterType : parameterTypes) {
            if (ServletRequest.class.isAssignableFrom(parameterType)) {
                args[args_i++] = req;
            }
            if (ServletResponse.class.isAssignableFrom(parameterType)) {
                args[args_i++] = res;
            }
            //获得每个参数的注解，因为一个参数可以有多个注解
            Annotation[] parameterAnnotation = method.getParameterAnnotations()[index];

            if (parameterAnnotation.length > 0) {
                for (Annotation annotation : parameterAnnotation) {
                    if (RequestParam.class.isAssignableFrom(annotation.getClass())) {
                        RequestParam annotation1 = (RequestParam) annotation;
                        args[args_i++] = req.getParameter(annotation1.value());
                    }
                }
            }
            index++;
        }
        return args;

    }
}
