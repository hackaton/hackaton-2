package hackaton

import java.io.File
import hackaton2.api.domain._

/**
 * Assumes filenames of the form '<Band name> - <Album name> - <Track No.> - <Song title>.flac' 
 */
class FileScanner(basedir: String) {

//  private def filesIn(dir: String) = new File(dir).listFiles

  private def isAlbum(dir: File) =
    dir.listFiles.filter(f => f.getName.endsWith(".flac")).size > 0

  private def songsIn(dir: File) =
    dir.listFiles.filter(f => f.getName.endsWith(".flac")).map(Song.apply).toList

  private def albumFrom(dir: File) = {
    val tracks = songsIn(dir)
    val firstTrack = tracks(0)
    Album(firstTrack.artist, firstTrack.album, tracks, dir)
  }

  private def allAlbums(f: File, al: List[Album]): List[Album] = (al /: f.listFiles) {
    (a, b) =>
            if (b.isDirectory && isAlbum(b))
              albumFrom(b) :: a
            else if (b.isDirectory)
              allAlbums(b, a)
            else
              a
  }

  def albums =
    allAlbums(new File(basedir), List[Album]())

  /*
  private def filesIn(dir: String) = new File(dir).listFiles

  def allFiles(f: File, files: List[File]): List[File] = (files /: f.listFiles)(
    (a, b) => if (b.isDirectory) allFiles(b, a) else b :: a)

  def files =
    allFiles(new File(basedir), Nil)

  def songs =
    files.filter(f => f.getName.endsWith(".flac")).map(Song.apply)

  def albums = (Map[(String, String), Album]() /: songs) {
    (a, song) =>
            val key = (song.artist, song.album)
            val optAlbum = a.get(key).map(_ + song)
            a(key) = optAlbum.getOrElse(Album(song.artist, song.album, List(song)))
  }.values.toList
  */
}
