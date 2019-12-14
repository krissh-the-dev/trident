import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.File;

class ToolsMenuListener implements ActionListener {
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
        TridentCompiler.openTerminal(Trident.checkOS());
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
