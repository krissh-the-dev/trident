public final class UnsupportedFileException extends Exception {
  String file;

  public UnsupportedFileException(String filepath) {
    file = filepath;
  }

  @Override
  public String toString() {
    return "The file type of " + file + " is unsupported.";
  }
}
