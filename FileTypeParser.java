
/*
 *  FileTypeParser.java
 *  (c) Copyright, 2020 - 2021 Krishna Moorthy
 *  akrishnamoorthy007@gmail.com | github.io/KrishnaMoorthy12
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/*
 * (GPL v3) Trident > FileTypeParser
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
}
