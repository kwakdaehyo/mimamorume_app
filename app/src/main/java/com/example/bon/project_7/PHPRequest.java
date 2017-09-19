package com.example.bon.project_7;

/**
 * Created by bon on 2017-04-20.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class PHPRequest {
    private URL url;

    public PHPRequest(String url) throws MalformedURLException { this.url = new URL(url); }

    private String readStream(InputStream in) throws IOException {
        StringBuilder jsonHtml = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = null;

        while((line = reader.readLine()) != null)
            jsonHtml.append(line);

        reader.close();
        return jsonHtml.toString();
    }

    public String PhPtest1(final Double data1, final Double data2, final String data3) {
        try {
            String postData = "lat=" + data1  + "lng=" + data2 + "phone" + data3;
//            Log.e("asdasd", postData.toString());
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            Log.i("PHPRequest", "1");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            Log.i("PHPRequest", "2");
            conn.setRequestMethod("POST");
            Log.i("PHPRequest", "3");
//            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            Log.i("PHPRequest", "4");
            conn.setDoOutput(true);
            Log.i("PHPRequest", "5");
            conn.setDoInput(true);
            Log.i("PHPRequest", "6");
            OutputStream outputStream = conn.getOutputStream();
            Log.i("PHPRequest", "7");
            outputStream.write(postData.getBytes("UTF-8"));
            Log.i("PHPRequest", "8");
            outputStream.flush();
            Log.i("PHPRequest", "9");
            outputStream.close();
            String result = readStream(conn.getInputStream());
            Log.e("sadads",postData.toString());
            conn.disconnect();
            Log.e("zzzz",result.toString());
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.i("error: ", e.getMessage());
            return null;
        }
    }

//    public String PhPtest2(final String ) {
//        try {
//            String postData = "Data1=" + data1;
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setRequestMethod("POST");
//            conn.setConnectTimeout(5000);
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            OutputStream outputStream = conn.getOutputStream();
//            outputStream.write(postData.getBytes("UTF-8"));
//            outputStream.flush();
//            outputStream.close();
//            String result = readStream(conn.getInputStream());
//            conn.disconnect();
//            return result;
//        }
//        catch (Exception e) {
//            Log.i("PHPRequest", "request was failed.");
//            return null;
//        }
//    }
}
