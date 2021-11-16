package controller;

import bean.Article;
import bean.Category;
import bean.User;
import mapper.ArticleMapper;
import mapper.CategoryMapper;
import mapper.RoleMapper;
import mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final CategoryMapper categoryMapper;
    private final ArticleMapper articleMapper;

    public ArticleController(UserMapper userMapper, RoleMapper roleMapper, CategoryMapper categoryMapper, ArticleMapper articleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.categoryMapper = categoryMapper;
        this.articleMapper = articleMapper;
    }

    public static  Timestamp currentTime(){
        return Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
    }

    @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
    public @ResponseBody
    Map<String, String> delete(HttpServletRequest request, HttpServletResponse response)  {
        int aid = Integer.parseInt(request.getParameter("id"));
        String ret = articleMapper.delete(aid)==1? "success" : "failed";
        Map<String, String> status = new HashMap<>();
        status.put("status", ret);
        return status;
    }

    @RequestMapping(path = "/{aid}", method = RequestMethod.GET)
    public @ResponseBody
    Article get(@PathVariable int aid) {
        Article article = articleMapper.get(aid);
        article.getUser().setPassword(null);//不应该传输密码
        return article;
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> add(HttpServletRequest request, HttpServletResponse response)  {
        Integer uid = UserController.tokenGetUserId();
        String title= request.getParameter("title");
        String description= request.getParameter("description");
        String content= request.getParameter("content");
        Integer cid= Integer.parseInt(request.getParameter("cid"));
        Timestamp time= currentTime();

        User user = new User();
        Category category = new Category();
        user.setId(uid);
        category.setId(cid);
        Article article = new Article(null,user,title,description,content,category,time);

        String ret = articleMapper.add(article)==1? "success" : "failed";

        Map<String, String> status = new HashMap<>();
        status.put("status", ret);
        status.put("uid", String.valueOf(uid));
        status.put("aid", String.valueOf(article.getId()));
        status.put("cid", String.valueOf(cid));
        return status;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public  @ResponseBody
    List<Article> list(Integer aid) {
        List<Article> articles = articleMapper.list(aid);
        for (Article article : articles) {
            article.getUser().setPassword(null);
        }
        return articles;
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public  @ResponseBody int count(Integer aid) {
        return articleMapper.count(aid);
    }

    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public @ResponseBody
    Map<String, String> update(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Integer uid = UserController.tokenGetUserId();//可被管理员更改
        String title= request.getParameter("title");
        String description= request.getParameter("description");
        String content= request.getParameter("content");
        String cid= request.getParameter("cid");//种类可更改
        Timestamp time=currentTime();

        Article article = articleMapper.get(Integer.parseInt(id));

        if(!Objects.isNull(uid)){
            User user = userMapper.getId(uid);
            if (user.getRole().getName().equals("admin")){
                article.setUser(user);
            }
        }
        String ret ="越权行为！";
        if(Objects.equals(uid, article.getUser().getId())){
            if(!Objects.isNull(title)){
                article.setTitle(title);
            }
            if(!Objects.isNull(description)){
                article.setDescription(description);
            }
            if(!Objects.isNull(content)){
                article.setContent(content);
            }
            if(!Objects.isNull(cid)){
                Category category = categoryMapper.get(Integer.parseInt(cid));
                article.setCategory(category);
            }
            article.setTime(time);
            ret = articleMapper.update(article)== 1 ? "success" : "failed";
        }
        Map<String, String> status = new HashMap<>();
        status.put("status", ret);
        return status;
    }

}