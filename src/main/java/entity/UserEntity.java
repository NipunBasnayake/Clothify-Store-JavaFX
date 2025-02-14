package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UserEntity {
    private int userID;
    private String userName;
    private String email;
    private String password;
    private String role;
    private String regDate;
}
