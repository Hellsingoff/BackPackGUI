package sample;

import javafx.beans.property.*;

public class Item implements Comparable<Item> {
    private final StringProperty name;
    private final IntegerProperty mass, price;
    private final double greed;
    private static int counter = 1;

    public Item(String name, int mass, int price) {
        this.mass = new SimpleIntegerProperty(mass);
        this.price = new SimpleIntegerProperty(price);
        greed = (double) price/mass;
        this.name = name.length() == 0 ? new SimpleStringProperty(String.valueOf(counter++)) : new SimpleStringProperty(name);
    }

    public int compareTo(Item item) {
        if (item.getMass() == mass.get())
            return Integer.compare(item.getPrice(), price.get());
        return Integer.compare(mass.get(), item.getMass());
    }

    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public int getMass() { return mass.get(); }
    public IntegerProperty massProperty() {
        return mass;
    }
    public int getPrice() {
        return price.get();
    }
    public IntegerProperty priceProperty() {
        return price;
    }
    public double getGreed() {
        return greed;
    }
}