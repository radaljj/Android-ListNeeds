package com.example.listneeds;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private EditText name;
    private Button snimi;
    private EditText quantity;
    private EditText description;
    private EditText apkPrice;
    private DatabaseHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler=new DatabaseHandler(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(handler.getCount()>0){
            startActivity(new Intent(MainActivity.this,ListActivity.class));
            finish();

        }

        FloatingActionButton fab = findViewById(R.id.fab);
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
        startActivity(new Intent(MainActivity.this,ListActivity.class));
    }
},1500);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}