package controller;

import bean.Category;
import bean.Role;
import mapper.CategoryMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryMapper mapper;
    public CategoryController(CategoryMapper mapper) {
        this.mapper = mapper;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Category get(@PathVariable int id){
        return mapper.get(id);
    }
    
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public List<Category> list(){
        return mapper.list();
    }

    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public int count(){
        return mapper.count();
    }
}
