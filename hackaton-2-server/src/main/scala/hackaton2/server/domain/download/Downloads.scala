package hackaton2.server.domain.download

import actors.Actor
import hackaton2.transmission.client.{Torrent, Transmission}

/**
 * @author <a href="mailto:trygve.laugstol@arktekk.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
object Downloads extends Actor {
  DownloadUpdater ! UpdateList

  val cacheTime = 5000

  def act() = loop(State(Nil, System.currentTimeMillis))

  def loop(implicit state: State) {
    react {
      case UpdateDownloadList(downloads) => {
        loop(State(downloads, System.currentTimeMillis))
      }
      case CurrentDownloads => {
        if (state.timestamp > System.currentTimeMillis + cacheTime)
          DownloadUpdater ! UpdateDownloadList

        reply(state.downloads)
        loop(state)
      }
    }
  }
}

private object DownloadUpdater extends Actor {
  val transmission: Transmission = Hackaton2Server.transmission

  def act() = loop()

  def loop() {
    react {
      case UpdateList => {
        println("Fetching status from Transmission...")
        val downloads = transmission.getTorrents.toArray(Array[Torrent]()).
                map(t => Download(t.name, t.haveValid / t.sizeWhenDone))
        println("Got " + downloads.size + " downloads")
        Downloads ! downloads
        loop()
      }
    }
  }
}

private case class State(downloads: List[Download], timestamp: long)

case class Download(name: String, progress: double)

case object CurrentDownloads

private case object UpdateList

private case class UpdateDownloadList(downloads: List[Download])
