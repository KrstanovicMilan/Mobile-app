package com.example.ispit;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class Racun {
    public String naziv;
    public String PIB;
    public String adresa;
    public String grad;
    public double iznos;
    public String datum;
    public String PFR_broj;

    public Racun(){};

    public Racun(String naziv, String PIB ,String adresa, String grad, double iznos, String datum, String PFR_broj){
        this.naziv = naziv;
        this.PIB = PIB;
        this.adresa = adresa;
        this.grad = grad;
        this.iznos = iznos;
        this.datum = datum;
        this.PFR_broj = PFR_broj;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getPIB() {
        return PIB;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getGrad() {
        return grad;
    }

    public double getIznos() {
        return iznos;
    }

    public String getDatum() {
        return datum;
    }

    public String getPFR_broj() {
        return PFR_broj;
    }

    public void setPIB(String PIB) {
        this.PIB = PIB;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public void setPFR_broj(String PFR_broj) {
        this.PFR_broj = PFR_broj;
    }



    public static Racun parseJsonObject(JSONObject object){
        Racun racun = new Racun();
        try {
            if(object.has("naziv_prodavca")){
                racun.setNaziv(object.getString("naziv_prodavca"));
            }
            if(object.has("pib")){
                racun.setPIB(object.getString("pib"));
            }
            if(object.has("adresa")){
                racun.setAdresa(object.getString("adresa"));
            }
            if(object.has("grad")){
                racun.setGrad(object.getString("grad"));
            }
            if(object.has("iznos")){
                racun.setIznos(object.getDouble("iznos"));
            }
            if(object.has("datum")){
                racun.setDatum(object.getString("datum"));
            }
            if(object.has("pfr")){
                racun.setPFR_broj(object.getString("pfr"));
            }
        }catch (Exception e){

        }
        return racun;
    }



    public static LinkedList<Racun> parseJsonArray (JSONArray array){
        LinkedList<Racun> list = new LinkedList<>();
        try{
            for (int i= 0; i < array.length(); i++){
                Racun racun = parseJsonObject(array.getJSONObject(i));
                list.add(racun);
            }
        }catch (Exception e){

        }
        return list;
    }
}
