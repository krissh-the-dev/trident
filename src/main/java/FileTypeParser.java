import java.io.File;

/*
 *  FileTypeParser.java
 *  (c) Copyright, 2020 - 2021 Krishna Moorthy
 *  akrishnamoorthy007@gmail.com | github.com/KrishnaMoorthy12
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * (Apache v2) Trident > FileTypeParser
 * @author: Krishna Moorthy
 */

public class FileTypeParser {

  public static String getExtension(String fileName) {
    /*
     * finds the extension of a file
     * 
     * @param: filename or path as string
     * 
     * @returns: the extension of the file, without '.'
     */
    String extension = "file";
    int i = fileName.lastIndexOf('.');
    if (i > 0)
      extension = fileName.substring(i + 1);
    return extension;
  }

  public static String getType(String fileName) {
    /*
     * Finds the type of the file based on extension
     * 
     * @param: file name or file path as string
     * 
     * @returns: the file type as string
     */
    String type;

    String extension = getExtension(fileName);

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

      case "scss":
        type = "SASS File";
        break;

      case "sass":
        type = "SASS File";
        break;

      case "less":
        type = "LESS Style Sheet";
        break;

      case "js":
        type = "Java Script File";
        break;

      case "ts":
        type = "TypeScript File";
        break;

      case "jar":
        type = "Java Archive";
        break;

      case "json":
        type = "Java Script Object";
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

      case "tcf":
        type = "Trident Configuration File";
        break;

      case "file":
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

  public static String getName(String filePath) {
    String fileName = "";

    try {
      String name = (new File(filePath)).getName();
      fileName = name.replaceFirst("[.][^.] + $", "");
    } catch (Exception e) {
      e.printStackTrace();
      fileName = "";
    }
    return fileName;
  }
}
