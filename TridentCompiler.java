import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Desktop;
import java.io.File;

public class TridentCompiler {
  public static void compile(String filepath)
      throws UnsupportedOperatingSystemException, UnsupportedFileException, IOException {
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
    if (Trident.checkOS() != 1) {
      throw new UnsupportedOperatingSystemException();
    }

    String exec;
    Process p = null;

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
    try {
      if (os == 1) {
        String parent = (new File(Trident.path)).getParent();
        Runtime.getRuntime()
            .exec("cmd /c start cmd.exe /K" + "\"title Trident Compiler Console && cd " + parent + "\"");
      } else if (os == 2) {
        String[] processArgs = new String[] { "/bin/bash", "-c", "Start" };
        Process proc = new ProcessBuilder(processArgs).start();
      } else
        throw new UnsupportedOperatingSystemException();
    } catch (UnsupportedOperatingSystemException unOS) {
      throw new UnsupportedOperatingSystemException();
    } catch (Exception uos) {
      Trident.ErrorDialog("TERMINAL_ERROR", uos);
    }
  }

}
