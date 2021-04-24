package com.example.topicostrab2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.topicostrab2.model.Product;
import com.example.topicostrab2.service.ProductService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastrarActivity extends AppCompatActivity {

    private TextInputEditText etName, etDescricao, etPreco, etStockLevel;
    private CheckBox cbAtivo;
    private Button btCadastrar;
    private Retrofit retrofit;
    private ProductService pService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        etName = this.findViewById(R.id.et_name);
        etDescricao = this.findViewById(R.id.et_descricao);
        etPreco =this.findViewById(R.id.et_preco);
        etStockLevel = this.findViewById(R.id.et_stockLevel);
        cbAtivo = this.findViewById(R.id.cb_ativo);
        this.cbAtivo.setActivated(true);
        btCadastrar = this.findViewById(R.id.bt_cadastrar);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://guisfco-online-shopping-api.herokuapp.com/api/online-shopping/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        pService =retrofit.create(ProductService.class);
        this.btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }

    private void cadastrar() {
        Product product = new Product();
        product.setName(this.etName.getText().toString());
        product.setDescription(this.etDescricao.getText().toString());
        product.setPrice(Float.parseFloat(this.etPreco.getText().toString()));
        product.setStockLevel(Integer.parseInt(this.etStockLevel.getText().toString()));
        product.setEnabled(true);
        product.setCreationTimestamp("");
        pService.postProduct(product).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Produto criado com sucesso", Toast.LENGTH_LONG);
                    toast.show();
                    limpar();
                }else{
                    Toast.makeText(CadastrarActivity.this,"Erro no cadastro de produto",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

                Toast.makeText(CadastrarActivity.this,"Erro de Conexão",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limpar() {
        this.etName.getText().clear();
        this.etDescricao.getText().clear();
        this.etPreco.getText().clear();
        this.etStockLevel.getText().clear();
        this.cbAtivo.setActivated(true);
    }
}