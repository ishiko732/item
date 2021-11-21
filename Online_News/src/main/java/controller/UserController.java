package controller;

import bean.Role;
import bean.User;
import com.google.gson.Gson;
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
    String get(HttpServletRequest request, HttpServletResponse response)  {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        Gson gson = new Gson();
        if (!Objects.isNull(id)) {
            User user = userMapper.getId(Integer.parseInt(id));
            return gson.toJson(user);

        }else if (!Objects.isNull(name)) {
            List<User> users =userMapper.getName(name);
            return gson.toJson(users);
        }
        return "";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> register(HttpServletRequest request, HttpServletResponse response)  {
        String name = request.getParameter("name");
        String password = password_md5(request.getParameter("password"));
        int rid = Integer.parseInt(request.getParameter("role"));
        String token =request.getParameter("token");

        String ret;
        User user = new User(null, name, null, password,null);
        Boolean captcha = captchaController.captcha(token);
        Map<String, String> status = new HashMap<>();
        if(captcha){
            Role role = roleMapper.get(rid);
            user.setRole(role);
            List<User> users = userMapper.getName(name);
            if (users.size()==0){
                ret = userMapper.add(user) == 1 ? "success" : "failed";
                status.put("uid", String.valueOf(user.getId()));
            }else{
                ret ="failed(已经存在该用户名)";
            }
            status.put("status", ret);
        }else{
            status.put("status", "验证码异常");
        }
        return status;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public  @ResponseBody List<User> list() {
        return userMapper.list();
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public  @ResponseBody int count() {
        return userMapper.count();
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> update(HttpServletRequest request, HttpServletResponse response)  {
//        String id = request.getParameter("id");
        Integer id = UserController.tokenGetUserId();
        User user = userMapper.getId(id);

        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String rid = request.getParameter("role");

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

        String ret = userMapper.update(user)== 1 ? "success" : "failed";
        Map<String, String> status = new HashMap<>();
        status.put("status", ret);
        status.put("uid", String.valueOf(user.getId()));
        return status;

    }


}
