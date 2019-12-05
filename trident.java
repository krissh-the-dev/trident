
// * AWT ELEMENTS
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

// * CLIPBOARD ELEMENTS AND UNDO HANDLERS
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

// * SWING ELEMENTS
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.filechooser.*;

// * IO ELEMENTS
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;

class Trident {
  protected static JTextArea textarea;
  protected static JFrame frame;
  public static JLabel status1, status2, status3, status4;
  public static String fileType;
  public static String path = System.getProperty("java.io.tmpdir") + "Unsaved file";
  public static String uitheme;
  public static Boolean warned;
  public static JMenuBar mb;
  public static JScrollPane editor;
  public static String configFilePath;

  // * HINTS
  // TODO: to be used with cut copy paste
  // clipboard.setContents(Transferable contents, ClipboardOwner owner);
  // textarea.cut(), .copy(), .paste();

  public static String fileTypeParser(String fileName) {
    String extension = "";

    int i = fileName.lastIndexOf('.');
    if (i > 0) {
      extension = fileName.substring(i + 1);
    }

    return (extension.toUpperCase() + " File");
  }

  public static void applyTheme() {
    try {
      uitheme = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"; // TODO: Will read from file
      UIManager.setLookAndFeel(uitheme);
    } catch (Exception themeError) {
      System.err.println("Error theming the application.");
    }
  }

  public static boolean applyConfigs() {
    // * Default configs
    // TODO: These will be configurable by the user
    textarea.setLineWrap(false);
    textarea.setFont(new Font("Consolas", 14, 14));
    textarea.setTabSize(4);
    textarea.setBorder(new EmptyBorder(2, 2, 0, 0));
    mb.setBackground(Color.WHITE);
    editor.setBackground(Color.WHITE);
    textarea.setBackground(Color.WHITE);
    textarea.setSelectedTextColor(Color.WHITE);
    textarea.setSelectionColor(new Color(23, 135, 227));
    return true;
  }

