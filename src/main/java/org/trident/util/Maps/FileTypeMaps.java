package Maps;

import java.util.HashMap;
import java.util.Map;

public class FileTypeMaps {

  /**
   * - package wrong
   * - have a sens this class but before we neet to build de structure of the text editor
   * I don't see the code this class, we should be work in the strucure because at this problem will think after refactoring of class
   */
  private static Map<String, String> sourceFileType = new HashMap<>();
  private static Map<String, String> sourceFileLang = new HashMap<>();

  private static void initFileTypes() {
    sourceFileType.put("java", "Java Source File");
    sourceFileType.put("js", "Java Script File");
    sourceFileType.put("ts", "TypeScript File");
    sourceFileType.put("py", "Python Source File");
    sourceFileType.put("kt", "Kotlin Source File");
    sourceFileType.put("kts", "Kotlin Source File");
    sourceFileType.put("c", "C Source File");
    sourceFileType.put("cpp", "C++ Source File");
    sourceFileType.put("h", "C/ C++ Header File");
    sourceFileType.put("class", "Java Class File");
    sourceFileType.put("r", "R Source File");
    sourceFileType.put("md", "Markdown File");
    sourceFileType.put("mf", "Manifest File");
    sourceFileType.put("html", "HTML File");
    sourceFileType.put("css", "Cascading Style Sheet");
    sourceFileType.put("scss", "SASS File");
    sourceFileType.put("sass", "SASS File");
    sourceFileType.put("less", "LESS Style Sheet");
    sourceFileType.put("json", "Java Script Object");
    sourceFileType.put("bat", "Windows Batch Script");
    sourceFileType.put("sh", "Linux Bash Script");
    sourceFileType.put("tcf", "Trident Configuration File");
    sourceFileType.put("file", "Plain File");
  }

  private static void initLangNames() {
    sourceFileLang.put("java", "Java");
    sourceFileLang.put("js", "Java Script");
    sourceFileLang.put("ts", "TypeScript");
    sourceFileLang.put("py", "Python");
    sourceFileLang.put("kt", "Kotlin");
    sourceFileLang.put("kts", "Kotlin");
    sourceFileLang.put("c", "C");
    sourceFileLang.put("cpp", "C++");
    sourceFileLang.put("h", "C/ C++ Header");
    sourceFileLang.put("class", "Java Class");
    sourceFileLang.put("r", "R");
    sourceFileLang.put("md", "Markdown");
    sourceFileLang.put("mf", "Manifest");
    sourceFileLang.put("html", "HTML");
    sourceFileLang.put("css", "Cascading Style Sheet");
    sourceFileLang.put("scss", "SASS");
    sourceFileLang.put("sass", "SASS");
    sourceFileLang.put("less", "LESS");
    sourceFileLang.put("json", "Java Script Object");
    sourceFileLang.put("bat", "Batch Script");
    sourceFileLang.put("sh", "Linux Bash Script");
    sourceFileLang.put("tcf", "Trident Configuration");
    sourceFileLang.put("file", "File");
  }

  private FileTypeMaps() {
    initFileTypes();
    initLangNames();
  }

  private static FileTypeMaps SINGLETON;
  private static final Class TAG = FileTypeMaps.class;

  public static FileTypeMaps getInstance() {
    if (SINGLETON == null) {
      SINGLETON = new FileTypeMaps();
    }
    return SINGLETON;
  }

  /**
   * In the SINGLETON only method of getInstance is static, the following method
   * should be NO static.
   * @param extension
   * @return
   */

  public static Map getFileDetailsMap(String extension) {
    Map<String, String> details = new HashMap<>();
    details.put("extension", extension);
    details.put("language", sourceFileLang.get(extension));
    details.put("type", sourceFileType.get(extension));
    return details;
  }

  public static String[] getFileDetailsArray(String extension) {
    String details[] = { extension, sourceFileLang.get(extension), sourceFileType.get(extension) };
    return details;
  }
}
