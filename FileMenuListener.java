
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

class FileMenuListener implements ActionListener {
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
      openDialog.setFileFilter(WebFiles);
      openDialog.setFileFilter(OtherFiles);
      openDialog.setFileFilter(SourceFiles);
      openDialog.setAcceptAllFileFilterUsed(true);
      int command = openDialog.showOpenDialog(Trident.frame);

      if (command == JFileChooser.APPROVE_OPTION) {
        Trident.path = openDialog.getSelectedFile().getAbsolutePath();
        openFile();
      } else if (command == JFileChooser.CANCEL_OPTION) {
        Trident.status1.setText("Operation cancelled by the user.");
        return;
      }
    } catch (Exception enr) {
      Trident.ErrorDialog("FILE_OPENER_ERR", enr);
      Trident.status1.setText("There was an error opening the file.");
    }
  }

  public static void openFile() {
    try {
      AutoSave.deleteSaved();
      File OpenedFile = new File(Trident.path);
      FileReader fr = new FileReader(OpenedFile);
      BufferedReader br = new BufferedReader(fr);
      String contents = "";
      for (String line = br.readLine(); line != null; line = br.readLine()) {
        contents += line + System.lineSeparator();
      }
      Trident.textarea.setText(contents);
      Trident.status1.setText("Editing existing file.");
      Trident.status2.setText("Saved");
      Trident.status3.setText(FileTypeParser.getType(Paths.get(Trident.path).getFileName().toString()));
      Trident.warned = false;

      Trident.undoManager = new UndoManager();
      Trident.textarea.getDocument().addUndoableEditListener(Trident.undoManager);

      Trident.Undo.setEnabled(false);
      Trident.Redo.setEnabled(false);

      Trident.frame.setTitle("Trident Text Editor - " + Paths.get(Trident.path).getFileName().toString());

      contents = null;
      fr.close();
      br.close();
      System.gc();
    } catch (IOException ioe) {
      Trident.ErrorDialog("FILE_OPEN_IO_ERR", ioe);
      Trident.status1.setText("Could not open the specified file.");
    } catch (Exception err) {
      err.printStackTrace();
      Trident.ErrorDialog("FILE_OPEN_ERR", err);
      Trident.status1.setText("Could not open the specified file.");
    }
  }

  public static void FileSaver(String filepath) {
    try {
      if (!filepath.equals("New File")) {
        File f1 = new File(filepath);
        if (!f1.exists()) {
          f1.createNewFile();
        }
        String contents = Trident.textarea.getText();
        FileWriter fileWritter = new FileWriter(f1, false);
        BufferedWriter bw = new BufferedWriter(fileWritter);
        bw.write(contents);
        bw.close();
        Trident.warned = false;
        Trident.frame.setTitle("Trident Text Editor - " + Paths.get(filepath).getFileName().toString());
        Trident.status1.setText("File saved successfully.");
        Trident.status2.setText("Saved");
        Trident.status3.setText(FileTypeParser.getType(Paths.get(filepath).getFileName().toString()));
      } else
        FileSaveAs();
    } catch (IOException ioe) {
      Trident.ErrorDialog("FILE_SAVE_IO", ioe);
      Trident.status1.setText("Error saving the file.");
    } catch (Exception unknownException) {
      Trident.ErrorDialog("FILE_SAVE_UNKNOWN", unknownException);
      Trident.status1.setText("Error saving the file.");
    }
  }

  public static void FileSaveAs() {
    JFileChooser saveAsDialog = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    int command = saveAsDialog.showSaveDialog(Trident.frame);

    if (command == JFileChooser.APPROVE_OPTION) {
      Trident.path = (saveAsDialog.getSelectedFile().getAbsolutePath());
      FileSaver(Trident.path);
    } else if (command == JFileChooser.CANCEL_OPTION) {
      Trident.status1.setText("File is not saved.");
    }
  }

  public static int warningDialog() {
    int opt = JOptionPane.showConfirmDialog(Trident.frame,
        "There are some unsaved changes in the file. Do you want to save the changes and continue?",
        "Warning: Unsaved changes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
        (new ImageIcon("raw/warning.png")));
    if (opt == JOptionPane.YES_OPTION) {
      FileSaver(Trident.path);
    }
    return opt;
  }

  public void actionPerformed(ActionEvent e) {
    try {
      switch (e.getActionCommand()) {
      case "New":
        if (Trident.warned) {
          int opt = warningDialog();
          if (opt == JOptionPane.CANCEL_OPTION) {
            Trident.status1.setText("Ready.");
            break;
          }
        }
        Trident.path = "New File";
        Trident.textarea.setText("");
        Trident.status1.setText("Ready.");
        Trident.status2.setText("Unsaved");
        Trident.status3.setText("Plain File");
        Trident.frame.setTitle("Trident Text Editor - New File");
        Trident.warned = false;
        Trident.Undo.setEnabled(false);
        Trident.Redo.setEnabled(false);
        Trident.undoManager = new UndoManager();
        Trident.textarea.getDocument().addUndoableEditListener(Trident.undoManager);

        break;

      case "Open":
        if (Trident.warned) {
          int opt = warningDialog();
          if (opt == JOptionPane.CANCEL_OPTION) {
            Trident.status1.setText("Ready.");
            break;
          }
        }
        FileOpenener();
        break;

      case "Exit":
        Trident.status1.setText("Exiting Trident...");
        if (Trident.warned) {
          int opt = warningDialog();
          if (opt == JOptionPane.NO_OPTION) {
            System.exit(0);
          } else {
            Trident.status1.setText("Ready.");
            break;
          }
        } else {
          System.exit(0);
        }

      case "Save":
        FileSaver(Trident.path);
        break;

      case "Save As":
        FileSaveAs();
        break;
      }
    } catch (Exception exp) {
      Trident.ErrorDialog("FILE_MENU_CRASH", exp);
    }
  }
}
