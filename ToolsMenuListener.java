import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// * IO ELEMENTS
import java.io.File;

class ToolsMenuListener extends Trident implements ActionListener {
  public final void openTerminal(int os) throws UnsupportedOperatingSystemException {
    try {
      if (os == 1) {
        String[] processArgs = new String[] { "cmd.exe", "/c", "Start" };
        Process proc = new ProcessBuilder(processArgs).start();
      } else if (os == 2) {
        String[] processArgs = new String[] { "bash", "-c", "Start" };
        Process proc = new ProcessBuilder(processArgs).start();
      } else
        throw new UnsupportedOperatingSystemException();
    } catch (UnsupportedOperatingSystemException unOS) {
      throw new UnsupportedOperatingSystemException();
    } catch (Exception uos) {
      ErrorDialog("TERMINAL_ERROR", uos);
    }
  }

  public void actionPerformed(ActionEvent e) {
    try {
      switch (e.getActionCommand()) {
      case "Compile":
        (new TridentCompiler(path)).compile();
        status1.setText("Compilation ended.");
        break;

      case "Run":
        (new TridentCompiler(path)).execute();
        status1.setText("Execution ended.");
        break;

      case "Compile and Run":
        TridentCompiler compiler = (new TridentCompiler(path));
        compiler.compile();
        status1.setText("Compilation ended.");
        compiler.execute();
        status1.setText("Execution ended.");
        break;

      case "Open Console":
        openTerminal(checkOS());
        break;
      }
    } catch (InterruptedException interruptedException) {
      ErrorDialog("PROCESS_BUILD_INTERRUPTED", interruptedException);
    } catch (IOException ioException) {
      ErrorDialog("PROCESS_BUILD_FILEIO", ioException);
    } catch (UnsupportedOperatingSystemException unOs) {
      ErrorDialog("OS_UNSUPPORTED", unOs);
    } catch (UnsupportedFileException fileNS) {
      ErrorDialog("FILE_UNSUPPORTED", fileNS);
    } catch (Exception unknownException) {
      unknownException.printStackTrace();
      ErrorDialog("TOOLS_MENU_CRASH", unknownException);
    }
  }
}
