
// AWT ELEMENTS
import java.awt.*;
import java.awt.event.*;

// SWING ELEMENTS
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.filechooser.*;

// IO ELEMENTS
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;

class Trident {
  protected static JTextArea editor;
  public static JLabel status;
  protected static JFrame frame;
  public static String path = System.getProperty("java.io.tmpdir") + "Unsaved file";
  public static Boolean warned;

  public static void main(String[] args) {
    try {
      try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
      } catch (Exception themeError) {
        System.err.println("Error theming the application.");
      }
      frame = new JFrame();
      MenuActionListener mml = new MenuActionListener();
      warned = false;
      frame.setTitle("Trident Text Editor - " + Paths.get(path).getFileName().toString());
      frame.setSize(800, 550);
      frame.setResizable(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());
      ImageIcon ic = new ImageIcon("raw\\trident.png");
      frame.setIconImage(ic.getImage());

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
      }

      editor = new JTextArea();
      JScrollPane scrollBar = new JScrollPane(editor, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
          JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      editor.getDocument().addDocumentListener(new ChangeListener());

      // WILL BE MADE CONFIGURABLE
      editor.setLineWrap(false);
      editor.setFont(new Font("Consolas", 12, 12));
      editor.setTabSize(4);

      JPanel statusBar = new JPanel();
      status = new JLabel("Ready");
      statusBar.setSize(30, 2500);
      statusBar.setLayout(new GridLayout(1, 4, 2, 2));
      statusBar.add(status);

      frame.getContentPane().add(mb, BorderLayout.NORTH);
      frame.getContentPane().add(scrollBar, BorderLayout.CENTER);
      frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
      frame.setVisible(true);

      /* Working area */

      File file = new File(path);
      file.createNewFile();
      status.setText("Working with temporary file.");
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
      editor.setText(contents);
      status.setText("Editing existing file.");
      warned = false;
      contents = null;
      fr.close();
      br.close();
      System.gc();
    } catch (IOException ioe) {
      status.setText("Error opening file.");
    }
  }

  public void FileSaver(String filepath) {
    try {
      File f1 = new File(filepath);
      if (!f1.exists()) {
        f1.createNewFile();
      }
      String contents = editor.getText();
      FileWriter fileWritter = new FileWriter(f1, false);
      BufferedWriter bw = new BufferedWriter(fileWritter);
      bw.write(contents);
      bw.close();
      status.setText("File saved successfully.");
      warned = false;
    } catch (IOException ioe) {
      status.setText("Error saving the file.");
    }
  }

  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
    case "New":
      editor.setText("");
      path = System.getProperty("java.io.tmpdir") + "Unsaved file";
      FileOpenener(path);
      status.setText("Editing temporary file.");
      frame.setTitle("Trident Text Editor - " + Paths.get(path).getFileName().toString());
      break;

    case "Open":
      JFileChooser bb = new JFileChooser(FileSystemView.getFileSystemView());
      int bbd = bb.showOpenDialog(null);

      if (bbd == JFileChooser.APPROVE_OPTION) {
        path = bb.getSelectedFile().getAbsolutePath();
      }
      FileOpenener(path);
      frame.setTitle("Trident Text Editor - " + Paths.get(path).getFileName().toString());

      break;

    case "Save":
      if (path != System.getProperty("java.io.tmpdir") + "Unsaved file") {
        FileSaver(path);
        frame.setTitle("Trident Text Editor - " + Paths.get(path).getFileName().toString());
        break;
      }

    case "Save As":
      JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
      int r = j.showSaveDialog(null);

      if (r == JFileChooser.APPROVE_OPTION) {
        path = (j.getSelectedFile().getAbsolutePath());
        FileSaver(path);
      } else
        status.setText("File is not saved.");

      frame.setTitle("Trident Text Editor - " + Paths.get(path).getFileName().toString());
      break;

    case "Exit":
      status.setText("Exiting Trident...");
      try {
        Thread.sleep(100);
      } catch (InterruptedException ie) {
        status.setText("Could not exit. Use Task Manager to kill the process.");
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
