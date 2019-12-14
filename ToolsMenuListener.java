import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.File;

class ToolsMenuListener implements ActionListener {
  public final void openTerminal(int os) throws UnsupportedOperatingSystemException {
    try {
      if (os == 1) {
        String[] processArgs = new String[] { "cmd.exe", "/c", "Start" };
        Process proc = new ProcessBuilder(processArgs).start();
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

  public void actionPerformed(ActionEvent e) {
    try {
      switch (e.getActionCommand()) {
      case "Compile":
        TridentCompiler.compile(Trident.path);
        Trident.status1.setText("Compilation ended.");
        break;

      case "Run":
        TridentCompiler.execute(Trident.path);
        Trident.status1.setText("Execution window deployed.");
        break;

      case "Compile and Run":
        TridentCompiler.compile(Trident.path);
        Trident.status1.setText("Compilation ended.");
        Thread.sleep(3000);
        TridentCompiler.execute(Trident.path);
        Trident.status1.setText("Execution window deployed.");
        break;

      case "Open Console":
        openTerminal(Trident.checkOS());
        break;
      }
    } catch (IOException ioException) {
      Trident.ErrorDialog("PROCESS_BUILD_FILEIO", ioException);
    } catch (UnsupportedOperatingSystemException unOs) {
      Trident.ErrorDialog("OS_UNSUPPORTED", unOs);
    } catch (UnsupportedFileException fileNS) {
      Trident.ErrorDialog("FILE_UNSUPPORTED", fileNS);
    } catch (Exception unknownException) {
      unknownException.printStackTrace();
      Trident.ErrorDialog("TOOLS_MENU_CRASH", unknownException);
    }
  }
}
