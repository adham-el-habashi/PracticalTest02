package ro.pub.cs.systems.eim.practicaltest02.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;
import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.general.Utilities;
import ro.pub.cs.systems.eim.practicaltest02.model.WeatherForecastInformation;

public class CommunicationThread extends Thread {

    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket == null) {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
            return;
        }
        try {
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            Log.i(Constants.TAG, "[COMMUNICATION THREAD] URL CLIENT");
            String url = bufferedReader.readLine();

            HashMap<String, WeatherForecastInformation> data = serverThread.getData();
            WeatherForecastInformation weatherForecastInformation = null;

            if(url.contains(Constants.block)) {
                Log.i(Constants.TAG, "[COMMUNICATION THREAD] URL BLOCKED");
            } else {
                Log.i(Constants.TAG, "[COMMUNICATION THREAD] URL OK. MAKING REQUEST");
                Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the webservice...");
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpGet httpGet = new HttpGet(url);
//
//                Log.e(Constants.TAG, "URL is " + url);

               // ResponseHandler<String> responseHandler = new BasicResponseHandler();
           //     String pageSourceCode = httpClient.execute(httpGet, responseHandler);
//                if (pageSourceCode == null) {
//                    Log.e(Constants.TAG, "[COMMUNICATION THREAD] Error getting the page!");
//                    return;
//                }
//                HttpResponse httpGetResponse = httpClient.execute(httpGet);
//                HttpEntity httpGetEntity = httpGetResponse.getEntity();
//                if (httpGetEntity != null) {
//                    // do something with the response
//                    Log.i(Constants.TAG, EntityUtils.toString(httpGetEntity));
//                }
//
                weatherForecastInformation  = new WeatherForecastInformation("hdfsjserhgfhesidgjeo");

                serverThread.setData(url, weatherForecastInformation);
            }


            if (weatherForecastInformation == null) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Weather Forecast Information is null!");
                return;
            }

        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }

}