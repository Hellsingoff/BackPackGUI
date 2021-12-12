package sample;

import javafx.concurrent.Task;

public class BackpackFilling extends Task<String> {
    @Override
    protected String call() {
        System.out.println("inside");
        return "123";
    }
}
