
// * AWT ELEMENTS
import java.awt.*;
import java.awt.event.*;

// * SWING ELEMENTS
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
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
  public static JLabel status1, status2, status3, status4;
  public static String fileType;
  protected static JFrame frame;
  public static String path = System.getProperty("java.io.tmpdir") + "Unsaved file";
  public static String uitheme;
  public static Boolean warned;

  public static void main(String[] args) {
    try {
      // * Local Variable declarations
      MenuActionListener mml = new MenuActionListener();

      // * Global variable inits
      {
        warned = false;
        fileType = "Unknown file";
        textarea = new JTextArea();
        uitheme = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
      }

      // * Theming
      try {
        UIManager.setLookAndFeel(uitheme);
      } catch (Exception themeError) {
        System.err.println("Error theming the application.");
      }

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
      JMenuBar mb = new JMenuBar();
      {
        JMenu fileMenu = new JMenu("File");
        {
          fileMenu.setMnemonic(KeyEvent.VK_F);
          JMenuItem newFile = new JMenuItem("New");
          fileMenu.add(newFile);
          newFile.addActionListener(mml);

          JMenuItem OpenFile = new JMenuItem("Open");
          fileMenu.add(OpenFile);
          OpenFile.addActionListener(mml);

          JMenuItem SaveFile = new JMenuItem("Save");
          fileMenu.add(SaveFile);
          SaveFile.addActionListener(mml);

          JMenuItem SaveAs = new JMenuItem("Save As");
          fileMenu.add(SaveAs);
          SaveAs.addActionListener(mml);

          JMenuItem Exit = new JMenuItem("Exit");
          fileMenu.add(Exit);
          Exit.addActionListener(mml);
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
        }

        JMenu formatMenu = new JMenu("Format");
        {
          formatMenu.setMnemonic(KeyEvent.VK_O);
          JMenuItem wwrap = new JMenuItem("Word wrap");
          formatMenu.add(wwrap);
          JMenuItem fontOptions = new JMenuItem("Fonts");
          formatMenu.add(fontOptions);
          JMenuItem themes = new JMenuItem("Themes");
          formatMenu.add(themes);
          JMenuItem settings = new JMenuItem("Settings");
          formatMenu.add(settings);
        }

        JMenu about = new JMenu("About");
        {
          JMenuItem AboutTrident = new JMenuItem("About Trident");
          about.add(AboutTrident);
          AboutTrident.addActionListener(mml);

          JMenuItem visit = new JMenuItem("Visit our site");
          about.add(visit);

          JMenuItem help = new JMenuItem("Help");
          about.add(help);
        }

        mb.add(fileMenu);
        mb.add(editMenu);
        mb.add(formatMenu);
        mb.add(about);
      } // * Menu bar setup ends here

      // * Text Area setup
      JScrollPane editor = new JScrollPane(textarea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
          JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      {
        textarea.getDocument().addDocumentListener(new ChangeListener());
        editor.setBorder(new EmptyBorder(-1, 0, -1, 0));
      }

      // * Default configs
      // TODO: These will be configurable by the user
      {
        textarea.setLineWrap(false);
        textarea.setFont(new Font("Consolas", 12, 12));
        textarea.setTabSize(4);
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

class MenuActionListener extends Trident implements ActionListener, MenuListener {
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
      contents = null;
      fr.close();
      br.close();
      System.gc();
    } catch (IOException ioe) {
      status1.setText("Error opening file.");
    }
  }

  public void FileSaver(String filepath) { // ! Running twice for no reason
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
    } catch (IOException ioe) {
      status1.setText("Error saving the file.");
    }
  }

  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
    case "New":
      textarea.setText("");
      path = System.getProperty("java.io.tmpdir") + "Unsaved file";
      FileOpenener(path);
      status1.setText("Editing temporary file.");
      frame.setTitle("Trident Text Editor - " + Paths.get(path).getFileName().toString());
      break;

    case "Open":
      JFileChooser openDialog = new JFileChooser(FileSystemView.getFileSystemView());
      int command = openDialog.showOpenDialog(null);

      if (command == JFileChooser.APPROVE_OPTION) {
        path = openDialog.getSelectedFile().getAbsolutePath();
      }
      FileOpenener(path);
      frame.setTitle("Trident Text Editor - " + Paths.get(path).getFileName().toString());

      break;

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

    case "Exit":
      status1.setText("Exiting Trident...");
      try {
        Thread.sleep(100);
      } catch (InterruptedException ie) {
        status1.setText("Could not exit. Use Task Manager to kill the process.");
      }
      System.exit(0);

    case "About Trident":
      JDialog aboutDialog = new JDialog(frame, "About Trident");
      JLabel l1 = new JLabel("Trident Text Editor");
      aboutDialog.add(l1);
      aboutDialog.setSize(300, 200);
      aboutDialog.setVisible(true);
      break;
    }
  }

  @Override
  public void menuSelected(MenuEvent me) {

  }

  @Override
  public void menuDeselected(MenuEvent me) {

  }

  @Override
  public void menuCanceled(MenuEvent me) {

  }
}

class ChangeListener extends Trident implements DocumentListener {
  private static void warn() {
    if (!warned && !(path.equals(System.getProperty("java.io.tmpdir") + "Unsaved file"))) {
      warned = true;
      status2.setText("Unsaved");
      frame.setTitle(frame.getTitle() + " - Unsaved");
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
