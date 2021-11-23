package controller;

import bean.Role;
import bean.User;
import mapper.RoleMapper;
import mapper.UserMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import utils.Cookies;
import utils.JwtUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final CaptchaController captchaController;

    public UserController(UserMapper userMapper, RoleMapper roleMapper, CaptchaController captchaController) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.captchaController = captchaController;
    }

    public static String password_md5(String origin) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] bytes = md.digest(origin.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer tokenGetUserId(){
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        Cookie cookie= Cookies.getCookieByName(request,"Authorization");
        return JwtUtil.getUserId(Objects.requireNonNull(cookie).getValue());
    }

    @RequestMapping(path = "/get", method = RequestMethod.POST)
    public @ResponseBody
    User get(HttpServletRequest request, HttpServletResponse response)  {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        User user = userMapper.abstractGet(id, name);
        return user;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> register(HttpServletRequest request, HttpServletResponse response)  {
        String name = request.getParameter("name");
        String password = password_md5(request.getParameter("password"));
        int rid = Integer.parseInt(request.getParameter("role"));
        String phone=request.getParameter("phone");
        String token =request.getParameter("token");
        final String token_postman="03AGdBq24ES5YrIs6S10esymEhIIeK6DkpdupRuwZAEFdLI-WevxvNYGtDzhdd1MTrEeeFRpK8e148IBK_X13ABxQUhcarGaUhOmFKiIhyC4BmAsnTVa9eU7";//权限码

        User user = new User(null, name, null, password,null,phone);
        Boolean captcha = captchaController.captcha(token);
        Map<String, Object> status = new HashMap<>();
        if(rid==1 && !token.equals(token_postman)){ //判断是否越权注册
            status.put("status", false);
            status.put("info", "越权注册");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);//设置状态码 400
            return status;
        }
        if(captcha){
            Role role = roleMapper.get(rid);
            user.setRole(role);
            User o_user = userMapper.getUser(null,name);;
            if (!Objects.isNull(o_user)){
                status.put("status", userMapper.add(user) == 1);
                status.put("info", String.valueOf(user.getId()));
            }else{
                status.put("status", false);
                status.put("info", "已存在该用户名");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);//设置状态码 400
            }
        }else{
            status.put("status", false);
            status.put("info", "验证码异常");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);//设置状态码 400
        }
        return status;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public  @ResponseBody List<User> list() {
        Integer id = UserController.tokenGetUserId();
        User user = userMapper.getUser(id,null);
        if (user.getRole().getName().equals("admin")){
            return userMapper.list();
        }else{
            System.out.printf("普通用户（%d）想查询list(user)！%n", user.getId());
        }
        return null;
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public  @ResponseBody int count() {
        return userMapper.count();
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> update(HttpServletRequest request, HttpServletResponse response)  {
//        String id = request.getParameter("id");
        Integer id = UserController.tokenGetUserId();
        User user = userMapper.getUser(id,null);

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String origin=request.getParameter("origin");
        String rid = request.getParameter("role");
        String phone=request.getParameter("phone");
        System.out.println(password);
        Map<String, Object> status = new HashMap<>();
        if (user.getPassword().equals(password_md5(origin))){
            if(!Objects.isNull(name)){
                user.setName(name);
            }
            if(!Objects.isNull(password)){
                user.setPassword(password_md5(password));
            }
            if(!Objects.isNull(rid)){
                Role role = roleMapper.get(Integer.parseInt(rid));
                user.setRole(role);
            }
            if(!Objects.isNull(phone)){
                user.setPhone(phone);
            }

            boolean ret = userMapper.update(user)== 1 ;
            status.put("status", ret);
            status.put("info", String.valueOf(user.getId()));
        }else{
            status.put("status", false);
            status.put("info", "密码验证失败");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);//设置状态码 400
        }
        return status;

    }


}
