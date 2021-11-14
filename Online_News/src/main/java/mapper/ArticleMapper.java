package mapper;


import bean.Article;
import bean.ArticleFile;
import bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ArticleMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    @Insert("insert into article( uid, title, description, content, category_id, created_timestamp) " +
            "values(#{user.id},#{title},#{description}, #{content}, #{category.id}, #{time})")
    int add(Article article);

    @Delete("delete from article where id=#{id}")
    int delete(int id);

    @Select("select * from article where id=#{id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "time",column = "created_timestamp"),
            @Result(property = "user",javaType = User.class,column="uid",
                    one = @One(select="mapper.UserMapper.getId")),
            @Result(property = "category",javaType = User.class,column="category_id",
                    one = @One(select="mapper.CategoryMapper.get"))
    })
    Article get(int id);

    @Update("update article set title=#{title},description=#{description},content=#{content},category_id=#{category.id},created_timestamp=#{time} " +
            "where id=#{id}")
    int update(Article article);

    @Select({
            "<script>",
            "select count(*) from article",
            "<if test='cid !=null'>",
            "where category_id=#{cid}",
            "</if>",
            "</script>"
    })
    int count(Integer aid);


    @Select({
            "<script>",
            "select * from article",
            "<if test='cid !=null'>",
            "where category_id=#{cid}",
            "</if>",
            "</script>"
    })
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "time",column = "created_timestamp"),
            @Result(property = "user",javaType = User.class,column="uid",
                    one = @One(select="mapper.UserMapper.getId")),
            @Result(property = "category",javaType = User.class,column="category_id",
                    one = @One(select="mapper.CategoryMapper.get"))
    })
    List<Article> list(Integer aid);

    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    @Insert("insert into file(uid, aid, dir, file,time) \n" +
            "values (#{uid},#{aid},#{dir},#{filename},#{time})")
    int uploadFile(ArticleFile af);

    @Select("select * from file where id=#{id}")
    @Result(property = "filename",column = "file")
    ArticleFile getFile(int id);
}
