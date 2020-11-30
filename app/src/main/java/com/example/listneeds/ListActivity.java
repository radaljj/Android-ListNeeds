package com.example.listneeds;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private EditText name;
    private Button snimi;
    private EditText quantity;
    private EditText description;
    private EditText apkPrice;
    private DatabaseHandler handler;
    private static final String TAG ="ListActivity" ;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> itemList;
    private DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler=new DatabaseHandler(this);

        setContentView(R.layout.activity_list);

recyclerView=findViewById(R.id.Recyclerview);
        databaseHandler=new DatabaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList=new ArrayList<>();
        itemList=databaseHandler.getAllItems();

        for (Item item :itemList){
            Log.d(TAG, "On create metoda: "+ item);
        }

        recyclerViewAdapter= new RecyclerViewAdapter(this,itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();


        FloatingActionButton fab = findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreatePopUpMenu();



            }
        });





    }

    private void CreatePopUpMenu() {
        builder= new AlertDialog.Builder(this);
        View view= getLayoutInflater().inflate(R.layout.popup,null);
        name=view.findViewById(R.id.entername);
        quantity=view.findViewById(R.id.enterquiantity);
        description=view.findViewById(R.id.enterdescription);
        apkPrice=view.findViewById(R.id.enterprice);
        builder.setView(view);
        dialog=builder.create();
        snimi=view.findViewById(R.id.snimi);
        dialog.show();

        snimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()&&description.getText().toString().isEmpty()&&apkPrice.getText().toString().isEmpty()&&quantity.getText().toString().isEmpty()){
                    Snackbar.make(view,"Popunite sva polja",Snackbar.LENGTH_SHORT).show();

                }else{
                    saveItem(view);




                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    public void saveItem(View view){
        Item item=new Item();

        String itemOpis= description.getText().toString().trim();
        String itemIme=name.getText().toString().trim();
        int itemCena= Integer.parseInt(apkPrice.getText().toString().trim());
        int itemKolicina=Integer.parseInt(quantity.getText().toString().trim());

        item.setName(itemIme);
        item.setDescription(itemOpis);
        item.setAppPrice(itemCena);
        item.setQuantity(itemKolicina);

        handler.addItem(item);

        Snackbar.make(view,"Uspesno dodato!",Snackbar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                startActivity(new Intent(ListActivity.this,ListActivity.class));
                finish();

            }
        },1500);


    }
}