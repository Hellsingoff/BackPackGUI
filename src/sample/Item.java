package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
    private final StringProperty name;
    private final DoubleProperty mass, price;
    private final double greed;
    private int iMass;
    private static int counter = 1;

    public Item(String name, double mass, double price) {
        iMass = (int) (mass * 1000 + 0.5);
        if (iMass == 0) {
            iMass = 1;
            this.mass = new SimpleDoubleProperty(0.001);
        }
        else this.mass = new SimpleDoubleProperty(iMass / 1000.0);
        this.name = name.length() == 0 ? new SimpleStringProperty(String.valueOf(counter++)) : new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        greed = this.price.get() / this.mass.get();
    }

    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public double getMass() {
        return mass.get();
    }
    public DoubleProperty massProperty() {
        return mass;
    }
    public double getPrice() {
        return price.get();
    }
    public DoubleProperty priceProperty() {
        return price;
    }
    public double getGreed() {
        return greed;
    }
    public double getIMass() {
        return iMass;
    }
}