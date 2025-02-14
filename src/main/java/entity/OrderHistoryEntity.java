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
public class OrderHistoryEntity {
    private int orderId;
    private Date orderDate;
    private String productName;
    private Double unitPrice;
    private int quantity;
    private Double totalAmount;
    private String paymentMethod;
    private String customerName;
    private String employeeName;
}
