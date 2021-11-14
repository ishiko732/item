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

    public UserController(UserMapper userMapper, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> register(HttpServletRequest request, HttpServletResponse response)  {
        String name = request.getParameter("name");
        String password = password_md5(request.getParameter("password"));
        int rid = Integer.parseInt(request.getParameter("role"));
        Role role = roleMapper.get(rid);
        System.out.println(role);
        User user = new User(null, name, role, password,null);

        String ret = userMapper.add(user) == 1 ? "success" : "failed";

        Map<String, String> status = new HashMap<>();
        status.put("status", ret);
        status.put("uid", String.valueOf(user.getId()));
        return status;
    }


    public String password_md5(String origin) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] bytes = md.digest(origin.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
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



    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> update(HttpServletRequest request, HttpServletResponse response)  {
        String id = request.getParameter("id");
        User user = userMapper.getId(Integer.parseInt(id));

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

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public  @ResponseBody List<User> list() {
        return userMapper.list();
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public  @ResponseBody int count() {
        return userMapper.count();
    }


}
