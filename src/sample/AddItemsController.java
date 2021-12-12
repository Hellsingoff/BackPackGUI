package sample;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.util.Collections;

public class AddItemsController {
    @FXML private TableView<Item> itemTable;
    @FXML private TableColumn<Item, String> name;
    @FXML private TableColumn<Item, Integer> mass, price;
    @FXML private TextField nameEdit;
    @FXML private Spinner<Integer> massEdit, priceEdit, bpSize;
    @FXML private Button removeItem, calculate;

    private static int size;
    private static final ObservableList<Item> result = FXCollections.observableArrayList();
    private static final ObservableList<Item> itemList = FXCollections.observableArrayList();
    private static final Items resultItems = new Items();

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
            new Thread(task).start();
            //After the completion of the task, start fadeOut animation
            task.setOnSucceeded(successEvent -> {
                System.out.println("suckass");
            });

            size = bpSize.getValue();
            Main.background.getChildren().set(1, Main.loading);
            removeUseless(itemList);
            if (StartController.isGreed() && itemList.size() > 0)
                resultItems.clone(greed(itemList));
            resultItems.clone(fillTheBackpack(resultItems, itemList));
            for (Item item : resultItems.getMap().keySet()) {
                item.setAmount(resultItems.getMap().get(item));
                result.add(item);
            }
            Main.background.getChildren().set(1, Main.end);
        }
    }

    public void removeItem() {
        itemTable.getItems().removeAll(itemTable.getSelectionModel().getSelectedItems());
        if (itemList.size() == 0) calculate.setDisable(true);
    }

    private static void removeUseless(ObservableList<Item> itemList) {
        Collections.sort(itemList);
        outsideLoop:
        for (int n = itemList.size() - 1; n >= 0; n--) {
            int firstMass = itemList.get(n).getMass();
            if (firstMass > size || itemList.get(n).getPrice() <= 0) {
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
    }

    protected static Items greed(ObservableList<Item> itemList) {
        int min = Integer.MAX_VALUE;
        Item topItem = itemList.get(0);
        for (Item item : itemList)
            if (item.getGreed() >= topItem.getGreed())
                topItem = item;
        int greedFilled = size/topItem.getMass();
        double topGreed = topItem.getGreed();
        for (Item item : itemList) {
            double temp = topGreed*2 - item.getGreed();
            int num = (int) (topGreed / temp * greedFilled);
            if (num < min) min = num - 1;
        }
        Items result = new Items();
        if (min > 0 && min < Integer.MAX_VALUE) result.add(topItem, min);
        return result;
    }

    protected static Items fillTheBackpack(Items greedFilled, ObservableList<Item> itemList){
        Items result = new Items();
        fillTheBackpack(greedFilled, itemList, itemList.size() - 1, result);
        return result;
    }

    private static void fillTheBackpack(Items greedFilled, ObservableList<Item> itemList, int n, Items result){
        for (; n >= 0; n--) {
            Items items = new Items(greedFilled);
            for (int j = n; j >= 0; j--) {
                int availableMass = size - items.getMass();
                if (availableMass >= itemList.get(j).getMass()) {
                    items.add(itemList.get(j));
                    fillTheBackpack(items, itemList, j, result);
                } else if (availableMass >= itemList.get(0).getMass())
                    fillTheBackpack(items, itemList, j - 1, result);
                else {
                    if (result.getPrice() < items.getPrice())
                        result.clone(items);
                    break;
                }
            }
        }
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
