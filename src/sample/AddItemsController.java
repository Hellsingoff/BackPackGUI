package sample;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

public class AddItemsController {
    @FXML private TableView<Item> itemTable;
    @FXML private TableColumn<Item, String> name;
    @FXML private TableColumn<Item, Integer> mass, price;
    @FXML private TextField nameEdit;
    @FXML private Spinner<Integer> massEdit, priceEdit, bpSize;
    @FXML private Button removeItem, calculate;

    private static int size;
    static final ObservableList<Item> result = FXCollections.observableArrayList();
    private static final ObservableList<Item> itemList = FXCollections.observableArrayList();
    protected static final Items resultItems = new Items();

    public static int getSize() {
        return size;
    }

    public static ObservableList<Item> getItemList() {
        return itemList;
    }

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
        massEdit.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            massEdit.getStyleClass().clear();
            massEdit.getStyleClass().add("spinner");
            char key = event.getCharacter().charAt(0);
            if('0' > key || key > '9') {
                massEdit.getStyleClass().add("error");
                event.consume();
            }
        });
        priceEdit.valueProperty().addListener((obs, oldValue, newValue) -> {
            priceEdit.getStyleClass().clear();
            priceEdit.getStyleClass().add("spinner");
        });
        priceEdit.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            priceEdit.getStyleClass().clear();
            priceEdit.getStyleClass().add("spinner");
            char key = event.getCharacter().charAt(0);
            if('0' > key || key > '9'){
                priceEdit.getStyleClass().add("error");
                event.consume();
            }
        });
        bpSize.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            bpSize.getStyleClass().clear();
            bpSize.getStyleClass().add("spinner");
            char key = event.getCharacter().charAt(0);
            if('0' > key || key > '9') {
                bpSize.getStyleClass().add("error");
                event.consume();
            }
        });
        bpSize.valueProperty().addListener((obs, oldValue, newValue) -> {
            bpSize.getStyleClass().clear();
            bpSize.getStyleClass().add("spinner");
        });
    }

    public void back() {
        Main.background.getChildren().set(1, Main.start);
    }

    private boolean validValue(String value) {
        try {
            int num = Integer.parseInt(value);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void addItem() {
        if (validValue(massEdit.getEditor().getText()) && validValue(priceEdit.getEditor().getText())) {
            itemList.add(new Item(nameEdit.getText(), massEdit.getValue(), priceEdit.getValue()));
            calculate.setDisable(false);
            nameEdit.setText("");
        }
        else
        {
            if (!validValue(massEdit.getEditor().getText()))
                massEdit.getStyleClass().add("error");
            if (!validValue(priceEdit.getEditor().getText()))
                priceEdit.getStyleClass().add("error");
        }
    }

    public void calculate() {
        if (!validValue(bpSize.getEditor().getText()))
            bpSize.getStyleClass().add("error");
        else {
            BackpackFilling task = new BackpackFilling();
            task.setOnSucceeded(successEvent -> Main.background.getChildren().set(1, Main.end));
            size = bpSize.getValue();
            new Thread(task).start();
            Main.background.getChildren().set(1, Main.loading);
        }
    }

    public void removeItem() {
        itemTable.getItems().removeAll(itemTable.getSelectionModel().getSelectedItems());
        if (itemList.size() == 0) calculate.setDisable(true);
    }

    public static void reset() {
        resultItems.clone(new Items());
        result.clear();
        itemList.clear();
        Main.background.getChildren().set(1, Main.start);
    }

    public static ObservableList<Item> getResult() {
        return result;
    }

    public static Items getResultItems() {
        return resultItems;
    }
}
