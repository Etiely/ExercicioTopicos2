package com.example.topicostrab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.topicostrab2.R;
import com.example.topicostrab2.model.Product;
import com.example.topicostrab2.service.ProductService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductActivity extends AppCompatActivity {

    private TextInputEditText tiName, tiDescricao, tiPreco, tiImageUrl, tiStockLevel;
    private CheckBox cbAtivo;
    private Button btEditar;
    private Button btExcluir;
    private Retrofit retrofit;
    private ProductService pService;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        product = (Product) bundle.getSerializable("product");
        tiName = this.findViewById(R.id.ti_editar_name);
        tiDescricao = this.findViewById(R.id.ti_editar_descricao);
        tiImageUrl = this.findViewById(R.id.ti_editar_imageUrl);
        tiPreco = this.findViewById(R.id.ti_editar_preco);
        tiStockLevel = this.findViewById(R.id.ti_editar_stockLevel);
        cbAtivo = this.findViewById(R.id.cb_ativo);
        tiName.setText(product.getName());
        tiDescricao.setText(product.getDescription());
        tiImageUrl.setText(product.getImageUrl());
        tiPreco.setText(String.valueOf(product.getPrice()));
        tiStockLevel.setText(String.valueOf(product.getStockLevel()));
        cbAtivo.setChecked(product.getEnabled());
        btEditar = this.findViewById(R.id.bt_editar);
        btExcluir = this.findViewById(R.id.bt_excluir);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://guisfco-online-shopping-api.herokuapp.com/api/online-shopping/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pService = retrofit.create(ProductService.class);
        this.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar();
            }
        });
        this.btExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluir();
            }
        });
    }

    private void editar() {
        product.setName(this.tiName.getText().toString());
        product.setDescription(this.tiDescricao.getText().toString());
        product.setPrice(Float.parseFloat(this.tiPreco.getText().toString()));
        product.setImageUrl(this.tiImageUrl.getText().toString());
        product.setStockLevel(Integer.parseInt(this.tiStockLevel.getText().toString()));
        product.setEnabled(cbAtivo.isChecked());
        pService.putProduct(product.getId(),product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Toast toast = Toast.makeText(getApplicationContext(), "Produto editado com sucesso", Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ProductActivity.this,"Erro de Conexão",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void excluir() {
        pService.deleteProduct(product.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(ProductActivity.this,"Item excluído com sucesso",Toast.LENGTH_LONG).show();
                Intent it = new Intent(ProductActivity.this, MainActivity.class);
                startActivity(it);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ProductActivity.this,"Verifique sua internet",Toast.LENGTH_LONG).show();
            }
        });
    }
}