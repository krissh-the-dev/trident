
/*
 *  FileMenuListener.java
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

/*
 * (GPL v3) Trident > FileMenuListener
 * @author: Krishna Moorthy
 */
class FileMenuListener implements ActionListener {
  public static void FileOpener() {
    /*
     * Opens the File Opener JFileChooser in user set Look and feel
     */
    try {
      // text files include plaint text, markdown, maniests, encrypt files etc.
      FileNameExtensionFilter textFiles = new FileNameExtensionFilter("Text Files (*.txt, *.mf, *.md, *.rtf)", "txt",
          "mf", "md", "rtf");
      // Source files include major program source code files formats
      FileNameExtensionFilter SourceFiles = new FileNameExtensionFilter(
          "Source Files (*.py, *.java, *.c, *.cpp, *.h, *.kt)", "py", "java", "c", "cpp", "h", "kt");

      // Web files contain files related to web pages and web apps
      FileNameExtensionFilter WebFiles = new FileNameExtensionFilter(
          "Web Files (*.html, *.htm, *.mhtml, *.css, *.less,*.js, *.php)", "html", "htm", "mhtml", "css", "less", "js",
          "php");
      // General other categories like object notations etc.
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
    /*
     * Opens the file in 'Trident.path' variable and places its contents in the
     * textarea
     */
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
      Toolbar.undoButton.setEnabled(false);
      Trident.Redo.setEnabled(false);
      Toolbar.redoButton.setEnabled(false);

      Trident.frame.setTitle("Trident Text Editor - " + Paths.get(Trident.path).getFileName().toString());

      contents = null;
      fr.close();
      br.close();
      System.gc();
    } catch (Exception ioe) {
      Trident.ErrorDialog("FILE_OPEN_ERR", ioe);
      Trident.status1.setText("Could not open the specified file.");
    }
  }

  @Deprecated
  public static void FileSaver(String filepath) {
    /*
     * Saves the changes made to the file in 'filepath'
     * 
     * @param: path of the file to save
     */
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
    } catch (Exception ioe) {
      Trident.ErrorDialog("FILE_SAVE_IO", ioe);
      Trident.status1.setText("Error saving the file.");
    }
  }

  public static void saveFile() {
    /*
     * Saves the changes made to the file in 'Trident.path' i.e current file
     */
    try {
      if (!Trident.path.equals("New File")) {
        File f1 = new File(Trident.path);
        if (!f1.exists()) {
          f1.createNewFile();
        }
        String contents = Trident.textarea.getText();
        FileWriter fileWritter = new FileWriter(f1, false);
        BufferedWriter bw = new BufferedWriter(fileWritter);
        bw.write(contents);
        bw.close();
        Trident.warned = false;
        Trident.frame.setTitle("Trident Text Editor - " + Paths.get(Trident.path).getFileName().toString());
        Trident.status1.setText("File saved successfully.");
        Trident.status2.setText("Saved");
        Trident.status3.setText(FileTypeParser.getType(Paths.get(Trident.path).getFileName().toString()));
      } else
        FileSaveAs();
    } catch (Exception ioe) {
      Trident.ErrorDialog("FILE_SAVE_IO", ioe);
      Trident.status1.setText("Error saving the file.");
    }
  }

  public static void FileSaveAs() {
    /*
     * Opens up JFileChooser Save As dialog to choose a path to save the current
     * file
     */
    JFileChooser saveAsDialog = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    int command = saveAsDialog.showSaveDialog(Trident.frame);

    if (command == JFileChooser.APPROVE_OPTION) {
      Trident.path = (saveAsDialog.getSelectedFile().getAbsolutePath());
      saveFile();
      // saveFile();
    } else if (command == JFileChooser.CANCEL_OPTION) {
      Trident.status1.setText("File is not saved.");
    }
  }

  public static int warningDialog() {
    /*
     * Displays a warning Dialog box when user attempts to leave the current file
     * without saving any recent changes, irrespective of autosaved copies
     */
    int opt = JOptionPane.showConfirmDialog(Trident.frame,
        "There are some unsaved changes in the file." + System.lineSeparator()
            + "Do you want to save the changes and continue?",
        "Warning: Unsaved changes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
        (new ImageIcon("raw/warning.png")));
    if (opt == JOptionPane.YES_OPTION) {
      saveFile();
      // saveFile();
    }
    return opt;
  }

  public static boolean newFile() {
    /*
     * Creates a new temporary file in arbitrary location. Refreshes and empties all
     * the older contents
     */
    if (Trident.warned) {
      int opt = warningDialog();
      if (opt == JOptionPane.CANCEL_OPTION) {
        Trident.status1.setText("Ready.");
        return false;
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
    Toolbar.undoButton.setEnabled(false);
    Toolbar.redoButton.setEnabled(false);
    Trident.undoManager = new UndoManager();
    Trident.textarea.getDocument().addUndoableEditListener(Trident.undoManager);
    return true;
  }

  protected static void boil(String choice)
      throws UnsupportedFileException, UnsupportedOperatingSystemException, IOException, InterruptedException {
    /*
     * Adds appropriate language specific boilerplate code to a new file.
     * 
     * @throws: Unsupported File Exception when the selected file is not a source
     * file, resulting in crashing of the function
     */
    String boiler = null;
    switch (choice) {
      case "C Source File":
        boiler = "boilers/c.c";
        break;

      case "C++ Source File":
        boiler = "boilers/c++.cpp";
        break;

      case "Python Source File":
        boiler = "boilers/python.py";
        break;

      case "Java Source File":
        boiler = "boilers/java.java";
        break;

      case "HTML File":
        boiler = "boilers/html5.html";
        break;

      case "Open PowerBoil":
        TridentCompiler.execute("boilers/powerboil/powerboil.py");
        return;

      default:
        throw new UnsupportedFileException(Trident.path);
    }
    try {
      if (newFile()) {
        BufferedReader be = new BufferedReader(new FileReader(new File(boiler)));
        String content = be.readLine();
        String contents = "";
        while (content != null) {
          contents += content + System.lineSeparator();
          content = be.readLine();
        }
        Trident.textarea.setText(contents);
        Trident.status3.setText(choice);
        be.close();
      } else {
        Trident.status1.setText("Operation cancelled by the user.");
      }
    } catch (Exception e) {
      throw e;
    }
  }

  public void actionPerformed(ActionEvent e) {
    /*
     * Controls the actions of File Menu
     */
    try {
      switch (e.getActionCommand()) {
        case "New":
          newFile();
          break;

        case "New Window":
          // new Trident("New File");
          ProcessBuilder pb = new ProcessBuilder("Trident.bat");
          Process p = pb.start();
          break;

        case "Open":
          if (Trident.warned) {
            int opt = warningDialog();
            if (opt == JOptionPane.CANCEL_OPTION) {
              Trident.status1.setText("Ready.");
              break;
            }
          }
          FileOpener();
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
          saveFile();
          break;

        case "Save As":
          FileSaveAs();
          break;

        default: /* For Boilers */
          /*
           * JMenuItem source = (JMenuItem) e.getSource(); JPopupMenu jpm = (JPopupMenu)
           * source.getParent(); JMenu pMenu = (JMenu) jpm.getInvoker();
           * System.out.println(pMenu.getActionCommand());
           */
          boil(e.getActionCommand());
          break;
      }
    } catch (UnsupportedFileException ufe) {
      Trident.ErrorDialog("SOURCE_ACTION_ERR", ufe);
    } catch (Exception exp) {
      Trident.ErrorDialog("FILE_MENU_CRASH", exp);
    }
  }
}
