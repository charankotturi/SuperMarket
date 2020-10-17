package com.example.supermarket;

import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static int ID = 0;
    private static int ORDER_ID = 0;

    public static final String TAG = "IN utils class!";
    private static String DB_NAME = "data_base";
    private static Gson gson = new Gson();
    private static Type groceryItemType = new TypeToken< ArrayList<groceryItems> >(){}.getType();
    private static String ALL_ITEMS = "all_items";
    private static final String CART_ITEMS = "all_cart_items";

    public static void initSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<groceryItems> allItems1 = gson.fromJson(sharedPreferences.getString(ALL_ITEMS, null), groceryItemType);

        if (null == allItems1) {
            initAllItems(context);
        }

    }

    public static ArrayList<groceryItems> searchItem(Context context, String text){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<groceryItems> allItems1 = gson.fromJson(sharedPreferences.getString(ALL_ITEMS, null), groceryItemType);
        if(allItems1 != null){
            ArrayList<groceryItems> newItems = new ArrayList<>();
            for(groceryItems item: allItems1){
                if(text.equalsIgnoreCase(item.getName())){
                    newItems.add(item);
                }

                String[] names = item.getName().split(" ");
                for (int i=0; i<names.length; i++){
                    if(text.equalsIgnoreCase(names[i])){
                        boolean doesExist = false;

                        for (groceryItems j:newItems){
                            if (item.getID() == j.getID()){
                                doesExist = true;
                            }
                        }

                        if (!doesExist){
                            newItems.add(item);
                        }
                    }
                }
            }
            return newItems;
        }
        return null;
    }

    public static void addItemToCart(Context context, groceryItems item) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<groceryItems> allCartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS, null), groceryItemType);
        if (allCartItems == null){
            allCartItems = new ArrayList<>();
            allCartItems.add(item);
        }else{
            boolean exists = false;
            for(groceryItems items: allCartItems){
                if(item.getID() == items.getID()){
                    exists = true;
                }
            }
            if(!exists){
                allCartItems.add(item);
            }
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEMS);
        editor.putString(CART_ITEMS, gson.toJson(allCartItems));
        editor.commit();
    }

    public static ArrayList<groceryItems> getCartList(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
        ArrayList<groceryItems> allCartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS, null), groceryItemType);
        return allCartItems;
    }

    public static ArrayList<Review> getReviews(Context context, int ItemId){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<groceryItems> allItem = gson.fromJson(sharedPreferences.getString(ALL_ITEMS, null), groceryItemType);
        ArrayList<Review> getReview = new ArrayList<>();
        if(allItem != null) {
            for (groceryItems i : allItem) {
                if (i.getID() == ItemId) {
                    Log.d(TAG, "getReviews: >>>>>>>>>>>>>///return success!!!!");
                    Log.d(TAG, "getReviews: " + i.getReviews());
                    getReview = i.getReviews();
                    return getReview;
                }
            }
        }
        return getReview;
    }

    public static boolean addReview(Context context, Review review) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<groceryItems> allItem = gson.fromJson(sharedPreferences.getString(ALL_ITEMS, null), groceryItemType);
        if(allItem != null) {
            ArrayList<groceryItems> newReviews = new ArrayList<>();
            for(groceryItems i: allItem) {
                ArrayList<Review> allReviews = i.getReviews();
                if(review.getGroceryItemId() == i.getID()) {
                    allReviews.add(review);
                    i.setReviews(allReviews);
                    newReviews.add(i);
                }else{
                    newReviews.add(i);
                }
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(ALL_ITEMS);
            editor.putString(ALL_ITEMS, gson.toJson(newReviews));
            editor.commit();

            return true;
        }else{
            return false;
        }
    }

    public static void clearSharedPreferences(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME ,Context.MODE_PRIVATE);
        SharedPreferences.Editor cEditor = sharedPreferences.edit();
        cEditor.clear();
        cEditor.commit();
    }

    public static void clearCartItems(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME ,Context.MODE_PRIVATE);
        SharedPreferences.Editor cartEditor = sharedPreferences.edit();
        cartEditor.remove(CART_ITEMS);
        cartEditor.commit();
    }

    private static void initAllItems(Context context){

        ArrayList<groceryItems> allItems = new ArrayList<>();

        groceryItems item = new groceryItems("Potatoes","only for cooking purpose.",
                "https://s.shld.net/is/image/Sears/potato%20baking%201-qm-fmt-eq-jpeg-amp-wid-eq-350-amp-hei-eq-350-amp-qlt-eq-90,0-amp-resMode-eq-sharp-amp-op_usm-eq-0.9,0.5,0,0,-amp-jpegSize-eq-100-amp-bgc-eq-ffffff"
                ,"Vegetables",900,1000);
        item.setUserPoints(3);
        item.setPopularityPoints(2);
        allItems.add(item);

        groceryItems item3 = new groceryItems("Mountain Dew", "get that shit in your bones.",
                "https://www.etikaholdings.com/sites/default/files/images/product/Mountain-Dew-Can_330ml.png",
                "Soft drinks", 60.00,920);
        item3.setUserPoints(2);
        item3.setPopularityPoints(4);
        allItems.add(item3);

        groceryItems item2 = new groceryItems("Bread", "Original now chemcial used homemade bread straight to your house.",
                "https://png.pngtree.com/png-clipart/20190925/original/pngtree-fresh-baked-loafs-of-bread-png-image_4992649.jpg",
                "Bread",20.3,520);
        item2.setUserPoints(1);
        item2.setPopularityPoints(3);
        allItems.add(item2);

        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME ,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_ITEMS, gson.toJson(allItems));
        editor.commit();

    }

    public static void changeRating(Context context, int ItemID, int setRating) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME ,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<groceryItems> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS,null), groceryItemType);

        if(allItems != null) {
            ArrayList<groceryItems> newItems = new ArrayList<>();
            for(groceryItems i: allItems) {
                if(i.getID() == ItemID) {
                    i.setRating(setRating);
                    newItems.add(i);
                }else{
                    newItems.add(i);
                }
            }

            editor.remove(ALL_ITEMS);
            editor.putString(ALL_ITEMS, gson.toJson(newItems));
            editor.commit();
        }

    }

    public static ArrayList<groceryItems> getAllItems (Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME ,Context.MODE_PRIVATE);
        ArrayList<groceryItems> allItems3 = gson.fromJson(sharedPreferences.getString(ALL_ITEMS, null), groceryItemType);
        return allItems3;
    }

    public static int getId() {
        ID++;
        return ID;
    }

    public static int getOrderId() {
        ORDER_ID++;
        return ORDER_ID;
    }

    public static ArrayList<String> getCategories(Context context){
        ArrayList<groceryItems> allItems = Utils.getAllItems(context);
        if(null != allItems) {
            ArrayList<String> categories = new ArrayList<>();
            for (groceryItems item: allItems){
                boolean doesExits = false;
                for (String i: categories){
                    if(i.equals(item.getCategory())){
                        doesExits = true;
                    }
                }

                if(!doesExits){
                    categories.add(item.getCategory());
                }
            }
            return categories;
        }
        return null;
    }

    public static ArrayList<groceryItems> getListCategoryItems(Context context, String itemCategory){
        ArrayList<groceryItems> allItems = Utils.getAllItems(context);
        if(allItems != null){
            ArrayList<groceryItems> newItems = new ArrayList<>();
            for (groceryItems i: allItems){
                if (i.getCategory().equals(itemCategory)){
                    newItems.add(i);
                }
            }
            return newItems;
        }
        return null;
    }

    public static ArrayList<groceryItems> deleteCartItem(Context context, int ItemId){
        ArrayList<groceryItems> allCartItems = getCartList(context);
        System.out.println(ItemId);
        if(allCartItems != null) {
            ArrayList<groceryItems> newItems = new ArrayList<>();
            for(groceryItems items:allCartItems){
                if(items.getID() != ItemId){
                    newItems.add(items);
                }
            }
            System.out.println(newItems);

            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(CART_ITEMS);
            editor.putString(CART_ITEMS, gson.toJson(newItems));
            editor.commit();

            return newItems;
        }

        return null;
    }
}
