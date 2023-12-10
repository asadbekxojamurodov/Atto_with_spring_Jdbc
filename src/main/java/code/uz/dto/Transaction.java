package code.uz.dto;


import code.uz.enums.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Transaction {

    private Integer id;
    private Integer cardId;
    private String cardNumber;
    private Double amount;
    private String terminalCode;
    private TransactionType transactionType;
    private String phone;
    private LocalDateTime createdDate;

}