  public static void main(String[] args) {
    try {
      // * Local Variable declarations
      FileMenuListener fml = new FileMenuListener();
      EditMenuListener eml = new EditMenuListener();
      FormatMenuListener oml = new FormatMenuListener();
      AboutMenuListener aml = new AboutMenuListener();

      // * Global variable inits
      {
        warned = false;
        fileType = " File";
        textarea = new JTextArea();
        mb = new JMenuBar();
        configFilePath = "configurations.json";
      }

      applyTheme();

      // * Frame Setup
      {
        frame = new JFrame();
        frame.setTitle("Trident Text Editor - " + Paths.get(path).getFileName().toString());
        frame.setSize(800, 550);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        ImageIcon ic = new ImageIcon("raw\\trident.png");
        frame.setIconImage(ic.getImage());
      }

      // * Menu Bar Setup
      {
        JMenu fileMenu = new JMenu("File");
        {
          fileMenu.setMnemonic(KeyEvent.VK_F);
          JMenuItem newFile = new JMenuItem("New");
          fileMenu.add(newFile);
          newFile.addActionListener(fml);

          JMenuItem OpenFile = new JMenuItem("Open");
          fileMenu.add(OpenFile);
          OpenFile.addActionListener(fml);

          JMenuItem SaveFile = new JMenuItem("Save");
          SaveFile.setMnemonic(KeyEvent.VK_S);
          fileMenu.add(SaveFile);
          SaveFile.addActionListener(fml);

          JMenuItem SaveAs = new JMenuItem("Save As");
          fileMenu.add(SaveAs);
          SaveAs.addActionListener(fml);

          JMenuItem Exit = new JMenuItem("Exit");
          fileMenu.add(Exit);
          Exit.addActionListener(fml);
        }

        JMenu editMenu = new JMenu("Edit");
        {
          editMenu.setMnemonic(KeyEvent.VK_E);
          JMenuItem Undo = new JMenuItem("Undo");
          editMenu.add(Undo);
          JMenuItem Redo = new JMenuItem("Redo");
          editMenu.add(Redo);
          JMenuItem Copy = new JMenuItem("Copy");
          editMenu.add(Copy);
          JMenuItem Cut = new JMenuItem("Cut");
          editMenu.add(Cut);
          JMenuItem Paste = new JMenuItem("Paste");
          editMenu.add(Paste);

          JMenu ClipMenu = new JMenu("Clipboard");
          {
            editMenu.add(ClipMenu);
            JMenuItem ShowClipboard = new JMenuItem("Show Contents");
            ClipMenu.add(ShowClipboard);
            ShowClipboard.addActionListener(eml);
            JMenuItem EraseClipboard = new JMenuItem("Erase Contents");
            ClipMenu.add(EraseClipboard);
            EraseClipboard.addActionListener(eml);
          }
        }

        JMenu formatMenu = new JMenu("Format");
        {
          formatMenu.setMnemonic(KeyEvent.VK_O);
          JMenuItem fontOptions = new JMenuItem("Fonts");
          formatMenu.add(fontOptions);
          JMenuItem themes = new JMenuItem("Themes");
          formatMenu.add(themes);
          JMenuItem settings = new JMenuItem("Settings");
          formatMenu.add(settings);
        }

        JMenu about = new JMenu("About");
        {
          JMenuItem AboutFile = new JMenuItem("About File");
          about.add(AboutFile);

          JMenuItem visit = new JMenuItem("Visit our site");
          about.add(visit);

          JMenuItem help = new JMenuItem("Help");
          about.add(help);

          JMenuItem AboutTrident = new JMenuItem("About Trident");
          about.add(AboutTrident);
          AboutTrident.addActionListener(aml);
        }

        mb.add(fileMenu);
        mb.add(editMenu);
        mb.add(formatMenu);
        mb.add(about);
      } // * Menu bar setup ends here

      // * Text Area setup
      editor = new JScrollPane(textarea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
          JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      {
        textarea.getDocument().addDocumentListener(new ChangeListener());
        // textarea.setComponentPopupMenu() // TODO: Add popup menu
        editor.setBorder(new EmptyBorder(-1, 0, -1, 0));
      }

      // * Status bar setup
      JPanel statusBar = new JPanel();
      {
        status1 = new JLabel("Ready");
        status2 = new JLabel("Unsaved");
        status3 = new JLabel(fileType);
        status4 = new JLabel("Satus Area 4");

        statusBar.setSize(30, 2500);
        statusBar.setBorder(new EmptyBorder(2, 3, 2, 2));
        statusBar.setLayout(new GridLayout(1, 4, 2, 2));
        statusBar.setBackground(Color.LIGHT_GRAY);
        statusBar.add(status1);
        statusBar.add(status2);
        statusBar.add(status3);
        statusBar.add(status4);

        frame.getContentPane().add(mb, BorderLayout.NORTH);
        frame.getContentPane().add(editor, BorderLayout.CENTER);
        frame.getContentPane().add(statusBar, BorderLayout.SOUTH);

        // * Theming
        applyConfigs();

        frame.setVisible(true);
      } // * Status bar setup ends here

      // * Temporary file setup
      File file = new File(path);
      file.createNewFile();
      status1.setText("Working with temporary file.");

    } catch (IOException ioe) {
      status1.setText("Create a new file.");
      JOptionPane.showMessageDialog(new JFrame(),
          "There was an error creating the temporary file. Create new file or Open an existing file to continue working.",
          "File Operation Error.", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
      System.err.println("Unexpected crash...");
      e.printStackTrace();
      System.exit(0);
    }
  }
}

class FileMenuListener extends Trident implements ActionListener {
  public void FileOpenener(String filepath) {
    try {
      File OpenedFile = new File(filepath);
      FileReader fr = new FileReader(OpenedFile);
      BufferedReader br = new BufferedReader(fr);
      String contents = "";
      for (String line = br.readLine(); line != null; line = br.readLine()) {
        contents += line + System.lineSeparator();
      }
      textarea.setText(contents);
      status1.setText("Editing existing file.");
      warned = false;
      status2.setText("Saved");
      status3.setText(fileTypeParser(Paths.get(path).getFileName().toString()));

      contents = null;
      fr.close();
      br.close();
      System.gc();
    } catch (IOException ioe) {
      status1.setText("Error opening file.");
    }
  }

  // ! Running twice for no reason
  public void FileSaver(String filepath) {
    try {
      File f1 = new File(filepath);
      if (!f1.exists()) {
        f1.createNewFile();
      }
      String contents = textarea.getText();
      FileWriter fileWritter = new FileWriter(f1, false);
      BufferedWriter bw = new BufferedWriter(fileWritter);
      bw.write(contents);
      bw.close();
      status1.setText("File saved successfully.");
      warned = false;
      status2.setText("Saved");
      status3.setText(fileTypeParser(Paths.get(path).getFileName().toString()));
    } catch (IOException ioe) {
      status1.setText("Error saving the file.");
    }
  }

  public void actionPerformed(ActionEvent e) {
    Boolean exitNow = false;
    try {
      switch (e.getActionCommand()) {
      case "New":
        textarea.setText("");
        path = System.getProperty("java.io.tmpdir") + "Unsaved file";
        FileOpenener(path);
        status1.setText("Editing temporary file.");
        status2.setText("Unsaved");
        frame.setTitle("Trident Text Editor - " + Paths.get(path).getFileName().toString());
        break;

      case "Open":
        JFileChooser openDialog = new JFileChooser(FileSystemView.getFileSystemView());
        int command = openDialog.showOpenDialog(null);

        if (command == JFileChooser.APPROVE_OPTION)
          path = openDialog.getSelectedFile().getAbsolutePath();

        FileOpenener(path);
        frame.setTitle("Trident Text Editor - " + Paths.get(path).getFileName().toString());
        break;

      case "Exit":
        status1.setText("Exiting Trident...");
        exitNow = true;
        if (warned) {
          int opt = JOptionPane.showConfirmDialog(new JFrame("Exiting..."),
              "There are some unsaved changes in the file. Do you want to save the changes?");
          if (opt == JOptionPane.YES_OPTION) {
            // doing nothing :P - it'll continue to save as there is no break
          } else if (opt == JOptionPane.NO_OPTION) {
            System.exit(0);
          } else if (opt == JOptionPane.CANCEL_OPTION) {
            break;
          }
        } else {
          System.exit(0);
        }

      case "Save":
        if (!path.equals(System.getProperty("java.io.tmpdir") + "Unsaved file")) {
          FileSaver(path);
          frame.setTitle("Trident Text Editor - " + Paths.get(path).getFileName().toString());
          break;
        }

      case "Save As":
        JFileChooser saveAsDialog = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        command = saveAsDialog.showSaveDialog(null);

        if (command == JFileChooser.APPROVE_OPTION) {
          path = (saveAsDialog.getSelectedFile().getAbsolutePath());
          FileSaver(path);
        } else
          status1.setText("File is not saved.");

        frame.setTitle("Trident Text Editor - " + Paths.get(path).getFileName().toString());
        break;
      }
    } catch (Exception exp) {
    } finally {
      if (exitNow) {
        System.exit(0);
      }
    }

  }
}

class EditMenuListener extends Trident implements ActionListener {
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
    case "Show Contents":
      Clipboard clipboard;
      try {
        // ! Size of text area is improper
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        JDialog cbviewer = new JDialog();
        cbviewer.setSize(300, 400);
        cbviewer.setTitle("Clipboard Viewer");
        JPanel TextViewer = new JPanel();
        JTextArea cta = new JTextArea(clipboard.getData(DataFlavor.stringFlavor).toString());
        JScrollPane spv = new JScrollPane(cta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        spv.setBorder(new EmptyBorder(-1, 0, -1, 0));
        cta.setSize(300, 400);
        spv.setSize(300, 400);
        TextViewer.setLayout(new FlowLayout());
        cbviewer.setLayout(new BorderLayout());
        TextViewer.add(spv);
        cbviewer.getContentPane().add(TextViewer, BorderLayout.CENTER);
        cbviewer.setVisible(true);
      } catch (UnsupportedFlavorException ufe) {
        System.err.println("UFE");
      } catch (IOException ioe) {
        System.err.println("IOE");
      }
      break;
    case "Erase Contents":

    }
  }
}

class FormatMenuListener extends Trident implements ActionListener {
  public void actionPerformed(ActionEvent e) {
  }
}

class AboutMenuListener extends Trident implements ActionListener {
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
    case "About Trident":
      JDialog aboutDialog = new JDialog(frame, "About Trident");
      JPanel infoPanel = new JPanel();
      ImageIcon ic = new ImageIcon("raw\\trident_logo.png");
      JLabel icon = new JLabel(ic);
      icon.setSize(50, 50);
      JLabel l1 = new JLabel(
          "<html> <center><h2> <br/>Trident Text Editor</h2> <br/> Version 0.0.3 <br/>ALPHA<br/> <a href=\"https://github.com/KrishnaMoorthy12/trident\">View Source Code - GitHub</a></center> </html>");
      // ! Link is not clickable
      infoPanel.setBorder(new EmptyBorder(10, 5, 5, 5));
      infoPanel.add(icon);
      infoPanel.add(l1);
      aboutDialog.add(infoPanel);
      aboutDialog.setSize(350, 500);
      aboutDialog.setResizable(false);
      aboutDialog.setVisible(true);
      break;
    }
  }
}

class ChangeListener extends Trident implements DocumentListener {
  private static void warn() {
    if (!warned) {
      status2.setText("Unsaved");
      warned = true;
      if (!(path.equals(System.getProperty("java.io.tmpdir") + "Unsaved file"))) {
        frame.setTitle(frame.getTitle() + " - Unsaved");
      }
    }
  }

  public void changedUpdate(DocumentEvent e) {
    warn();
  }

  public void removeUpdate(DocumentEvent e) {
    warn();
  }

  public void insertUpdate(DocumentEvent e) {
    warn();
  }
}
