package com.example.ispit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DodajRacun extends AppCompatActivity implements View.OnClickListener {
//    public TextView PIB;
//    public TextView adresa;
//    public TextView grad;
//    public TextView iznos;
//    public DatePicker datum;
//    public TextView PFR_broj;

    private void mojaPoruka( String mojaPoruka){
        Toast toast = Toast.makeText(getApplicationContext(),mojaPoruka , Toast.LENGTH_SHORT);
        toast.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_racun);

        //https://www.youtube.com/watch?v=2oHA6jBbCnw

        initComponents();
    }

    private void initComponents() {


        Button sacuvajRacun = findViewById(R.id.buttonSacuvajRacun);
        sacuvajRacun.setOnClickListener(this);

        //Racun racun = new Racun(PIB, adresa,  grad,  iznos, datum, PFR_broj);
    }

    @Override
    public void onClick(View v) {
        String naziv = ((TextView)findViewById(R.id.inputNazivProdavca)).getText().toString();
        String PIB = ((TextView)findViewById(R.id.inputPIB)).getText().toString();
        String adresa = ((TextView)findViewById(R.id.inputAdresa)).getText().toString();
        String grad = ((TextView)findViewById(R.id.inputGrad)).getText().toString();
        float iznos = Float.parseFloat(((TextView)findViewById(R.id.inputIznos)).getText().toString());
        DatePicker inputDatum = findViewById(R.id.inputDatePicker);
       // String datum = String.format("%4d. %2d. %2d", inputDatum.getYear(), inputDatum.getMonth() + 1, inputDatum.getDayOfMonth());
        int y = inputDatum.getYear() - 1900;
        int m = inputDatum.getMonth();
        int d = inputDatum.getDayOfMonth();

        Date date = new Date(y,m,d);
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd");


        String datum = sdf.format(date);


        String PFR_broj = ((TextView)findViewById(R.id.inputPFR)).getText().toString();

        TextView test = findViewById(R.id.test);
        int red = Color.parseColor("#00FF00");
        test.setBackgroundColor(red);

            JSONObject postData = new JSONObject();
            try {
                postData.put("naziv" , naziv);
                postData.put("pib" , PIB);
                postData.put("adresa" , adresa);
                postData.put("grad" , grad);
                postData.put("iznos" , iznos);
                postData.put("datum" , datum);
                postData.put("pfr" , PFR_broj);
                API api = new API();
                api.execute("http://192.168.0.16/ispit/php_skripta.php", postData.toString());
                //postData.toString()

            }catch (Exception e){

            }

    }
}