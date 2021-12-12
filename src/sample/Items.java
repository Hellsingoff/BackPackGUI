package sample;

import java.util.HashMap;
import java.util.Map;

public class Items implements Comparable<Items> {
    private Map<Item, Integer> map;
    private int price, mass;

    Items() {
        map = new HashMap<>();
        price = 0;
        mass = 0;
    }

    Items(Items items) {
        map = new HashMap<>(items.getMap());
        price = items.getPrice();
        mass = items.getMass();
    }

    public void add(Item item) {
        price += item.getPrice();
        mass += item.getMass();
        if (map.containsKey(item))
            map.put(item, map.get(item) + 1);
        else {
            map.put(item, 1);
        }
    }

    public void add(Item item, int num) {
        price += item.getPrice() * num;
        mass += item.getMass() * num;
        if (map.containsKey(item))
            map.put(item, map.get(item) + num);
        else {
            map.put(item, num);
        }
    }

    public void clone(Items items) {
        this.mass = items.getMass();
        this.price = items.getPrice();
        this.map = new HashMap<>(items.getMap());
    }

    public int compareTo(Items items) {
        return Integer.compare(items.getPrice(), price);
    }

    public Map<Item, Integer> getMap() { return map; }
    public int getPrice() { return price; }
    public int getMass() { return mass; }
}
