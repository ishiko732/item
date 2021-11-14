package bean;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private Role role;
    private String password;
}
