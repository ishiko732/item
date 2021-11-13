package mapper;

import bean.Role;
import bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    @Insert("insert into user(id,name, password,role_id) values(null,#{name},#{password},#{role.id})")
    int add(User user);


//    public void delete(int id);

    @Select("select * from user where id=#{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "role",javaType = Role.class,column="role_id",
                    one = @One(select="mapper.RoleMapper.get")),
    })
    User getId(int id);

    @Select("select * from user where name=#{name}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "role",javaType = Role.class,column="role_id",
                    one = @One(select="mapper.RoleMapper.get")),
    })
    User getName(String name);


    @Update("update user set name=#{name},password=#{password},role_id=#{role.id} " +
            "where id=#{id}")
    int update(User user);

    @Select("select count(*) from user")
    int count();

    @Select("select * from user")
    List<User> list();
}
