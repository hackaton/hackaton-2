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

    public List<Torrent> getTorrents() throws Exception {
        Map<String, Object> arguments = new HashMap<String, Object>();
        arguments.put("fields", asList("id", "name"));

        JSONObject responseArguments = request(arguments);

        JSONArray torrentsObject = responseArguments.getJSONArray("torrents");
        List<Torrent> torrents = new ArrayList<Torrent>();

        int length = torrentsObject.length();
        for(int i =0; i < length; i++) {
            JSONObject torrentObject = torrentsObject.getJSONObject(i);
            torrents.add(new Torrent(torrentObject.getString("id"), torrentObject.getString("name")));
        }

        return torrents;
    }

    public JSONObject request(Map<String, Object> arguments) throws Exception {
        Map<String, Object> request = new HashMap<String, Object>();

        request.put("method", "torrent-get");
        request.put("arguments", new JSONObject(arguments));


        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(new JSONObject(request).toString()));

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        String responseBody = httpclient.execute(httpPost, responseHandler);

        JSONObject response = new JSONObject(responseBody);

        String resultS = response.getString("result");
        if(!resultS.equals("success")) {
            throw new RuntimeException("Request failed: " + resultS);
        }

        return response.getJSONObject("arguments");
    }
}
