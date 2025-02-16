package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderHistory {
    private int orderId;
    private Date orderDate;
    private String productName;
    private double productPrice;
    private int quantity;
    private double totalAmount;
    private String paymentMethod;
    private String customerName;
    private String userName;
}
