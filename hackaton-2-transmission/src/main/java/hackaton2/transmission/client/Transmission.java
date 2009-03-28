package hackaton2.transmission.client;

import java.util.List;

/**
 * @author <a href="mailto:trygve.laugstol@arktekk.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public interface Transmission {
    List<Torrent> getTorrents() throws Exception;

    /**
     * @param filename filename or URL of the .torrent file
     * @param paused if true, don't start the torrent
     * @throws Exception
     */
    Torrent addTorrent(String filename, boolean paused) throws Exception;

    void remove(int id, boolean deleteLocalData) throws Exception;
}
