package mapper;

import bean.Role;
import bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    @Insert("insert into user(id,name, password,role_id,phone) values(null,#{name},#{password},#{role.id},#{phone})")
    int add(User user);

    @Select({
            "<script>",
            "select * from user",
            "<where>" ,
            "<if test='id !=null'>",
            "and id=#{id}",
            "</if>",
            "<if test='name !=null'>",
            "and name=#{name}",
            "</if>",
            "</where>",
            "</script>"
    })
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "role",javaType = Role.class,column="role_id",
                    one = @One(select="mapper.RoleMapper.get")),
    })
    User getUser(@Param("id")Integer id,@Param("name")String name);
//    public void delete(int id);





    @Update("update user set name=#{name},password=#{password},role_id=#{role.id},phone=#{phone} " +
            "where id=#{id}")
    int update(User user);

    @Select("select count(*) from user")
    int count();

    @Select("select * from user")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "role",javaType = Role.class,column="role_id",
                    one = @One(select="mapper.RoleMapper.get")),
    })
    List<User> list();

    @Update("update user set icon=#{imagePath} " +
            "where id=#{id}")
    int setImage(@Param("id")int id,@Param("imagePath")String imagePath);



    @Select({
            "<script>",
            "select id,name,role_id,icon from user",
            "<where>" ,
            "<if test='id !=null'>",
            "and id=#{id}",
            "</if>",
            "<if test='name !=null'>",
            "and name=#{name}",
            "</if>",
            "</where>",
            "</script>"
    })
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",column = "name"),
            @Result(property = "role",javaType = Role.class,column="role_id",
                    one = @One(select="mapper.RoleMapper.get")),
    })
    User abstractGet(@Param("id")String id,@Param("name")String name);

    @Select("select id,name,role_id,icon,phone from user where id=#{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "name",column = "name"),
            @Result(property = "role",javaType = Role.class,column="role_id",
                    one = @One(select="mapper.RoleMapper.get")),
    })
    User abstractGetById(@Param("id")Integer id);

}
