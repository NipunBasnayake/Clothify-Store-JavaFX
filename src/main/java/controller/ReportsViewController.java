package controller;

import db.DBConnection;
import dto.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.shape.Rectangle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import service.ServiceFactory;
import service.custom.*;
import util.ServiceType;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ReportsViewController implements Initializable {

    @FXML
    public ComboBox cmbSaleSortTime;
    @FXML
    public BarChart<String, Number> chartCustomer;
    @FXML
    public AreaChart<String, Number> chartSales;
    @FXML
    public LineChart chartSupplier;
    @FXML
    public Rectangle rect1;
    @FXML
    public Rectangle rect2;
    @FXML
    public Rectangle rect3;
    @FXML
    public ComboBox cmbProductCategories;


    private CustomerService customerService;
    private OrderService orderService;
    private ProductService productService;
    private OrderProductService orderProductService;
    private SupplierService supplierService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbProductCategories.getItems().addAll("Gents","Ladies","Kids","Accessories","Footwear");
        cmbProductCategories.setValue("Gents");
        cmbSaleSortTime.getItems().addAll("All Time", "Last Month", "Last Week", "Last Day");
        cmbSaleSortTime.setValue("All Time");

        customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMERS);
        orderService = ServiceFactory.getInstance().getService(ServiceType.ORDERS);
        productService = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
        orderProductService = ServiceFactory.getInstance().getService(ServiceType.ORDERPRODUCT);
        supplierService = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);

        loadCustomerChart();
        loadAllSalesChart();
        loadSupplierChart("Gents");
    }

    public void btnCustomerReportsOnAction(ActionEvent actionEvent) {
        generateReport("Customers.jrxml");
    }

    @FXML
    public void btnSalesReportsOnAction(ActionEvent actionEvent) {
        String selectedOption = cmbSaleSortTime.getSelectionModel().getSelectedItem().toString();
        generateReport("Sales_" + selectedOption.replace(" ", "_") + ".jrxml");
    }

    @FXML
    public void cmbSalesOnAction(ActionEvent actionEvent) {
        String selectedOption = cmbSaleSortTime.getSelectionModel().getSelectedItem().toString();
        switch (selectedOption) {
            case "All Time":
                loadAllSalesChart();
                break;
            case "Last Month":
                loadSalesChart(LocalDate.now().minusMonths(1), null);
                break;
            case "Last Week":
                loadSalesChart(LocalDate.now().minusWeeks(1), null);
                break;
            case "Last Day":
                loadSalesChart(LocalDate.now().minusDays(1), null);
                break;
        }
    }

    @FXML
    public void btnSupplierReportsOnAction(ActionEvent actionEvent) {

    }

    public void cmbProductCategoriesOnAction(ActionEvent actionEvent) {
        if (cmbProductCategories.getSelectionModel().getSelectedItem().equals("Gents")) {
            loadSupplierChart("Gents");
        }
        if (cmbProductCategories.getSelectionModel().getSelectedItem().equals("Ladies")) {
            loadSupplierChart("Ladies");
        }
        if (cmbProductCategories.getSelectionModel().getSelectedItem().equals("Kids")) {
            loadSupplierChart("Kids");
        }
        if (cmbProductCategories.getSelectionModel().getSelectedItem().equals("Accessories")) {
            loadSupplierChart("Accessories");
        }
        if (cmbProductCategories.getSelectionModel().getSelectedItem().equals("Footwear")) {
            loadSupplierChart("Footwear");
        }
    }

    private void loadSupplierChart(String category) {
        List<Supplier> supplierList = supplierService.getSuppliers();
        List<Product> productList = productService.getProducts();

        Map<String, Integer> supplierProductCount = new HashMap<>();

        for (Supplier supplier : supplierList) {
            int count = 0;

            for (Product product : productList) {
                if (supplier.getSupplierId() == product.getSupplierID() &&
                        category.equalsIgnoreCase(product.getProductCategory())) {
                    count++;
                }
            }
            supplierProductCount.put(supplier.getSupplierCompany(), count);
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(category + " Clothes Count");

        for (Map.Entry<String, Integer> entry : supplierProductCount.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        chartSupplier.getData().clear();
        chartSupplier.getData().add(series);
    }



    private void loadCustomerChart() {
        List<Customer> customers = customerService.getCustomers();
        chartCustomer.getData().clear();

        Map<String, Integer> addressCountMap = new HashMap<>();
        for (Customer customer : customers) {
            String address = customer.getCustomerAddress();
            addressCountMap.put(address, addressCountMap.getOrDefault(address, 0) + 1);
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Customers per Address");

        for (Map.Entry<String, Integer> entry : addressCountMap.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        chartCustomer.getData().add(series);
        adjustYAxis(chartCustomer, addressCountMap.values());
    }

    private void loadAllSalesChart() {
        loadSalesChart(null, null);
    }

    private void loadSalesChart(LocalDate startDate, LocalDate endDate) {
        List<Order> orders = orderService.getOrders();
        List<String> categories = new ArrayList<>();
        List<Integer> salesQuantities = new ArrayList<>();

        for (Order order : orders) {
            LocalDate orderLocalDate = order.getOrderDate().toLocalDate();

            if (startDate != null && orderLocalDate.isBefore(startDate)) continue;
            if (endDate != null && orderLocalDate.isAfter(endDate)) continue;

            for (OrderDetails orderDetails : orderProductService.getOrderProducts()) {
                Product product = productService.getProductById(orderDetails.getProductId());
                if (product != null && product.getProductCategory() != null) {
                    String category = product.getProductCategory();
                    int quantity = orderDetails.getQuantity();
                    int index = categories.indexOf(category);

                    if (index != -1) {
                        salesQuantities.set(index, salesQuantities.get(index) + quantity);
                    } else {
                        categories.add(category);
                        salesQuantities.add(quantity);
                    }
                }
            }
        }

        chartSales.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Sold Quantity by Category");

        for (int i = 0; i < categories.size(); i++) {
            series.getData().add(new XYChart.Data<>(categories.get(i), salesQuantities.get(i)));
        }

        chartSales.getData().add(series);

        adjustYAxis(chartSales, salesQuantities);
    }

    private void adjustYAxis(Chart chart, Collection<Integer> dataValues) {
        NumberAxis yAxis = (NumberAxis) ((XYChart<String, Number>) chart).getYAxis();
        yAxis.setTickUnit(1);
        yAxis.setMinorTickCount(0);
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(Collections.max(dataValues) + 5);
    }

    private void generateReport(String reportFileName) {
        try {
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/" + reportFileName);
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
