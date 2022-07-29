package com.chanokh.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.chanokh.reggie.common.BaseContext;
import com.chanokh.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class loginCheck implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    public static String[] urls = new String[]{"/employee/login","/employee/logout","/backend/**","/front/**","/common/**"};
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info("拦截到请求:{}", request.getRequestURI());
        if(check(request.getRequestURI()))
        {
            filterChain.doFilter(request, response);
            return;
        }
        //不成立
        if(request.getSession().getAttribute("employee") != null)
        {
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request, response);
            return;
        }
        //未登录 以输出流方式 向客户端返回
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }
    public boolean check(String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match) {
                return true;
            }
        }
        return false;

    }
}
