package com.example.ispit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Spinner sortiraj ;
    private Button filtriraj;
    private Button dodajRacun;
    private Button osvezi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        sortiraj = findViewById(R.id.sortiraj);
        ArrayList<String> List = new ArrayList<>();
        List.add("Datum : rastuci");
        List.add("Datum : opadajuci");
        List.add("Iznos : rastuci");
        List.add("Iznos : opadajuci");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item , List);

        sortiraj.setAdapter(adapter);

        filtriraj = findViewById(R.id.filtriraj);
        dodajRacun = findViewById(R.id.dodajRacun);
        osvezi = findViewById(R.id.buttonOsvezi);




        filtriraj.setOnClickListener(this);
        dodajRacun.setOnClickListener(this);
        osvezi.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.dodajRacun:
                Intent intent = new Intent(this, DodajRacun.class);
                startActivity(intent);
                break;
            case R.id.filtriraj:
                Intent intent1 = new Intent(this,Filtriraj.class);
                startActivity(intent1);
                break;
            case R.id.buttonOsvezi:
                API_GET.getJSON("http://192.168.0.16/ispit/php_get.php" , new ReadDataHandler()
                {
                    @Override
                    public void handleMessage(Message msg){
                        String reply = getJson(); // u reply je smesten ceo json kod
                        @SuppressLint("WrongViewCast") LinearLayout MainScrollView = findViewById(R.id.scrollViewListRacuni);
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        try {
                            MainScrollView.removeAllViews();
                            JSONArray array = new JSONArray(reply);
                            LinkedList<Racun> Racuni = Racun.parseJsonArray(array);


                            for(Racun r : Racuni ){
                                RelativeLayout item = (RelativeLayout) inflater.inflate(R.layout.single_item_racun , null);
                                ((TextView)item.findViewById(R.id.textViewNaziv)).setText(r.getNaziv());
                                ((TextView)item.findViewById(R.id.textViewDatum)).setText(r.getDatum());
                                ((TextView)item.findViewById(R.id.textViewIznos)).setText(String.format(Locale.US , "%.2f" ,r.getIznos()));
                                ImageView image = (ImageView)item.findViewById(R.id.imageIcon);
                                image.setImageResource(R.drawable.bill);

                                MainScrollView.addView(item);


                            }
                        }catch (Exception e){

                        }
                    }
                });


                break;
        }


    }
}