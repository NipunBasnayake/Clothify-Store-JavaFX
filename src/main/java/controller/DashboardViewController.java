package controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import dto.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.ServiceFactory;
import service.custom.CustomerService;
import service.custom.OrderService;
import service.custom.ProductService;
import com.itextpdf.layout.element.Cell;
import util.ServiceType;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DashboardViewController implements Initializable {
    CustomerService customerService = ServiceFactory.getInstance().getService(ServiceType.CUSTOMERS);
    ProductService productService = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
    OrderService orderService = ServiceFactory.getInstance().getService(ServiceType.ORDERS);

    private static User currentUser;

    private List<Product> productList;
    private List<Product> cartList = new ArrayList<>();

    @FXML
    public Label lblLoadOrderId;

    @FXML
    public Label txtTotalAmount;

    @FXML
    public FlowPane flowPaneCart;

    @FXML
    private FlowPane flowPaneProducts;

    @FXML
    private ComboBox<String> cmbSelectCustomer;

    @FXML
    private TextField txtSearchProductText;

    @FXML
    public Label lblDate;

    @FXML
    public Label lblTime;

    @FXML
    public AnchorPane paneDashboard;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCustomersComboBox();
        productList = productService.getProducts();
        loadProductPanes(productList);
        loadOrderId();
        loadDateAdnTime();

    }

    @FXML
    void btnPayBillOnAction(ActionEvent event) {
//        Order order = new Order(
//                Integer.parseInt(lblLoadOrderId.getText()),
//                convertDateFormat(lblDate.getText()),
//                Double.parseDouble(txtTotalAmount.getText()),
//                "Cash",
////                currentUser.getUserID(),
//                1,
//                getCustomerIdByComboBox(cmbSelectCustomer.getSelectionModel().getSelectedItem().toString()),
//                cartList
//        );
//        boolean isOrderPlaced = orderService.addOrder(order);
//        if (isOrderPlaced) {
//            generateBillPdf();
//            loadOrderId();
//            loadProductPanes(productService.getProducts());
//        }else{
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Order Place Failed");
//            alert.setHeaderText("Failed to Place Order");
//            alert.show();
//        }
    }


    @FXML
    void btnAllProductsOnAction(ActionEvent actionEvent) {
        loadProductPanes(productList);
    }

    @FXML
    void btnGentsOnAction(ActionEvent actionEvent) {
        loadProductPanes(sortProductsByCategory("Gents"));
    }

    @FXML
    void btnLadiesOnAction(ActionEvent actionEvent) {
        loadProductPanes(sortProductsByCategory("Ladies"));
    }

    @FXML
    void btnKidsOnAction(ActionEvent actionEvent) {
        loadProductPanes(sortProductsByCategory("Kids"));
    }

    @FXML
    void btnAccessoriesOnAction(ActionEvent actionEvent) {
        loadProductPanes(sortProductsByCategory("Accessories"));
    }

    @FXML
    void btnFootwearOnAction(ActionEvent actionEvent) {
        loadProductPanes(sortProductsByCategory("Footwear"));
    }

    @FXML
    void btnAddNewCustomerOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/add-customer-view.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Add Customer");

            stage.setOnHidden(e -> loadCustomersComboBox());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSearchProductOnAction(ActionEvent actionEvent) {
        String searchText = txtSearchProductText.getText().trim().toLowerCase();
        List<Product> filteredList = new ArrayList<>();

        if (!searchText.isEmpty()) {
            for (Product product : productList) {
                if (product.getProductName().toLowerCase().contains(searchText)) {
                    filteredList.add(product);
                }
            }
            loadProductPanes(filteredList);
        } else {
            loadProductPanes(productList);
        }
    }

    private List<Product> sortProductsByCategory(String category) {
        List<Product> sortedList = new ArrayList<>();
        for (Product product : productList) {
            if (product.getProductCategory().equals(category)) {
                sortedList.add(product);
            }
        }
        return sortedList;
    }

    private void loadCustomersComboBox() {
        List<Customer> customerList = customerService.getCustomers();
        ObservableList<String> customerObservableList = FXCollections.observableArrayList();

        for (Customer customer : customerList) {
            customerObservableList.add(customer.getCustomerId() + " - " + customer.getCustomerName() + " - " + customer.getCustomerMobile());
        }
        cmbSelectCustomer.setItems(customerObservableList);
    }

    private void loadProductPanes(List<Product> products) {
        flowPaneProducts.getChildren().clear();
        flowPaneProducts.setHgap(15);
        flowPaneProducts.setVgap(15);
        flowPaneProducts.setPrefWrapLength(950);

        for (Product product : products) {
            VBox productCard = createProductCard(product);
            flowPaneProducts.getChildren().add(productCard);
        }
    }

    private VBox createProductCard(Product product) {
        VBox productCard = new VBox(5);
        productCard.setStyle("-fx-padding: 12; -fx-background-color: #ffffff; -fx-border-width: 2; -fx-border-color: #ccc; -fx-background-radius: 10; -fx-border-radius: 10;");
        productCard.setPrefWidth(177);
        productCard.setPrefHeight(265);
        productCard.setAlignment(Pos.CENTER);

        ImageView productImage = new ImageView(new Image("file:" + product.getProductImage()));
        productImage.setFitWidth(177);
        productImage.setFitHeight(130);

        Rectangle clip = new Rectangle(177, 130);
        clip.setArcWidth(12);
        clip.setArcHeight(12);
        productImage.setClip(clip);

        Label lblProductName = new Label(product.getProductName());
        lblProductName.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        Label lblCategory = new Label("Category: " + product.getProductCategory());
        Label lblQuantity = new Label("Stock: " + product.getProductQuantity());

        Label lblPrice = new Label("LKR " + product.getProductPrice());
        lblPrice.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #333;");

        VBox textContainer = new VBox(3, lblProductName, lblCategory, lblQuantity);
        textContainer.setAlignment(Pos.CENTER);

        setProductCardClickAction(productCard, product);

        productCard.getChildren().addAll(productImage, textContainer, lblPrice);

        return productCard;
    }

    private void setProductCardClickAction(VBox productCard, Product product) {
        productCard.setOnMouseClicked(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Enter Quantity");
            dialog.setHeaderText("Enter the quantity for " + product.getProductName());
            dialog.setContentText("Quantity:");

            dialog.showAndWait().ifPresent(input -> {
                try {
                    int quantity = Integer.parseInt(input);

                    boolean found = false;
                    for (Product listProduct : cartList) {
                        if (product.getProductName().equals(listProduct.getProductName())) {
                            listProduct.setProductQuantity(listProduct.getProductQuantity() + quantity);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        cartList.add(new Product(product.getProductID(),
                                product.getProductName(),
                                product.getProductCategory(),
                                product.getProductSize(),
                                product.getProductPrice(),
                                quantity,
                                product.getProductImage(),
                                product.getSupplierID()));
                    }

                    loadCartPane();
                } catch (NumberFormatException e) {
                    new Alert(Alert.AlertType.ERROR, "Invalid quantity! Please enter a valid number.").show();
                }
            });
        });
    }

    private void loadCartPane() {
        flowPaneCart.getChildren().clear();
        double totalAmount = 0;

        VBox cartContainer = new VBox(10);
        cartContainer.setPadding(new Insets(10, 0, 10, 0));

        for (Product product : cartList) {
            HBox cartItem = new HBox(10);
            cartItem.setStyle("-fx-padding: 10; -fx-background-color: #f8f9fa; "
                    + "-fx-border-width: 1; -fx-border-color: #ccc; "
                    + "-fx-background-radius: 5; -fx-border-radius: 5;");
            cartItem.setAlignment(Pos.CENTER_LEFT);
            cartItem.setPrefWidth(450);
            cartItem.setPadding(new Insets(5, 10, 5, 10));

            Label lblProductName = new Label(product.getProductName());
            lblProductName.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
            lblProductName.setPrefWidth(130);

            Label lblPrice = new Label("LKR " + product.getProductPrice());
            lblPrice.setStyle("-fx-font-size: 14; -fx-text-fill: #333;");
            lblPrice.setPrefWidth(80);

            TextField txtQuantity = new TextField(String.valueOf(product.getProductQuantity()));
            txtQuantity.setPrefWidth(70);
            txtQuantity.setAlignment(Pos.CENTER);
            txtQuantity.setStyle("-fx-border-radius: 5; -fx-background-radius: 5;");

            Label lblTotal = new Label("LKR " + (product.getProductPrice() * product.getProductQuantity()));
            lblTotal.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
            lblTotal.setPrefWidth(100);

            txtQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    int newQuantity = Integer.parseInt(newValue.trim());
                    if (newQuantity > 0) {
                        product.setProductQuantity(newQuantity);
                        lblTotal.setText("LKR " + (product.getProductPrice() * newQuantity));
                        updateTotalAmount();
                    } else {
                        txtQuantity.setText(oldValue);
                    }
                } catch (NumberFormatException e) {
                    txtQuantity.setText(oldValue);
                }
            });

            Image closeImage = new Image(getClass().getResourceAsStream("/images/closeIcon.png"));
            ImageView closeImageView = new ImageView(closeImage);
            closeImageView.setFitWidth(16);
            closeImageView.setFitHeight(16);

            Button btnRemove = new Button();
            btnRemove.setGraphic(closeImageView);
            btnRemove.setStyle("-fx-background-color: transparent; -fx-padding: 6;");
            btnRemove.setTooltip(new Tooltip("Remove Item"));
            btnRemove.setOnAction(event -> {
                cartList.remove(product);
                loadCartPane();
            });

            Pane spacer = new Pane();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            cartItem.getChildren().addAll(lblProductName, lblPrice, txtQuantity, lblTotal, spacer, btnRemove);
            cartContainer.getChildren().add(cartItem);

            totalAmount += product.getProductPrice() * product.getProductQuantity();
        }

        txtTotalAmount.setText(String.valueOf(totalAmount));
        flowPaneCart.getChildren().setAll(cartContainer);
    }

    private void updateTotalAmount() {
        double newTotal = cartList.stream().mapToDouble(p -> p.getProductPrice() * p.getProductQuantity()).sum();
        txtTotalAmount.setText(String.valueOf(newTotal));
    }

    private void loadOrderId() {
        int lastOrderId = orderService.getLastOrderId();
        lblLoadOrderId.setText(String.valueOf(lastOrderId + 1));
    }

    private String getCustomerNameByComboBox() {
        String selectedValue = cmbSelectCustomer.getValue();
        if (selectedValue == null || selectedValue.isEmpty()) {
            return "N/A";
        }
        String pattern = "\\d+\\s*-\\s*([A-Za-z\\s]+)\\s*-";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(selectedValue);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "N/A";
    }

    public static Integer getCustomerIdByComboBox(String selectedValue) {
        if (selectedValue == null || selectedValue.isEmpty()) {
            return -1;
        }
        String pattern = "^(\\d+)\\s*-";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(selectedValue);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }

    private void loadDateAdnTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        lblDate.setText(formatter.format(date));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    LocalTime now = LocalTime.now();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                    lblTime.setText(now.format(dateFormatter));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void generateBillPdf() {
        try {
            String filePath = Paths.get(System.getProperty("user.home"), "Downloads", "Clothify_PDF", "Clothify_Invoice_" + lblLoadOrderId.getText() + ".pdf").toString();
            Files.createDirectories(Paths.get(System.getProperty("user.home"), "Downloads", "Clothify_PDF"));

            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, com.itextpdf.kernel.geom.PageSize.A5);
            document.setMargins(36, 36, 36, 36);

            document.add(new Paragraph("Clothify Store").setFontSize(20).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("INVOICE").setFontSize(14).setTextAlignment(TextAlignment.CENTER).setMarginBottom(10));

            Table dateTimeTable = new Table(new float[]{1, 1});
            dateTimeTable.setWidth(UnitValue.createPercentValue(100));
            dateTimeTable.addCell(new Cell().add(new Paragraph("Date: " + lblDate.getText()).setFontSize(10)).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));
            dateTimeTable.addCell(new Cell().add(new Paragraph("Time: " + lblTime.getText()).setFontSize(10).setTextAlignment(TextAlignment.RIGHT)).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));
            document.add(dateTimeTable);

            document.add(new Paragraph("Customer: " + getCustomerNameByComboBox()).setFontSize(10).setMarginBottom(10));

            float[] columnWidths = {2, 6, 3, 3, 4};
            Table table = new Table(columnWidths);
            table.setWidth(UnitValue.createPercentValue(100));
            table.addHeaderCell(new Cell().add(new Paragraph("#").setFontSize(10)));
            table.addHeaderCell(new Cell().add(new Paragraph("Item").setFontSize(10)));
            table.addHeaderCell(new Cell().add(new Paragraph("Price").setFontSize(10)));
            table.addHeaderCell(new Cell().add(new Paragraph("Qty").setFontSize(10)));
            table.addHeaderCell(new Cell().add(new Paragraph("Amount").setFontSize(10)));

            int count = 1;
            double totalAmount = 0.0;
            for (Product product : cartList) {
                double amount = product.getProductPrice() * product.getProductQuantity();
                totalAmount += amount;

                table.addCell(new Cell().add(new Paragraph(String.valueOf(count)).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(product.getProductName()).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f", product.getProductPrice())).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(product.getProductQuantity())).setFontSize(10)));
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f", amount)).setFontSize(10)));
                count++;
            }
            document.add(table.setMarginBottom(10));

            Table summaryTable = new Table(new float[]{3, 2});
            summaryTable.setWidth(UnitValue.createPercentValue(100));
            summaryTable.addCell(new Cell().add(new Paragraph("Total Amount:").setFontSize(10)).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER));
            summaryTable.addCell(new Cell().add(new Paragraph("LKR " + String.format("%.2f", totalAmount)).setFontSize(10)).setBorder(com.itextpdf.layout.borders.Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));
            summaryTable.addCell(new Cell().add(new Paragraph("Final Amount:").setFontSize(12)).setBorder(Border.NO_BORDER));
            summaryTable.addCell(new Cell().add(new Paragraph("LKR " + String.format("%.2f", totalAmount)).setFontSize(12)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));

            document.add(summaryTable.setMarginBottom(10));

            document.add(new Paragraph("Thank you for your purchase!").setFontSize(10).setTextAlignment(TextAlignment.CENTER));

            document.close();

            File pdfFile = new File(filePath);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Date convertDateFormat(String inputDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(inputDate, inputFormatter);
        return Date.valueOf(localDate);
    }


    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
