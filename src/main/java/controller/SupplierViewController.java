package controller;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import config.AppModule;
import dto.Employee;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import dto.Supplier;
import service.ServiceFactory;
import service.custom.SupplierService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SupplierViewController implements Initializable {

    @Inject
    SupplierService supplierService;

    List<Supplier> supplierList = new ArrayList<>();

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
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add-Supplier-view.fxml"))));
            stage.setTitle("Add Supplier");
            stage.setResizable(false);
            stage.show();

            stage.setOnHidden(e -> Platform.runLater(() -> populateTable()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        supplierList.clear();
        supplierList.addAll(supplierService.getSuppliers());
        tblSupplier.setItems(FXCollections.observableArrayList(supplierList));
    }

    private void openUpdateSupplierView(Supplier supplier) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/update-Supplier-view.fxml"));
            Stage stage = new Stage();
            Injector injector = Guice.createInjector(new AppModule());
            loader.setControllerFactory(injector::getInstance);
            stage.setScene(new Scene(loader.load()));

            UpdateSupplierViewController controller = loader.getController();
            controller.setSupplier(supplier);

            stage.setTitle("Update Customer");
            stage.setResizable(false);
            stage.show();

            stage.setOnHidden(e -> Platform.runLater(() -> populateTable()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteSupplier(Supplier supplier) {
        if (supplier == null) return;

        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Delete Supplier");
        deleteAlert.setHeaderText("Do you want to delete supplier: " + supplier.getSupplierName() + "?");
        deleteAlert.setContentText("Click 'Delete' to confirm, or 'Cancel' to abort.");

        Optional<ButtonType> result = deleteAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText("Are you sure you want to delete the supplier: " + supplier.getSupplierName() + "?");
            confirmationAlert.setContentText("This action cannot be undone.");

            Optional<ButtonType> confirmationResult = confirmationAlert.showAndWait();

            if (confirmationResult.isPresent() && confirmationResult.get() == ButtonType.OK) {
                boolean isSupplierDeleted = supplierService.deleteSupplier(supplier.getSupplierId());

                if (isSupplierDeleted) {
                    Platform.runLater(() -> populateTable());
                }
            }
        }
    }


}
