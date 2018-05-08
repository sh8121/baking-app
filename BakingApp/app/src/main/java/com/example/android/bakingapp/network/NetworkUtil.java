package com.example.android.bakingapp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by lgpc on 2018-02-24.
 */

public class NetworkUtil {
    private static final int SUCCESS_CODE = 200;

    public static String getJsonResponse(URL url) throws IOException{

        if(url == null)
            return null;

        String jsonResponse = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try{
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(1000);
            httpURLConnection.setConnectTimeout(1000);
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode() == SUCCESS_CODE){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        }
        catch (IOException ex){

        }
        finally {
            if(httpURLConnection != null){
                httpURLConnection.disconnect();
                httpURLConnection = null;
            }

            if(inputStream != null){
                inputStream.close();
                inputStream = null;
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        if(inputStream == null){
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        while(line != null){
            stringBuilder.append(line);
            line = bufferedReader.readLine();
        }

        return stringBuilder.toString();
    }
}
