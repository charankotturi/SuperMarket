package com.example.supermarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.util.Util;

public class PaymentResultFragment extends Fragment {

    private TextView txtMessage;
    private Button btnHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_result_fragment, container, false);
        initView(view);


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(intent);
            }
        });

        Utils.clearCartItems(getActivity());
        System.out.print(Utils.getCartList(getActivity()));

        return view;
    }
    private void initView(View view) {
        txtMessage = view.findViewById(R.id.txtSuccessPayment);
        btnHome = view.findViewById(R.id.btnShopping);
    }
}
