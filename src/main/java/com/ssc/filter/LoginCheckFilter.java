package com.ssc.filter;

import com.alibaba.fastjson.JSON;
import com.ssc.common.BaseContext;
import com.ssc.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName LoginCheckFilter
 * @Authoc 孙少聪
 * @Date 2022/8/21 10:29:25
 */

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    // 路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取请求路径
        String requestURI = request.getRequestURI();
        // 定义不需要处理的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };
        // 路径匹配
        boolean check = checkURL(urls, requestURI);
        Long employee =(Long) request.getSession().getAttribute("employee");
        /**
         * 网页端
         */
        if (check || employee != null){

            // 将用户id放进线程中去，以方便后来字段自动填充
            BaseContext.setCurrentId(employee);

            filterChain.doFilter(request, response);
            return;
        }

        /**
         * 手机端
          */
        Long user =(Long) request.getSession().getAttribute("user");
        if (check || user != null){
            // 将用户id放进线程中去，以方便后来字段自动填充
            BaseContext.setCurrentId(user);

            filterChain.doFilter(request, response);
            return;
        }

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配，判断本次请求是否可以被放行
     * @param url
     * @return
     */
    public boolean checkURL(String[] urls, String url){
        for (String s : urls) {
            boolean match = PATH_MATCHER.match(s, url);
            if (match){
                return true;
            }
        }
        return false;
    }
}
