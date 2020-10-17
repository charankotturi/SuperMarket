package com.example.supermarket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.supermarket.ThirdCartFragment.CALL_BACK;

public class SecondCartFragment extends Fragment {

    public static final String ORDER_CALL = "order_call";

    private TextView editTxtAddress, editTxtZipCode, editTxtPhoneNumber, editTxtEmailId;
    private Button btnPrevious, btnNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.second_cart_fragment, container, false);
        initView(view);

        Gson gson = new Gson();
        final Bundle bundle2 = getArguments();
        if(bundle2 != null) {
            final String jsonObject = bundle2.getString(CALL_BACK);
            Type type = new TypeToken<OrderObject>() {
            }.getType();
            final OrderObject orderObject = gson.fromJson(jsonObject, type);
            if (orderObject != null) {
                editTxtZipCode.setText(orderObject.getZipCode());
                editTxtAddress.setText(orderObject.getAddress());
                editTxtPhoneNumber.setText(orderObject.getPhoneNumber());
                editTxtEmailId.setText(orderObject.getEmailId());
            }

        }

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, new FirstCartFragment());
                transaction.commit();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<groceryItems> allOrderItems = Utils.getCartList(getActivity());
                System.out.println(allOrderItems);
                if( allOrderItems != null){
                    OrderObject orderObject = new OrderObject();
                    String address = String.valueOf(editTxtAddress.getText());
                    String ZipCode = String.valueOf(editTxtZipCode.getText());
                    String PhoneNumber = String.valueOf(editTxtPhoneNumber.getText());
                    String EmailId = String.valueOf(editTxtEmailId.getText());


                    orderObject.setItems(allOrderItems);
                    orderObject.setAddress(address);
                    orderObject.setEmailId(EmailId);
                    orderObject.setZipCode(ZipCode);
                    orderObject.setPhoneNumber(PhoneNumber);

                    Gson gson = new Gson();
                    String orderObjectCall = gson.toJson(orderObject);
                    Bundle bundle = new Bundle();
                    bundle.putString(ORDER_CALL, orderObjectCall);

                    System.out.println("it is working till here...");
                    ThirdCartFragment thirdCartFragment = new ThirdCartFragment();
                    thirdCartFragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, thirdCartFragment);
                    transaction.commit();
                }

            }
        });

        return view;
    }

    private void initView(View view){
        editTxtAddress = view.findViewById(R.id.editTxtAddress);
        editTxtEmailId = view.findViewById(R.id.editTxtEmailId);
        editTxtPhoneNumber = view.findViewById(R.id.editTxtPhoneNumber);
        editTxtZipCode = view.findViewById(R.id.editTxtZipCode);
        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
    }
}
