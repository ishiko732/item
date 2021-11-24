package filter;

import com.google.gson.Gson;
import utils.Cookies;
import utils.JwtUtil;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.*;

//@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String url=((HttpServletRequest) request).getRequestURL().toString();
        String suffix = url.substring(url.lastIndexOf(".") + 1);//后缀名
        List<String> across =new ArrayList<>();
        across.add(request.getServerName()+":"+request.getServerPort());
        List<String> listHtml = Arrays.asList("index.html","login.html", "register.html","infoUser.html");
        List<String> listController = Arrays.asList("login","register", "resetPassword","abstractList");
        List<String> listSource = Arrays.asList("js","css", "ico","jpg","gif","png");
        across.addAll(listHtml);
        across.addAll(listController);
        across.addAll(listSource);
        String $1 = url.replaceFirst(".*/([^/?]+).*", "$1");
//        System.out.printf("IP:%s, exec:%s%n", $1,suffix);
        if(across.contains($1)||across.contains(suffix)){
            chain.doFilter(request,response);
            return ;
        }

        try{
            Cookie cookie = Cookies.getCookieByName(((HttpServletRequest) request), "Authorization");
            String token = cookie.getValue();
            boolean verify = JwtUtil.verify(token);
            if(!verify){
                throw new Exception("token验证失败");
            }
            chain.doFilter(request,response);
        }catch (Exception e){
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
            // 设置响应内容，结束请求
            Map<String, Object> map = new HashMap<>();
            map.put("error:",e.getClass().getName());
            map.put("timestamp", Instant.now().getEpochSecond());
            map.put("path",url);
            map.put("message", e.getMessage());
            map.put("info", "请先登录");
            out.write(new Gson().toJson(map));
            out.flush();
            out.close();
        }

    }
}
