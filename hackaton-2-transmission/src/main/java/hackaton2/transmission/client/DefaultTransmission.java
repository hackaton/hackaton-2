package hackaton2.transmission.client;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:trygve.laugstol@arktekk.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public class DefaultTransmission implements Transmission {
    private final String url;

    public DefaultTransmission(String url) {
        this.url = url;
    }

    // -----------------------------------------------------------------------
    // Transmission Implementation
    // -----------------------------------------------------------------------

    public List<Torrent> getTorrents() throws Exception {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("fields", asList("id", "name", "hashString"));

        JSONObject responseArguments = request("torrent-get", arguments);

        JSONArray torrentsObject = responseArguments.getJSONArray("torrents");
        List<Torrent> torrents = new ArrayList<Torrent>();

        int length = torrentsObject.length();
        for (int i = 0; i < length; i++) {
            torrents.add(Torrent.fromJsonObject(torrentsObject.getJSONObject(i)));
        }

        return torrents;
    }

    public Torrent addTorrent(String filename, boolean paused) throws Exception {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("filename", filename);
        arguments.put("paused", paused);

        JSONObject responseArguments = request("torrent-add", arguments);

        JSONObject torrentObject = responseArguments.getJSONObject("torrent-added");

        return Torrent.fromJsonObject(torrentObject);
    }

    public void remove(int id, boolean deleteLocalData) throws Exception {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("ids", asList(id));
        arguments.put("delete-local-data", deleteLocalData);

        request("torrent-remove", arguments);
    }

    // -----------------------------------------------------------------------
    //
    // -----------------------------------------------------------------------

    private JSONObject request(String method, Map<String, Object> arguments) throws Exception {
        Map<String, Object> request = new HashMap<String, Object>();

        request.put("method", method);
        request.put("arguments", new JSONObject(arguments));


        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost(url);
        String requestJson = new JSONObject(request).toString();
        System.out.println("-------------------------------------");
        System.out.println("Request:");
        System.out.println("-------------------------------------");
        System.out.println(requestJson);
        System.out.println("-------------------------------------");
        httpPost.setEntity(new StringEntity(requestJson));

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        String responseJson = httpclient.execute(httpPost, responseHandler);
        System.out.println("-------------------------------------");
        System.out.println("Response:");
        System.out.println("-------------------------------------");
        System.out.println(responseJson);
        System.out.println("-------------------------------------");
        JSONObject response = new JSONObject(responseJson);

        String resultS = response.getString("result");
        if (!resultS.equals("success")) {
            throw new RuntimeException("Request failed: " + resultS);
        }

        return response.getJSONObject("arguments");
    }
}
