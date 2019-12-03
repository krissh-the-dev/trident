import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

/*
class MenuListeners implements ItemListener {
  @Override
  public void actionPerformed(ItemEvent ie) {
    // switch (ie.getSource().getName()) {

    // }
  }
}
*/

class Trident {
  public static void main(String[] args) {
    try {
      try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
      } catch (Exception themeError) {
        System.err.println("Error theming the application.");
      }
      final JFrame frame = new JFrame();
      final String AppName = "Trident Text Editor";
      frame.setTitle(AppName);
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
          JMenuItem newFile = new JMenuItem("New");
          fileMenu.add(newFile);
          JMenuItem OpenFile = new JMenuItem("Open");
          fileMenu.add(OpenFile);
          JMenuItem SaveFile = new JMenuItem("Save");
          fileMenu.add(SaveFile);
          JMenuItem CloseFile = new JMenuItem("Close");
          fileMenu.add(CloseFile);
        }

        JMenu editMenu = new JMenu("Edit");
        {
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
          JCheckBox wwrap = new JCheckBox("Word wrap");
          formatMenu.add(wwrap);
          JMenuItem fontOptions = new JMenuItem("Fonts");
          formatMenu.add(fontOptions);
          JMenuItem themes = new JMenuItem("Themes");
          formatMenu.add(themes);
          JMenuItem settings = new JMenuItem("Settings");
          formatMenu.add(settings);
        }

        JMenu about = new JMenu("About");

        mb.add(fileMenu);
        mb.add(editMenu);
        mb.add(formatMenu);
        mb.add(about);
      }

      JTextArea editor = new JTextArea();
      JScrollPane scrollBar = new JScrollPane(editor, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
          JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

      // WILL BE MADE CONFIGURABLE
      editor.setLineWrap(false);
      editor.setFont(new Font("Consolas", 12, 12));
      editor.setTabSize(4);

      JPanel statusBar = new JPanel();
      JLabel status = new JLabel("Ready");
      statusBar.setSize(30, 2500);
      statusBar.setLayout(new GridLayout(1, 4, 2, 2));
      statusBar.add(status);

      frame.getContentPane().add(mb, BorderLayout.NORTH);
      frame.getContentPane().add(scrollBar, BorderLayout.CENTER);
      frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
      frame.setVisible(true);

      /* Working area */

      // File file = new File(filename);

    } catch (Exception e) {
      System.err.println("Unexpected crash...");
      System.exit(0);
    }
  }
}
