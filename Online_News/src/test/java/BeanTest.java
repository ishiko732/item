import bean.MimeTypeEnum;
import bean.Role;
import bean.User;
import org.junit.Test;

public class BeanTest {

    public static void main(String[] args) {
        User user = new User();
        user.setId(2);
        user.setName("32");
        Role role = user.getRole();

        System.out.println(role==null);
        System.out.println(user);
    }

    @Test
    public void BeanUser() {
        User user = new User();
        user.setId(2);
        user.setName("32");
        Role role = user.getRole();

        System.out.println(role==null);
        System.out.println(user);

    }
}


