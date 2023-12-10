package code.uz.dto;


import code.uz.enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Terminal {
    private Integer id;
    private String code;
    private String address;
    private Status statusTerminal;
    private boolean visible;
    private LocalDateTime createdDate;

}
