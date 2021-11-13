package mapper;

import bean.Role;
import bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleMapper {

    @Select("select * from role where id=#{id}")
    Role get(int id);

    @Select("select count(*) from role")
    int count();

    @Select("select * from role")
    List<Role> list();
}
