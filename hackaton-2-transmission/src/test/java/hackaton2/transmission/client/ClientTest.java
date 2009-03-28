package hackaton2.transmission.client;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * @author <a href="mailto:trygve.laugstol@arktekk.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public class ClientTest {

    @Test
    @Ignore
    public void testBasic() throws Exception {
        Transmission transmission = new DefaultTransmission("http://127.0.0.1:9091/transmission/rpc");

        List<Torrent> torrents = transmission.getTorrents();
        System.out.println("Got " + torrents.size() + " torrents.");
        for (Torrent torrent : torrents) {
            System.out.println("torrent.name = " + torrent.name);
        }
    }
}
