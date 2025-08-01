package com.example.ispit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class Filtriraj extends AppCompatActivity implements View.OnClickListener {
    TextView datumOd;
    TextView datumDo;
    Button calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtriraj);
        initComponents();
    }

    private void initComponents() {
        datumOd = findViewById(R.id.textIzaberiDatum);
        datumDo = findViewById(R.id.textIzaberiDatum2);

        calendar = findViewById(R.id.buttonOtvoriKalendar);

        MaterialDatePicker<Pair<Long, Long>> materialDatePicker  = MaterialDatePicker.Builder.dateRangePicker().
                setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds())).build();

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "Tag_picker");
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        Long startDate = selection.first;
                        Long endDate = selection.second;
                        String startDateString = DateFormat.format("yyyy-MM-dd", new Date(startDate)).toString();
                        String endDateString = DateFormat.format("yyyy-MM-dd", new Date(endDate)).toString();
                        datumOd.setText(startDateString);
                        datumDo.setText(endDateString);
                    }

                    //@Override
                    //public  void onPositiveButtonClick(<Pair<Long,Long>> selection) {
                       // dateRangeText.setText(materialDatePicker.getHeaderText());


                });
            }
        });

        Button filtriraj = findViewById(R.id.buttonFiltriraj);
        filtriraj.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonFiltriraj){
            String iznosOd = ((TextView)findViewById(R.id.inputOd)).getText().toString();
            String iznosDo = ((TextView)findViewById(R.id.inputDo)).getText().toString();
            String datumOd = ((TextView)findViewById(R.id.textIzaberiDatum)).getText().toString();
            String datumDo = ((TextView)findViewById(R.id.textIzaberiDatum2)).getText().toString();

            JSONObject filtar = new JSONObject();
            try {
                filtar.put("iznosOd" , iznosOd);
                filtar.put("iznosDo", iznosDo);
                filtar.put("datumOd",datumOd);
                filtar.put("datumDo", datumDo);
                API api = new API();
                api.execute("http://192.168.0.16/ispit/php_uslov.php", filtar.toString());
            }catch (Exception e){

            }

            API_GET.getJSON("http://192.168.0.16/ispit/newVar.txt" , new ReadDataHandler()
            {
                @Override
                public void handleMessage(Message msg){
                    String reply = getJson(); // u reply je smesten ceo json kod
                    @SuppressLint("WrongViewCast") LinearLayout MainScrollview = findViewById(R.id.scrollViewFiltriraj);
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    try {
                        MainScrollview.removeAllViews();
                        JSONArray array = new JSONArray(reply);
                        LinkedList<Racun> Racuni = Racun.parseJsonArray(array);


                        for(Racun r : Racuni ){
                            RelativeLayout item = (RelativeLayout) inflater.inflate(R.layout.single_item_racun , null);
                            ((TextView)item.findViewById(R.id.textViewNaziv)).setText(r.getNaziv());
                            ((TextView)item.findViewById(R.id.textViewDatum)).setText(r.getDatum());
                            ((TextView)item.findViewById(R.id.textViewIznos)).setText(String.format(Locale.US , "%.2f" ,r.getIznos()));
                            ImageView image = (ImageView)item.findViewById(R.id.imageIcon);
                            image.setImageResource(R.drawable.bill);

                            MainScrollview.addView(item);


                        }
                    }catch (Exception e){

                    }
                }
            });
        }
    }
}