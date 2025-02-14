package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerEntity {
    private int customerId;
    private String customerName;
    private String customerMobile;
    private String customerAddress;
}
