import bean.User;
import mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:springmvc-servlet.xml")
public class springMVC_MybatisTest {
    @Autowired
    private UserMapper mapper;
    @Test
    public void testUser(){
        User user1 = mapper.getId(10);
        System.out.println(user1);

        List<User> admin = mapper.getName("admin");
        System.out.println(admin);

//        admin.setPassword("77777777778");
//        System.out.println(mapper.update(admin));
//        System.out.println(admin);

        System.out.println(mapper.count());

        List<User> users = mapper.list();
        for (User u : users) {
            System.out.println(u);
        }

    }
}
