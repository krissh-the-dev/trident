import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MenuListeners implements ItemListener {
  @Override
  public void actionPerformed(ItemEvent ie) {
    // switch (ie.getSource().getName()) {

    // }
  }
}

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
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());

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

        JMenu about = new JMenu("About");

        mb.add(fileMenu);
        mb.add(editMenu);
        mb.add(about);
      }

      JTextArea editor = new JTextArea();
      JScrollPane scrollBar = new JScrollPane(editor);

      editor.setLineWrap(true);
      editor.setFont(new Font("Consolas", 12, 12));
      editor.setTabSize(4);
      frame.getContentPane().add(mb, BorderLayout.NORTH);
      frame.getContentPane().add(scrollBar, BorderLayout.EAST); // Not visible
      frame.getContentPane().add(editor, BorderLayout.CENTER);
      frame.setVisible(true);
    } catch (Exception e) {
      System.err.println("Unexpected crash...");
      System.exit(0);
    }
  }
}
