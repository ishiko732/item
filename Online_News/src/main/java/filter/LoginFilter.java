package filter;

import org.springframework.stereotype.Component;
import utils.Cookies;
import utils.JwtUtil;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;


        String url=request.getRequestURL().toString();
        System.out.println(url);

        if(url.endsWith("login.html")||url.endsWith("login")){
            filterChain.doFilter(request,response);
            return ;
        }

        try{
            Cookie cookie = Cookies.getCookieByName(request, "Authorization");
            String token = cookie.getValue();
            boolean verify = JwtUtil.verify(token);
            if(!verify){
                throw new Exception("token验证失败");
            }
        }catch (Exception e){
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            // 设置响应内容，结束请求
            out.write("请先登录");
            out.flush();
            out.close();
            e.printStackTrace();
        }

        filterChain.doFilter(request,response);

    }
}
