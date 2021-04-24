package com.example.topicostrab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.topicostrab2.model.Product;
import com.example.topicostrab2.service.ProductService;

import java.util.ArrayList;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView listProducts;
    private Retrofit retrofit;
    private ProductService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listProducts = findViewById(android.R.id.list);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://guisfco-online-shopping-api.herokuapp.com/api/online-shopping/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(ProductService.class);
        service .listProducts().enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                if(response.isSuccessful()){
                    listProducts.setAdapter(new ArrayAdapter<>(
                            getApplicationContext(),
                            R.layout.list_item,
                            response.body().stream()
                                    .map(product -> new StringBuilder(product.getName() + " : " + product.getDescription()))
                                    .collect(Collectors.toList())
                    ));
                }else{
                    Toast.makeText(MainActivity.this,"Erro no cadastro de produto",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Verifique sua internet",Toast.LENGTH_LONG).show();
            }
        });
    }
}