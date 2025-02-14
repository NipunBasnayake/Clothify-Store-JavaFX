package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderProductEntity {
    private int orderProductId;
    private int orderId;
    private int productId;
    private int quantity;
}
