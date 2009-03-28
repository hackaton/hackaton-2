package hackaton2.transmission.client;

import java.util.List;

/**
 * @author <a href="mailto:trygve.laugstol@arktekk.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public interface Transmission {
    List<Torrent> getTorrents() throws Exception;
}
