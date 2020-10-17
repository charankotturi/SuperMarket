package com.example.supermarket;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class groceryItems implements Parcelable {
    private int ID;
    private String name;
    private String description;
    private String imageUrl;
    private String category;
    private double price;
    private int rating;
    private float availableAmount;
    private int userPoints;
    private int popularityPoints;
    private ArrayList<Review> reviews;


    public groceryItems(String name, String description, String imageUrl, String category, double price, float availableAmount) {
        this.ID = Utils.getId();
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.price = price;
        this.availableAmount = availableAmount;
        this.rating = 0;
        this.userPoints = 0;
        this.popularityPoints = 0;
        this.reviews = new ArrayList<>();
    }

    protected groceryItems(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        category = in.readString();
        price = in.readDouble();
        rating = in.readInt();
        availableAmount = in.readFloat();
        userPoints = in.readInt();
        popularityPoints = in.readInt();
    }

    public static final Creator<groceryItems> CREATOR = new Creator<groceryItems>() {
        @Override
        public groceryItems createFromParcel(Parcel in) {
            return new groceryItems(in);
        }

        @Override
        public groceryItems[] newArray(int size) {
            return new groceryItems[size];
        }
    };

    @Override
    public String toString() {
        return "groceryItems{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                ", availableAmount=" + availableAmount +
                ", userPoints=" + userPoints +
                ", popularityPoints=" + popularityPoints +
                ", reviews=" + reviews +
                '}';
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(float availableAmount) {
        this.availableAmount = availableAmount;
    }

    public int getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
    }

    public int getPopularityPoints() {
        return popularityPoints;
    }

    public void setPopularityPoints(int popularityPoints) {
        this.popularityPoints = popularityPoints;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeString(category);
        dest.writeDouble(price);
        dest.writeInt(rating);
        dest.writeFloat(availableAmount);
        dest.writeInt(userPoints);
        dest.writeInt(popularityPoints);
    }
}
