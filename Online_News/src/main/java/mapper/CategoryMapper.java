package mapper;

import bean.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper {
//    int add(Category category);

//    void delete(int id);

    @Select("select * from category where id=#{id}")
    Category get(int id);

//    public int update(Category category);

    @Select("select count(*) from category")
    int count();

    @Select("select * from category")
    List<Category> list();
}
