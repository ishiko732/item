import bean.Article;
import bean.Category;
import bean.Role;
import bean.User;
import mapper.ArticleMapper;
import mapper.CategoryMapper;
import mapper.RoleMapper;
import mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MybatisTest {
    @Test
    public void name() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();


        session.commit();
        session.close();
    }

    @Test
    public void roleTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

        RoleMapper mapper = session.getMapper(RoleMapper.class);

        System.out.println(mapper.count());

        List<Role> roles = mapper.list();
        for (Role role : roles) {
            System.out.println(role);
        }

        System.out.println(mapper.get(1));

        session.commit();
        session.close();
    }

    @Test
    public void categoryTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

        CategoryMapper mapper = session.getMapper(CategoryMapper.class);

        System.out.println(mapper.count());

        List<Category> categories = mapper.list();
        for (Category category : categories) {
            System.out.println(category);
        }

        System.out.println(mapper.get(1));

        session.commit();
        session.close();
    }

    @Test
    public void register() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

        UserMapper mapper = session.getMapper(UserMapper.class);

        User user = new User();
        user.setName("admin");
        user.setPassword("online_news");

        RoleMapper roleMapper = session.getMapper(RoleMapper.class);
        user.setRole(roleMapper.get(1));

        System.out.println(mapper.add(user));

        System.out.println(user);

        session.commit();
        session.close();
    }

    @Test
    public void UserTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

        UserMapper mapper = session.getMapper(UserMapper.class);

        User user = new User();

        User user1 = mapper.getId(10);
        System.out.println(user1);

        User admin = mapper.getName("admin");
        System.out.println(admin);

        admin.setPassword("77777777778");
        System.out.println(mapper.update(admin));
        System.out.println(admin);

        System.out.println(mapper.count());

        List<User> users = mapper.list();
        for (User u : users) {
            System.out.println(u);
        }

        session.commit();
        session.close();
    }

    @Test
    public void insertArticle() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

        ArticleMapper mapper = session.getMapper(ArticleMapper.class);
        CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);
        UserMapper userMapper = session.getMapper(UserMapper.class);
        Article article = new Article();
        article.setUser(userMapper.getName("admin"));
        article.setTitle("Your most unhappy customers are your greatest source of learning.");
        article.setDescription("Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.");
        article.setContent("test3");
        article.setCategory(categoryMapper.get(1));
        Timestamp timestamp = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));

        article.setTime(timestamp);
        System.out.println(mapper.add(article));
        System.out.println(article);
        session.commit();
        session.close();
    }

    @Test
    public void DateTest() {
        Timestamp timestamp = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp.getTime()));
    }

    @Test
    public void ArticleTest() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();

        ArticleMapper mapper = session.getMapper(ArticleMapper.class);
        CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);
        UserMapper userMapper = session.getMapper(UserMapper.class);


        Article article = mapper.get(1);
        System.out.println(article);
        article.setTime(Article.currentTime());
        System.out.println(mapper.update(article));
        System.out.println(article);

        System.out.println(mapper.count());

        List<Article> articles = mapper.list();
        for (Article a : articles) {
            System.out.println(a);
        }

        session.commit();
        session.close();
    }

    @Test
    public void deleteArticle() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        ArticleMapper mapper = session.getMapper(ArticleMapper.class);
        System.out.println(mapper.delete(3));


        session.commit();
        session.close();
    }


}
