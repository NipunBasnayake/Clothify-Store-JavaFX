package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Product {
    private int productID;
    private String productName;
    private String productCategory;
    private String productSize;
    private Double productPrice;
    private Integer productQuantity;
    private String productImage;
    private Integer supplierID;
}
