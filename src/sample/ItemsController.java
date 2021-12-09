package sample;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Collections;

public class ItemsController {
    @FXML private TextField bpSize;
    @FXML private TableView<Item> itemTable;
    @FXML private TableColumn<Item, String> name;
    @FXML private TableColumn<Item, Integer> mass, price;
    @FXML private TextField nameEdit;
    @FXML private Spinner<Integer> massEdit, priceEdit;
    @FXML private Button removeItem, calculate;
    private static int size;

    private static final ObservableList<Item> itemList = FXCollections.observableArrayList();

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
            bpSize.getStyleClass().add("text-field");
            char key = event.getCharacter().charAt(0);
            if('0' > key || key > '9') {
                bpSize.getStyleClass().add("error");
                event.consume();
            }
        });
    }

    public void back(MouseEvent mouseEvent) {
        Main.background.getChildren().set(1, Main.start);
    }

    boolean validValue(String value) {
        try {
            int num = Integer.parseInt(value);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void addItem(MouseEvent mouseEvent) {
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

    public void calculate(MouseEvent mouseEvent) {
        if (!validValue(bpSize.getText()))
            priceEdit.getStyleClass().add("error");
        else {
            size = Integer.parseInt(bpSize.getText());
            Main.background.getChildren().set(1, Main.loading);
            removeUseless(ItemsController.getItemList());
        }
    }

    public void removeItem(MouseEvent mouseEvent) {
        itemTable.getItems().removeAll(itemTable.getSelectionModel().getSelectedItems());
        if (itemList.size() == 0) calculate.setDisable(true);
    }

    static void removeUseless(ObservableList<Item> itemList) {
        System.out.println("got it");
        Collections.sort(itemList);
        outsideLoop:
        for (int n = itemList.size() - 1; n >= 0; n--) {
            int firstMass = itemList.get(n).getMass();
            if (firstMass > ItemsController.getSize() || itemList.get(n).getPrice() <= 0) {
                itemList.remove(n);
                continue;
            }
            for (int j = n - 1; j >= 0; j--) {
                if (firstMass/itemList.get(j).getMass() * itemList.get(j).getPrice() >= itemList.get(n).getPrice()) {
                    itemList.remove(n);
                    continue outsideLoop;
                }
            }
        }
        if (itemList.size() == 0) {
            System.out.println("В рюкзак ничего нельзя добавить.");
        }
    }

    public static ObservableList<Item> getItemList() {
        return itemList;
    }

    public static int getSize() {
        return size;
    }
}
