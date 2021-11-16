package bean;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private Integer id;
    private User user;
    private String title;
    private String description;
    private String content;
    private Category category;
    private Timestamp time;




}
