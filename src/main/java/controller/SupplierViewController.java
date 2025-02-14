package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Supplier;
import service.ServiceFactory;
import service.custom.SupplierService;
import service.custom.impl.SupplierServiceImpl;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SupplierViewController implements Initializable {

    SupplierService supplierService = ServiceFactory.getInstance().getService(ServiceType.SUPPLIER);

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    private TableColumn<Supplier, Integer> colSupplierId;

    @FXML
    private TableColumn<Supplier, String> colSupplierName, colSupplierCompany, colSupplierEmail, colSypplyItem;

    @FXML
    private TableColumn<Supplier, Void> colSupplierUpdateAction, colSupplierDeleteAction;

    @FXML
    private TextField txtSearchSupplier;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableColumns();
        populateTable();
    }

    private void initializeTableColumns() {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colSupplierCompany.setCellValueFactory(new PropertyValueFactory<>("supplierCompany"));
        colSupplierEmail.setCellValueFactory(new PropertyValueFactory<>("supplierEmail"));
        colSypplyItem.setCellValueFactory(new PropertyValueFactory<>("supplyItem"));

        colSupplierUpdateAction.setCellFactory(param -> createButtonCell("Update", "#495057", "#363b3e", this::openUpdateSupplierView));
        colSupplierDeleteAction.setCellFactory(param -> createButtonCell("Delete", "#6e0000", "#4c0000", this::deleteSupplier));
    }

    private TableCell<Supplier, Void> createButtonCell(String text, String defaultColor, String hoverColor, java.util.function.Consumer<Supplier> action) {
        return new TableCell<>() {
            private final Button button = new Button(text);

            {
                button.setStyle("-fx-background-color: " + defaultColor + "; -fx-text-fill: white;");
                button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + hoverColor + "; -fx-text-fill: white;"));
                button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + defaultColor + "; -fx-text-fill: white;"));

                button.setOnAction(event -> {
                    Supplier supplier = getTableRow().getItem();
                    if (supplier != null) action.accept(supplier);
                });
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : button);
            }
        };
    }

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        openWindow("/view/add-Supplier-view.fxml", "Add Supplier");
    }

    @FXML
    void btnSearchSupplierOnAction(ActionEvent event) {
        String searchText = txtSearchSupplier.getText().toLowerCase();

        List<Supplier> filteredList = supplierService.getSuppliers().stream().filter(s -> s.getSupplierName().toLowerCase().contains(searchText) ||
                        s.getSupplierEmail().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        tblSupplier.setItems(FXCollections.observableArrayList(filteredList));
    }

    private void populateTable() {
        tblSupplier.setItems(FXCollections.observableArrayList(supplierService.getSuppliers()));
    }

    private void openUpdateSupplierView(Supplier supplier) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update-supplier-view.fxml"));
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Update Supplier");
            stage.setResizable(false);

            UpdateSupplierViewController controller = loader.getController();
            controller.setSupplier(supplier);

            stage.setOnHidden(event -> populateTable());
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteSupplier(Supplier supplier) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this supplier? This action cannot be undone.", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean isDeletedSupplier = supplierService.deleteSupplier(supplier.getSupplierId());

            if (isDeletedSupplier) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully.", ButtonType.OK);
                successAlert.showAndWait();
                populateTable();
            } else {
                Alert failureAlert = new Alert(Alert.AlertType.ERROR, "Failed to delete the supplier. Please try again.", ButtonType.OK);
                failureAlert.showAndWait();
            }
        }
    }

    private void openWindow(String resource, String title) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(resource))));
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
