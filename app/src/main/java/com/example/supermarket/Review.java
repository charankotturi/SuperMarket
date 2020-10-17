package com.example.supermarket;

public class Review {
    private int groceryItemId;
    private String userName;
    private String Text;
    private String date;

    public Review(int groceryItemId, String userName, String text, String date) {
        this.groceryItemId = groceryItemId;
        this.userName = userName;
        Text = text;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Review{" +
                "groceryItemId=" + groceryItemId +
                ", userName='" + userName + '\'' +
                ", Text='" + Text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public int getGroceryItemId() {
        return groceryItemId;
    }

    public void setGroceryItemId(int groceryItemId) {
        this.groceryItemId = groceryItemId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
