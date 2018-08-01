package me.latonis.jacob.todo;
import java.util.Calendar;

public class ListItem {
    private String input;
    private String dateCreated;
    ListItem() {
        date();
    }
    ListItem(String input) {
        this.input = input;
        date();
    }

    public int getLength() {
        return input.length();
    }

    public String getDate() {
        return dateCreated;
    }

    private void date() {
        this.dateCreated = Calendar.MONTH + "/" + Calendar.DAY_OF_MONTH;
    }
}
