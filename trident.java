import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.io.*;
import javax.swing.filechooser.*;

class Trident {
  protected static JTextArea editor;
  public static JLabel status;
  protected static JFrame frame;
  public static String path = "temp/tempFile";

  public static void main(String[] args) {
    try {
      try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
      } catch (Exception themeError) {
        System.err.println("Error theming the application.");
      }
      frame = new JFrame();
      MenuActionListener mml = new MenuActionListener();
      frame.setTitle("Trident Text Editor - " + path);
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

          JMenuItem CloseFile = new JMenuItem("Close");
          fileMenu.add(CloseFile);
          CloseFile.addActionListener(mml);
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

        }

        mb.add(fileMenu);
        mb.add(editMenu);
        mb.add(formatMenu);
        mb.add(about);
      }

      editor = new JTextArea();
      JScrollPane scrollBar = new JScrollPane(editor, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
          JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

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
      for (int inc = 1; file.exists(); inc++) {
        path += inc;
      }

      // there is some problem

      if (file.createNewFile())
        status.setText("Working with temporary file.");
      else
        status.setText("Unable to create temporary file. Save the file to avoid loss of progress.");

    } catch (Exception e) {
      System.err.println("Unexpected crash...");
      e.printStackTrace();
      System.exit(0);
    }
  }
}

class MenuActionListener extends Trident implements ActionListener, MenuListener {
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
    case "New":
      editor.setText("");
      break;
    case "Open":
      path = "temp/tempFile";
      JFileChooser bb = new JFileChooser(FileSystemView.getFileSystemView());
      int bbd = bb.showOpenDialog(null);

      if (bbd == JFileChooser.APPROVE_OPTION) {
        path = bb.getSelectedFile().getAbsolutePath();
      }

      try {
        File OpenedFile = new File(path);
        FileReader fr = new FileReader(OpenedFile);
        BufferedReader br = new BufferedReader(fr);
        String contents = "";
        for (String line = br.readLine(); line != null; line = br.readLine()) {
          contents += line + System.lineSeparator();
        }
        frame.setTitle("Trident Text Editor - " + path);
        editor.setText(contents);
        contents = null;
        fr.close();
        br.close();
        System.gc();
      } catch (IOException ioe) {
        status.setText("Error opening file.");
      }
      break;

    case "About Trident":
      JDialog aboutDialog = new JDialog(frame, "Trident v1.0");
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
