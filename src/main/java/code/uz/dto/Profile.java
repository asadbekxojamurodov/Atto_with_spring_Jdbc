package code.uz.dto;

import code.uz.enums.ProfileRole;
import code.uz.enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Profile {
    private int id;
    private String name;
    private String surname;
    private String phone;
    private String password;
    private LocalDateTime createdDate;
    private Status status;
    private ProfileRole profileRole;

}
