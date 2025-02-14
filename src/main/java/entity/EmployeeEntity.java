package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeEntity {
    private int employeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeeRole;
}
