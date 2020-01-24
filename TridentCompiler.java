
/*
 *  TridentCompiler.java
 *  (c) Copyright, 2020 - 2021 Krishna Moorthy
 *  akrishnamoorthy007@gmail.com | github.com/KrishnaMoorthy12
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

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Desktop;
import java.io.File;

/*
 * Trident Compiler v2.2
 * (GPL v3) Trident > Compilers
 * Since: Trident v0.2
 * Stable since: Trident v2.0
 */

public class TridentCompiler {
  /*
   * The Compiler and Tools of Trident Text Editor
   */
  public static void compile(String filepath)
      throws UnsupportedOperatingSystemException, UnsupportedFileException, IOException {
    /*
     * Executes language specific compile commands on a system terminal.
     * 
     * @throws: Unsupported OS Exception, Unsupported File Exception, IOException,
     * InterruptedException
     */
    if (Trident.checkOS() != 1) {
      throw new UnsupportedOperatingSystemException();
    }
    String fileType = FileTypeParser.getType(filepath);
    switch (fileType) {
    case "Python Source File":
      Runtime.getRuntime().exec("cmd /c start cmd.exe /K" + "\"title Trident Compiler && echo && python \"" + filepath
          + "\" && pause && exit \"");
      break;

    case "Java Source File":
      Runtime.getRuntime().exec("cmd /c start cmd.exe /K" + "\"title Trident Compiler && echo && javac \"" + filepath
          + "\" && echo Compilation ended. && pause && exit \"");
      break;

    case "C Source File":
      String fileLocation = (new File(filepath)).getParent();
      Runtime.getRuntime().exec("cmd /c start cmd.exe /K" + "\"title Trident Compiler && cd \"" + fileLocation
          + "\" && echo && gcc \"" + filepath + "\" -std=c99 && echo Compilation ended. && pause && exit \"");
      // using C99 to avoid irritating forbidden errors
      break;

    case "C++ Source File":
      fileLocation = (new File(filepath)).getParent();
      Runtime.getRuntime().exec("cmd /c start cmd.exe /K" + "\"title Trident Compiler && cd \"" + fileLocation
          + "\" && echo && g++ \"" + filepath + "\" && echo Compilation ended. && pause && exit \"");
      break;

    default:
      throw new UnsupportedFileException(filepath);
    }
  }

  public static void execute(String filepath)
      throws UnsupportedOperatingSystemException, UnsupportedFileException, IOException, InterruptedException {
    /*
     * Runs any compiled output files defined for current language.
     * 
     * @throws: Unsupported OS Exception, Unsupported File Exception, IOException,
     * InterruptedException
     */
    if (Trident.checkOS() != 1) {
      throw new UnsupportedOperatingSystemException();
    }

    String exec;
    Process p = null;

    if (filepath.contains("powerboil")) {
      p = Runtime.getRuntime().exec("cmd /c start cmd.exe /K" + "\"title Trident - PowerBoil Alpha && echo && python \""
          + filepath + "\" && pause && exit \"");
      return;
    }

    String fileType = FileTypeParser.getType(filepath);
    switch (fileType) {
    case "Python Source File":
      p = Runtime.getRuntime().exec("cmd /c start cmd.exe /K" + "\"title Trident Compiler && echo && python \""
          + filepath + "\" && pause && exit \"");
      break;

    case "Java Source File":
    case "Java Class File":
      Path name = Paths.get(filepath);
      String classFile = name.getFileName().toString();
      exec = classFile.replaceFirst("[.][^.]+$", "");
      String location = new File(filepath).getParent().toString();
      p = Runtime.getRuntime().exec("cmd /c start cmd.exe /K" + "\"title Trident Compiler &&  cd \"" + location
          + "\" && echo && java " + exec + " && pause && exit \"");
      break;

    case "C Source File":
    case "C++ Source File":
      exec = (new File(filepath)).getParent() + "/a.exe";
      p = Runtime.getRuntime()
          .exec("cmd /c start cmd.exe /K" + "\"title Trident Compiler && echo && \"" + exec + "\" && pause && exit \"");
      break;

    case "HTML File":
      try {
        filepath = filepath.replace('\\', '/');
        Desktop.getDesktop().browse(java.net.URI.create("file:///" + filepath));
      } catch (IOException ioe) {
        Trident.ErrorDialog("COMPILER_BROSWER_ERR", ioe);
      }
      break;

    default:
      throw new UnsupportedFileException(filepath);
    }
    p.waitFor();
    p.destroy();
  }

  public static void openTerminal(int os) throws UnsupportedOperatingSystemException {
    /*
     * Opens System Terminal window
     * 
     * @throws: Unsupported OS Exception, if OS is not supported for this feature
     */
    try {
      if (os == 1) {
        String parent = (new File(Trident.path)).getParent();
        Runtime.getRuntime()
            .exec("cmd /c start cmd.exe /K" + "\"title Trident Compiler Console && cd " + parent + "\"");
      } else if (os == 2) {
        String[] processArgs = new String[] { "/bin/bash", "-c", "Start" };
        new ProcessBuilder(processArgs).start();
      } else
        throw new UnsupportedOperatingSystemException();
    } catch (UnsupportedOperatingSystemException unOS) {
      throw new UnsupportedOperatingSystemException();
    } catch (Exception uos) {
      Trident.ErrorDialog("TERMINAL_ERROR", uos);
    }
  }

}
