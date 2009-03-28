package hackaton2.transmission.client;

import org.json.JSONObject;
import org.json.JSONException;

/**
 * @author <a href="mailto:trygve.laugstol@arktekk.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public class Torrent {

    public final int id;
    public final String name;
    public final String hashString;

    public static Torrent fromJsonObject(JSONObject torrentObject) throws JSONException {
        return new Torrent(torrentObject.getInt("id"),
            torrentObject.getString("name"),
            torrentObject.getString("hashString"));
    }

    private Torrent(int id, String name, String hashString) {
        this.id = id;
        this.name = name;
        this.hashString = hashString;
    }

    @Override
    public String toString() {
        return "Torrent: id=" + id + ", name=" + name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHashString() {
        return hashString;
    }
}
