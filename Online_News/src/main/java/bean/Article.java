package bean;

import lombok.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private int id;
    private User user;
    private String title;
    private String description;
    private String content;
    private Category category;
    private Timestamp time;

    public static  Timestamp currentTime(){
        return Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
    }


}
