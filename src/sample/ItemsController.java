package sample;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ItemsController {
    @FXML private TableView<Item> itemTable;
    @FXML private TableColumn<Item, String> name;
    @FXML private TableColumn<Item, Double> mass, price;
    @FXML private TextField nameEdit;
    @FXML private Spinner<Double> massEdit, priceEdit;
    @FXML private Button removeItem, calculate;

    private ObservableList<Item> itemList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        mass.setCellValueFactory(cellData -> cellData.getValue().massProperty().asObject());
        price.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        itemTable.setItems(itemList);
        removeItem.disableProperty().bind(Bindings.isEmpty(itemTable.getSelectionModel().getSelectedItems()));
        massEdit.valueProperty().addListener((obs, oldValue, newValue) -> {
            massEdit.getStyleClass().clear();
            massEdit.getStyleClass().add("spinner");
        });
        massEdit.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //if(!valid.checkNumeric(event.getCharacter()))
                    event.consume();
            }});
    }

    public void back(MouseEvent mouseEvent) {
        Main.background.getChildren().set(1, Main.start);
    }

    public void addItem(MouseEvent mouseEvent) {
        if (massEdit.getValue() > 0 && priceEdit.getValue() > 0) {
            itemList.add(new Item(nameEdit.getText(), massEdit.getValue(), priceEdit.getValue()));
            calculate.setDisable(false);
            nameEdit.setText("");
        }
        else
        {
            massEdit.getStyleClass().add("error");
        }
    }

    public void calculate(MouseEvent mouseEvent) {
        
    }

    public void removeItem(MouseEvent mouseEvent) {
        itemTable.getItems().removeAll(itemTable.getSelectionModel().getSelectedItems());
        if (itemList.size() == 0) calculate.setDisable(true);
    }
}
