package hackaton2.transmission.client;

import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

/**
 * @author <a href="mailto:trygve.laugstol@arktekk.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
public class ClientTest {
    private static final File LouisArmstrongFile = new File(System.getProperty("basedir"), "src/test/resources/Louis_Armstrong_-_Louis_Armstrong____s_All_Time_Greatest_Hits.4484009.TPB.torrent");
    private static final String LouisArmstrongName = "Louis Armstrong - Louis Armstrong's All Time Greatest Hits";

    @Test
    @Ignore
    public void testBasic() throws Exception {
        Transmission transmission = new DefaultTransmission("http://127.0.0.1:9091/transmission/rpc");

        List<Torrent> torrents = transmission.getTorrents();
        System.out.println("Got " + torrents.size() + " torrents.");
        for (Torrent torrent : torrents) {
            System.out.println(torrent);
        }
    }

    @Test
    @Ignore
    public void testAddRemove() throws Exception {
        Transmission transmission = new DefaultTransmission("http://127.0.0.1:9091/transmission/rpc");

        assertTrue(LouisArmstrongFile.canRead());

        for (Torrent torrent : transmission.getTorrents()) {
            System.out.println("torrent.name = " + torrent.name);
            if(torrent.name.equals(LouisArmstrongName)){
                System.out.println("Removing torrent.id = " + torrent.id);
                transmission.remove(torrent.id, true);
            }
        }

        Torrent torrent = transmission.addTorrent(LouisArmstrongFile.getAbsolutePath(), false);
        System.out.println(torrent);
    }
}
