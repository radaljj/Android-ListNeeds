package com.example.listneeds;

import android.app.AlertDialog;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
private AlertDialog.Builder builder;
private AlertDialog dialog;
private Context context;
private List<Item> lista;
private LayoutInflater layoutInflater;

    public RecyclerViewAdapter(Context context, List<Item> lista) {
        this.context = context;
        this.lista = lista;
    }
    public RecyclerViewAdapter(){}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,false);





        return new ViewHolder(view,context);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {

        Item item = lista.get(position); // object Item

        viewHolder.ime.setText("Ime proizvoda: "+ item.getName());
        viewHolder.opis.setText("Opis proizvoda: " + item.getDescription());
        viewHolder.kolicinarow.setText("Kolicina potrebna: "+ String.valueOf(item.getQuantity()));
        viewHolder.cenarow.setText("Okvirna cena: "+ String.valueOf(item.getAppPrice()));
        viewHolder.datum.setText(  item.getDate());


    }


    @Override
    public int getItemCount() {
     return   lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView  opis;
        public TextView  cenarow;
        public TextView  kolicinarow;
        public TextView  datum;
        private int id;
        public TextView  ime;
        public ImageButton edit;
        public ImageButton delete;

        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context=ctx;

            opis = itemView.findViewById(R.id.row_opis);
            ime=itemView.findViewById(R.id.row_ime);
            cenarow=itemView.findViewById(R.id.row_cena);
            kolicinarow=itemView.findViewById(R.id.row_kolicina);
            datum=itemView.findViewById(R.id.row_datum);
            edit=itemView.findViewById(R.id.editButton);
            delete=itemView.findViewById(R.id.deleteButton);

            edit.setOnClickListener(this);
            delete.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {

           int position=getAdapterPosition();
            Item item=lista.get(position);


            switch (v.getId()){
                case R.id.editButton:
                    updateItem(item);
                    break;
                case R.id.deleteButton:
                    deleteItem(item.getId());
                    break;
            }
        }

        private void updateItem(Item item) {

            builder= new AlertDialog.Builder( context);
            layoutInflater=LayoutInflater.from(context);
            View view=layoutInflater.inflate(R.layout.popup,null);

            EditText name;
             Button snimi;
             EditText quantity;
             EditText description;
             EditText apkPrice;
             TextView title=view.findViewById(R.id.txt1);

            snimi=view.findViewById(R.id.snimi);
            snimi.setText("Azuriraj");
            name=view.findViewById(R.id.entername);
            quantity=view.findViewById(R.id.enterquiantity);
            description=view.findViewById(R.id.enterdescription);
            apkPrice=view.findViewById(R.id.enterprice);
            title.setText("Azurirajte");

            name.setText(item.getName());
            quantity.setText(String.valueOf(item.getQuantity()));
            description.setText(item.getDescription());
            apkPrice.setText(String.valueOf(item.getAppPrice()));
            builder.setView(view);
            dialog=builder.create();
            dialog.show();

            snimi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


DatabaseHandler databaseHandler= new DatabaseHandler(context);
item.setName(name.getText().toString());
item.setQuantity(Integer.parseInt(quantity.getText().toString()));
item.setDescription(description.getText().toString());
item.setAppPrice(Integer.parseInt(apkPrice.getText().toString()));
databaseHandler.updateItem(item);

notifyItemChanged(getAdapterPosition(),item);
dialog.dismiss();



                }
            });



        }

        private void deleteItem(int id) {

           builder= new AlertDialog.Builder( context);
           layoutInflater=LayoutInflater.from(context);
           View view=layoutInflater.inflate(R.layout.are_you_sure,null);

            Button ne;
            ne=view.findViewById(R.id.ne);
            Button da=view.findViewById(R.id.da);
            builder.setView(view);
            dialog=builder.create();
            dialog.show();

            ne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            da.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseHandler db= new DatabaseHandler(context);
                    db.deleteItem(id);
                    lista.remove(getAdapterPosition());
                    notifyItemChanged(getAdapterPosition());
                    dialog.dismiss();
                }
            });








        }




    }
}
