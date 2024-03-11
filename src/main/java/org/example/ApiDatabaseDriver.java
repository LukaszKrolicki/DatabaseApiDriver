package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ApiDatabaseDriver {
    ////////////////////////////////////////////////////////////////
    //Connecting with db with api

    HttpClient httpClient = HttpClient.newHttpClient();

    static String baseUrl = "http://localhost:8080";


    //Getting Array of data or Single data object with different data types
    //Function that retries array of one or more objects
    ////////////////////////////////////////////////////////////////
    public <type> ArrayList<type> getArrayData(String specUrl, Type listType) throws IOException, InterruptedException {

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + specUrl))
                .build();

        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

        String jsonString = getResponse.body();

        Gson gson = new Gson();

        ArrayList<type> list = gson.fromJson(jsonString, listType);

        return list;
    }

    //sending 'normal' data to database
    public void sendPostRequest(String endpoint, Map<String, Object> data) throws IOException, InterruptedException {
        // Construct the full URL
        String url = baseUrl + endpoint;

        // Convert data map to JSON string
        Gson gson = new Gson();

        String jsonBody = gson.toJson(data);

        // Create the POST request
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        // Send the POST request and handle the response
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        postResponse.body();
    }


    //Getting image/pds (LongBlob) from database
    public void getLongBlobFromDB(String endPoint, String pathName, String memberName){
        try {
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + endPoint))
                    .build();// Parse JSON
            HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
            String jsonString = response.body();
            System.out.println(jsonString);

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
            byte[] data = gson.fromJson(jsonObject.getAsJsonObject(memberName).get("data"), byte[].class);

                data = Base64.getDecoder().decode(data);


            Path filePath = Path.of(pathName);
            Files.write(filePath, data);
            System.out.println("Blob downloaded successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendBlob(String filePath, String endpoint, String dataColumn) throws IOException, InterruptedException {
        Path path = Paths.get(filePath); //pdf23.pdf
        byte[] content = Files.readAllBytes(path);
        String base64Content = Base64.getEncoder().encodeToString(content);
        Map<String, Object> data = new HashMap<>();
        data.put(dataColumn, base64Content);
        sendPostRequest( endpoint, data);
    }


}
