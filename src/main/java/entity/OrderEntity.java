package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderEntity {
    private int orderId;
    private Date orderDate;
    private Double totalPrice;
    private String paymentMethod;
    private int employeeId;
    private int customerId;
}
