package org.example;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        ApiDatabaseDriver x = new ApiDatabaseDriver();


//      getting array of data
        Type listType = new TypeToken<ArrayList<Report>>() {
        }.getType();
        ArrayList<Report> reports = x.getArrayData("/reports", listType);
        System.out.println(reports.get(1).getOpis());

//      getting one data set
        ArrayList<Report> report = x.getArrayData("/getReport/5", listType);
        System.out.println(report.get(0).getOpis());

        //Second example with int variable
        Type listType2 = new TypeToken<ArrayList<Sala>>() {}.getType();
        ArrayList<Sala> sale = x.getArrayData("/sale", listType2);
        System.out.println(sale.get(0).getNazwa() + " "+ sale.get(0).getWielkosc());
        System.out.println(sale.get(1).getNazwa()+" "+ sale.get(1).getWielkosc() );

        //getting one data sala
        ArrayList<Sala> sala = x.getArrayData("/getSala/2", listType2);
        System.out.println(sala.get(0).getWielkosc());

        //post data
        Map<String, Object> data = new HashMap<>();
        data.put("content", "Report content123");
        data.put("idWorker", "123");
        data.put("username", "abba");

        try {
             x.sendPostRequest( "/createReport", data);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        //post data 2 example
        Map<String, Object> data2 = new HashMap<>();
        data2.put("name", "A5");
        data2.put("size", "123");

        try {
            x.sendPostRequest( "/createSala", data2);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


        // sending image
        x.sendBlob("m51.jpg","/createImage","image");

        //send pdf
        x.sendBlob("pdf24.pdf","/createPdf","pdfData");

        //getting image from db
        x.getLongBlobFromDB("/getImage/12","test11.jpg", "image");
        //getting pdf from db
        x.getLongBlobFromDB("/getPdfById/7","test2.pdf", "pdf");
    }
}