package controller;

import bean.User;
import mapper.UserMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.Cookies;
import utils.JwtUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@CrossOrigin
@RestController
public class AuthController {
    private final UserMapper userMapper;
    private final CaptchaController captchaController;

    public AuthController(UserMapper userMapper, CaptchaController captchaController) {
        this.userMapper = userMapper;
        this.captchaController = captchaController;
    }

    @RequestMapping("/login")
    public
    Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String password=request.getParameter("password");
        User user = userMapper.getUser(null,name);
        Map<String, Object> status = new HashMap<>();

        String tokenCaptcha =request.getParameter("token");
        Boolean captcha = captchaController.captcha(tokenCaptcha);
        if(!captcha){
            status.put("status", false);
            status.put("info","验证码异常");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);//设置状态码 400
            return status;
        }
        if(!Objects.isNull(Cookies.getCookieByName(request, "Authorization"))){//已经登录
            status.put("status", false);
            status.put("info","已经登录");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);//设置状态码 400
            return status;
        }
        if(Objects.isNull(user)){
            status.put("status", false);
            status.put("info","用户不存在！");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);//设置状态码 400
            return status;
        }

        if (user.getName().equals(name) && user.getPassword().equals(UserController.password_md5(password))) {
            String token = JwtUtil.sign(user);
            if(!Objects.isNull(token)){
                Cookie cookie=new Cookie("Authorization",token);
                cookie.setMaxAge(3600);
                cookie.setPath("/");
//                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
            status.put("status", true);
            status.put("token",token);
            status.put("uid",String.valueOf(user.getId()));
            status.put("name",name);
            status.put("ico",user.getIcon());
            status.put("role",user.getRole());
            return status;

        }
        status.put("status", true);
        status.put("info","账号或密码错误");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);//设置状态码 400
        return status;
    }
    @RequestMapping("/logout")
    public Map<String, Object> logout(HttpSession session,HttpServletResponse response) {
        // 退出登录就是将用户信息删除
        Cookie cookie=new Cookie("Authorization","");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        Map<String, Object> status = new HashMap<>();
        status.put("status", true);
        return status;
    }


}
