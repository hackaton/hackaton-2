package hackaton

import java.io.File

class FileScanner(basedir: String) {
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
}
