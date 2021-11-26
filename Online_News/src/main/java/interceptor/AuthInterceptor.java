package interceptor;

import com.google.gson.Gson;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import utils.Cookies;
import utils.JwtUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.*;

public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        List<String> authController = Arrays.asList("AuthController.login");
        List<String> userController = Arrays.asList("UserController.register", "UserController.resetPassword");
        List<String> articleController = Arrays.asList("ArticleController.abstractList");
        List<String> categoryController = Arrays.asList("CategoryController.list");
        List<String> across = new ArrayList<>();
        across.addAll(authController);
        across.addAll(userController);
        across.addAll(articleController);
        across.addAll(categoryController);
        String method;
        if(handler instanceof HandlerMethod) {
            HandlerMethod h = (HandlerMethod)handler;
            method=h.getBeanType().getSimpleName()+"."+h.getMethod().getName();
        }else{
            return false;
        }

        if(across.contains(method)){
            return true;
        }
        Cookie cookie = Cookies.getCookieByName((request), "Authorization");
        try{
            if(Objects.isNull(cookie)){
                throw new Exception("token不存在");
            }
            String token = cookie.getValue();
            boolean verify = JwtUtil.verify(token);
            if(!verify){
                throw new Exception("token验证失败");
            }

        }catch (Exception e){
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            // 设置响应内容，结束请求
            Map<String, Object> map = new HashMap<>();
            map.put("error:",e.getClass().getName());
            map.put("timestamp", Instant.now().getEpochSecond());
            map.put("path",request.getRequestURL().toString());
            map.put("method",method);
            map.put("message", e.getMessage());
            map.put("info", "请先登录");
            out.write(new Gson().toJson(map));
            out.flush();
            out.close();
            return false;
        }

        //true表示放行,false表示拦截
        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
//        System.out.println("postHandle(), 在访问Controller之后，访问视图之前被调用,这里可以注入一个时间到modelAndView中，用于后续视图显示");
//        modelAndView.addObject("date","由拦截器生成的时间:" + new Date());
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     *
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */

    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

//        System.out.println("afterCompletion(), 在访问视图之后被调用");
    }


}
