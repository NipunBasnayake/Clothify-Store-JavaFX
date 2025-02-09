package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Supplier {
    private int supplierId;
    private String supplierName;
    private String supplierCompany;
    private String supplierEmail;
    private String supplyItem;
}
