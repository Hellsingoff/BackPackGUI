package sample;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EndController {
    @FXML private Label bpPrice, bpWeight;
    @FXML private TableView<Item> itemTable;
    @FXML private TableColumn<Item, String> name;
    @FXML private TableColumn<Item, Integer> mass, price;
    @FXML private TableColumn<Item, Integer> amount;

    @FXML
    private void initialize() {
        ObservableList<Item> result = AddItemsController.getResult();
        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        mass.setCellValueFactory(cellData -> cellData.getValue().massProperty().asObject());
        price.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        amount.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        itemTable.setItems(result);
        Items items = AddItemsController.getResultItems();
        result.addListener((ListChangeListener<Item>) change -> {
            bpPrice.setText(Integer.toString(items.getPrice()));
            bpWeight.setText(Integer.toString(items.getMass()));
        });
    }

    public void reset() {
        AddItemsController.reset();
    }
}
