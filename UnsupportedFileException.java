public final class UnsupportedFileException extends Exception {
  String file;

  public UnsupportedFileException(String filepath) {
    file = filepath;
  }

  @Override
  public String toString() {
    return FileTypeParser.getType(file) + " is unsupported.";
  }
}
