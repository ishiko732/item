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
    int uid;
    int aid;
    String dir;
    String filename;
    Timestamp time;
}
