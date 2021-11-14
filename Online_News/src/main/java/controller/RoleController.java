package controller;

import bean.Role;
import com.google.gson.Gson;
import mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleMapper mapper;
    public RoleController(RoleMapper mapper) {
        this.mapper = mapper;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Role get(@PathVariable int id){
        return mapper.get(id);
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public List<Role> list(){
        return mapper.list();
    }

    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public int count(){
        return mapper.count();
    }
}
