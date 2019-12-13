
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Paths;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

class FileMenuListener extends Trident implements ActionListener {
  public void FileOpenener() {
    try {
      FileNameExtensionFilter textFiles = new FileNameExtensionFilter("Text Files (*.txt, *.mf, *.md, *.rtf)", "txt",
          "mf", "md", "rtf");
      FileNameExtensionFilter SourceFiles = new FileNameExtensionFilter(
          "Source Files (*.py, *.java, *.c, *.cpp, *.h, *.kt)", "py", "java", "c", "cpp", "h", "kt");
      FileNameExtensionFilter WebFiles = new FileNameExtensionFilter(
          "Web Files (*.html, *.htm, *.mhtml, *.css, *.less,*.js, *.php)", "html", "htm", "mhtml", "css", "less", "js",
          "php");
      FileNameExtensionFilter OtherFiles = new FileNameExtensionFilter("Scripts (*.json, *.config, *.bat, *.sh)",
          "json", "config", "bat", "sh");
      JFileChooser openDialog = new JFileChooser(FileSystemView.getFileSystemView());
      openDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
      openDialog.setFileFilter(textFiles);
      openDialog.setFileFilter(SourceFiles);
      openDialog.setFileFilter(WebFiles);
      openDialog.setFileFilter(OtherFiles);
      openDialog.setAcceptAllFileFilterUsed(true);
      int command = openDialog.showOpenDialog(frame);

      if (command == JFileChooser.APPROVE_OPTION)
        path = openDialog.getSelectedFile().getAbsolutePath();
      else if (command == JFileChooser.CANCEL_OPTION) {
        status1.setText("Operation cancelled by the user.");
        return;
      }

      File OpenedFile = new File(path);
      FileReader fr = new FileReader(OpenedFile);
      BufferedReader br = new BufferedReader(fr);
      String contents = "";
      for (String line = br.readLine(); line != null; line = br.readLine()) {
        contents += line + System.lineSeparator();
      }
      textarea.setText(contents);
      status1.setText("Editing existing file.");
      status2.setText("Saved");
      status3.setText(FileTypeParser.getType(Paths.get(path).getFileName().toString()));
      warned = false;

      Trident.undoManager = new UndoManager();
      Trident.textarea.getDocument().addUndoableEditListener(undoManager);

      Undo.setEnabled(false);
      Redo.setEnabled(false);

      contents = null;
      fr.close();
      br.close();
      System.gc();
    } catch (Exception ioe) {
      ErrorDialog("FILE_OPENER", ioe);
      status1.setText("Ready.");
    }
  }

  public static void FileSaver(String filepath) {
    try {
      if (!path.equals("New File")) {
        File f1 = new File(filepath);
        if (!f1.exists()) {
          f1.createNewFile();
        }
        String contents = textarea.getText();
        FileWriter fileWritter = new FileWriter(f1, false);
        BufferedWriter bw = new BufferedWriter(fileWritter);
        bw.write(contents);
        bw.close();
        warned = false;
        frame.setTitle("Trident Text Editor - " + Paths.get(path).getFileName().toString());
        status1.setText("File saved successfully.");
        status2.setText("Saved");
        status3.setText(FileTypeParser.getType(Paths.get(path).getFileName().toString()));
      } else
        FileSaveAs();
    } catch (IOException ioe) {
      ErrorDialog("FILE_SAVE_IO", ioe);
      status1.setText("Error saving the file.");
    } catch (Exception unknownException) {
      ErrorDialog("FILE_SAVE_UNKNOWN", unknownException);
      status1.setText("Error saving the file.");
    }
  }

  public static void FileSaveAs() {
    JFileChooser saveAsDialog = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    int command = saveAsDialog.showSaveDialog(frame);

    if (command == JFileChooser.APPROVE_OPTION) {
      path = (saveAsDialog.getSelectedFile().getAbsolutePath());
      FileSaver(path);
    } else if (command == JFileChooser.CANCEL_OPTION) {
      status1.setText("File is not saved.");
    }
  }

  public static int warningDialog() {
    int opt = JOptionPane.showConfirmDialog(frame,
        "There are some unsaved changes in the file. Do you want to save the changes and continue?",
        "Warning: Unsaved changes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
        (new ImageIcon("raw/warning.png")));
    if (opt == JOptionPane.YES_OPTION) {
      FileSaver(path);
    }
    return opt;
  }

  public void actionPerformed(ActionEvent e) {
    try {
      switch (e.getActionCommand()) {
      case "New":
        if (warned) {
          int opt = warningDialog();
          if (opt == JOptionPane.CANCEL_OPTION) {
            status1.setText("Ready.");
            break;
          }
        }
        path = "New File";
        textarea.setText("");
        status1.setText("Ready.");
        status2.setText("Unsaved");
        status3.setText("Plain File");
        frame.setTitle("Trident Text Editor - New File");
        warned = false;
        Undo.setEnabled(false);
        Redo.setEnabled(false);
        Trident.undoManager = new UndoManager();
        Trident.textarea.getDocument().addUndoableEditListener(undoManager);

        break;

      case "Open":
        if (warned) {
          int opt = warningDialog();
          if (opt == JOptionPane.CANCEL_OPTION) {
            status1.setText("Ready.");
            break;
          }
        }
        FileOpenener();
        frame.setTitle("Trident Text Editor - " + Paths.get(path).getFileName().toString());
        break;

      case "Exit":
        status1.setText("Exiting Trident...");
        if (warned) {
          int opt = warningDialog();
          if (opt == JOptionPane.NO_OPTION) {
            System.exit(0);
          } else {
            status1.setText("Ready.");
            break;
          }
        } else {
          System.exit(0);
        }

      case "Save":
        FileSaver(path);
        break;

      case "Save As":
        FileSaveAs();
        break;
      }
    } catch (Exception exp) {
      ErrorDialog("FILE_MENU_CRASH", exp);
    }
  }
}
