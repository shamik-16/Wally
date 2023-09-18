package com.example.shamik.wally;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ImageModel> modelClassList;
    MyAdapter myAdapter;

    CardView nature,bike,car,game,trending;
    EditText edtSearch;
    ImageButton search;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        nature = findViewById(R.id.nature);
        car = findViewById(R.id.car);
        bike = findViewById(R.id.bike);
        game = findViewById(R.id.game);
        trending = findViewById(R.id.trending);
        edtSearch = findViewById(R.id.edt_search);
        search = findViewById(R.id.btn_search);
        progressBar = findViewById(R.id.pg_bar);

        // progress bar function :
        progress();

        // Connectivity dialog box : method connection available is define below -
        if(!isConnectionAvailable()){

            Dialog dialog = new Dialog(MainActivity.this,R.style.noInternetDialog);
            dialog.setContentView(R.layout.internet_connection_dialog);
            Button retry = dialog.findViewById(R.id.btn);
            retry.setOnClickListener(v -> finishAffinity());
            dialog.show();
        }

        modelClassList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        myAdapter = new MyAdapter(getApplicationContext(), modelClassList);
        recyclerView.setAdapter(myAdapter);
        findPhotos();

        // Section on click listeners :

        nature.setOnClickListener(v -> {
            progress();
            String query="nature";
            getSearchImage(query);
        });

        bike.setOnClickListener(v -> {
            progress();
            String query="sports bike";
            getSearchImage(query);
        });

        car.setOnClickListener(v -> {
            progress();
            String query="car";
            getSearchImage(query);
        });

        game.setOnClickListener(v -> {
            progress();
            String query="game";
            getSearchImage(query);
        });

        trending.setOnClickListener(v -> {
            progress();
            String query="trending wallpapers";
            getSearchImage(query);
        });

        search.setOnClickListener(v -> {
            String query = edtSearch.getText().toString().trim().toLowerCase();
            if(query.isEmpty()){
                Toast.makeText(getApplicationContext(),"Write something",Toast.LENGTH_LONG).show();
            }else{
                progress();
                getSearchImage(query);
            }
        });

    }

    private void getSearchImage(String query) {

        ApiUtilities.getApiInterface().getSearchImage(query,1,80).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(@NonNull Call<SearchModel> call, @NonNull Response<SearchModel> response) {

                modelClassList.clear();
                if(response.isSuccessful()){
                    assert response.body() != null;
                    modelClassList.addAll(response.body().getPhotos());
                    myAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<SearchModel> call, @NonNull Throwable t) {

            }
        });

    }

    private void findPhotos() {

        modelClassList.clear();

        ApiUtilities.getApiInterface().getImage(1,80).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(@NonNull Call<SearchModel> call, @NonNull Response<SearchModel> response) {

                if(response.isSuccessful()){
                    assert response.body() != null;
                    modelClassList.addAll(response.body().getPhotos());
                    myAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<SearchModel> call, @NonNull Throwable t) {

            }
        });

    }

    public void progress(){
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> progressBar.setVisibility(View.GONE),3000);
    }


    // Network checking :
    public boolean isConnectionAvailable(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isAvailable() || !networkInfo.isConnected()){
            return false;
        }
        return true;
    }

}