package com.example.topicostrab2.service;

import com.example.topicostrab2.model.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductService {

    @GET("product/")
    Call<ArrayList<Product>> listProducts();

    @POST("product/")
    Call<Product> postProduct(@Body Product product);

    @PUT("product/{id}")
    Call<Product> putProduct(@Path("id") long id, @Body Product product);

    @DELETE("product/{id}")
    Call<Void> deleteProduct(@Path("id") long id);
}
