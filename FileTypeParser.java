public class FileTypeParser {
  public static String parse(String fileName) {
    String type, extension = "";

    int i = fileName.lastIndexOf('.');
    if (i > 0) {
      extension = fileName.substring(i + 1);
    }

    switch (extension.toLowerCase()) {
    case "txt":
      type = "Text File";
      break;

    case "py":
      type = "Python Source File";
      break;

    case "c":
      type = "C Source File";
      break;

    case "cpp":
      type = "C++ Source File";
      break;

    case "h":
      type = "Header File";
      break;

    case "kt":
      type = "Kotlin Source File";
      break;

    case "r":
      type = "R Source File";
      break;

    case "java":
      type = "Java Source File";
      break;

    case "class":
      type = "Java Class File";
      break;

    case "md":
      type = "Markdown File";
      break;

    case "mf":
      type = "Manifest File";
      break;

    case "html":
      type = "HTML File";
      break;

    case "css":
      type = "Cascading Style Sheet";
      break;

    case "less":
      type = "LESS Style Sheet";
      break;

    case "js":
      type = "Java Script File";
      break;

    case "jar":
      type = "Java Archive";
      break;

    case "bat":
      type = "Windows Batch Script";
      break;

    case "sh":
      type = "Linux Bash Script";
      break;

    case "rtf":
      type = "Rich Text File";
      break;

    case "docx":
      type = "Word Document";
      break;

    case "xlsx":
      type = "Excel Spread Sheet";
      break;

    case "ppt":
      type = "PowerPoint File";
      break;

    case "odt":
      type = "Open Document File";
      break;

    case "pdf":
      type = "Portable Document";
      break;

    case "":
      type = "Plain File";
      break;

    case "jpeg":
    case "png":
    case "ico":
    case "tiff":
    case "bmp":
      type = extension.toUpperCase() + " Image File";
      break;

    default:
      type = extension.toUpperCase() + " File";
      break;
    }
    return type;
  }
}
