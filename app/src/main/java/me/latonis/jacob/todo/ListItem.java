package me.latonis.jacob.todo;
import java.util.Calendar;
import java.util.Date;

public class ListItem {
    private String input;
    private Date dateCreated;
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
        return dateCreated.toString();
    }

    private void date() {
        dateCreated = Calendar.getInstance().getTime();
    }
}
