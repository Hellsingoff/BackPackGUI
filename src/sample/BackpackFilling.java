package sample;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.Collections;

import static sample.AddItemsController.result;
import static sample.AddItemsController.resultItems;

public class BackpackFilling extends Task<String> {

    @Override
    protected String call() {
        ObservableList<Item> itemList = AddItemsController.getItemList();
        removeUseless(itemList);
        if (StartController.isGreed() && itemList.size() > 0)
            resultItems.clone(greed(itemList));
        resultItems.clone(fillTheBackpack(itemList));
        for (Item item : resultItems.getMap().keySet()) {
            item.setAmount(resultItems.getMap().get(item));
            result.add(item);
        }
        return null;
    }

    private static void removeUseless(ObservableList<Item> itemList) {
        Collections.sort(itemList);
        outsideLoop:
        for (int n = itemList.size() - 1; n >= 0; n--) {
            int firstMass = itemList.get(n).getMass();
            if (firstMass > AddItemsController.getSize() || itemList.get(n).getPrice() <= 0) {
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
        int greedFilled = AddItemsController.getSize()/topItem.getMass();
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

    protected static Items fillTheBackpack(ObservableList<Item> itemList){
        Items result = new Items();
        fillTheBackpack(resultItems, itemList, itemList.size() - 1, result);
        return result;
    }

    private static void fillTheBackpack(Items greedFilled, ObservableList<Item> itemList, int n, Items result){
        for (; n >= 0; n--) {
            Items items = new Items(greedFilled);
            for (int j = n; j >= 0; j--) {
                int availableMass = AddItemsController.getSize() - items.getMass();
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
}
