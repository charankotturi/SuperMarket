package com.example.supermarket;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.supermarket.SecondCartFragment.ORDER_CALL;

public class ThirdCartFragment extends Fragment {

    public static final String CALL_BACK = "call_back";
    private static final String TAG = "response_result";

    private TextView txtAddress, txtItems, txtPrice, txtNumber, txtWarning;
    private Button btnNext, btnPrevious;
    private RadioGroup btnPayment2;
    private RadioButton btnPaypal, btnCreditCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_cart_fragment, container, false);
        initView(view);

        final Gson gson = new Gson();
        final Bundle bundle = getArguments();
        if(bundle != null) {
            final String jsonObject = bundle.getString(ORDER_CALL);
            Type type = new TypeToken<OrderObject>() {
            }.getType();
            final OrderObject orderObject = gson.fromJson(jsonObject, type);

            if(orderObject != null){
                String items = "";
                int TotalPrice = 0 ;
                for(groceryItems i: orderObject.getItems()){
                    items += "\n\t" + i.getName();
                    TotalPrice += i.getPrice();
                }

                txtPrice.setText(String.valueOf(TotalPrice));
                txtItems.setText("\t" + items);
                txtNumber.setText("\t" + orderObject.getPhoneNumber());
                txtAddress.setText("\t" + orderObject.getAddress());

                btnPrevious.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle1 = new Bundle();
                        String callBackJson = gson.toJson(orderObject);
                        bundle1.putString(CALL_BACK, callBackJson);

                        SecondCartFragment secondCartFragment = new SecondCartFragment();
                        secondCartFragment.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, new SecondCartFragment());
                        transaction.commit();

                    }
                });

                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (btnPayment2.getCheckedRadioButtonId()){
                            case R.id.btnPaypal:
                                orderObject.setPaymentMethod("Paypal");
                                break;
                            case R.id.btnCreditCard:
                                orderObject.setPaymentMethod("CreditCard");
                                break;
                        }
                        orderObject.setSuccess(true);

                        PaymentResultFragment paymentResultFragment = new PaymentResultFragment();

                        System.out.println(orderObject);

                        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY);

                        OkHttpClient client = new OkHttpClient.Builder()
                                .addInterceptor(interceptor)
                                .build();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://jsonplaceholder.typicode.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .client(client)
                                .build();

                        EndPoint endPoint = retrofit.create(EndPoint.class);
                        Call<OrderObject> call = endPoint.newObjects(orderObject);
                        call.enqueue(new Callback<OrderObject>() {
                            @Override
                            public void onResponse(Call<OrderObject> call, Response<OrderObject> response) {
                                if(response.isSuccessful()){
                                    Log.d(TAG, "onResponse: Successful");

                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frameLayout, paymentResultFragment);
                                    transaction.commit();
                                }else{
                                    Log.d(TAG, "onResponse: Successful");

                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frameLayout, paymentResultFragment);
                                    transaction.commit();
                                }
                            }
                            @Override
                            public void onFailure(Call<OrderObject> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });


                    }
                });
            }
        }

        return view;
    }

    private void initView(View view){
        txtItems = view.findViewById(R.id.txtItems3);
        txtAddress = view.findViewById(R.id.txtAddress3);
        txtNumber = view.findViewById(R.id.txtPhoneNumber3);
        txtPrice = view.findViewById(R.id.txtPrice3);
        btnNext = view.findViewById(R.id.btnNext3);
        btnPrevious = view.findViewById(R.id.btnPrevious3);
        txtWarning = view.findViewById(R.id.txtWarning3);
        btnPaypal = view.findViewById(R.id.btnPaypal);
        btnCreditCard = view.findViewById(R.id.btnCreditCard);
        btnPayment2 = view.findViewById(R.id.btnPayment2);
    }
}
