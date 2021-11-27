package bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleFile {
    Integer id;
    Integer uid;
    Integer aid;
    String dir;
    String filename;
    Timestamp time;
}
