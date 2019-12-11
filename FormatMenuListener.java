//*AWT ELEMENTS

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// * CLIPBOARD ELEMENTS AND UNDO HANDLERS
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.lang.ProcessBuilder.Redirect;

// * IO ELEMENTS
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;

// * SWING ELEMENTS
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.text.BadLocationException;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

class FormatMenuListener extends FileMenuListener implements ActionListener {
  protected void SettingsEditor() {
    try {
      JDialog jsonEditor = new JDialog(frame, "Style Editor");
      jsonEditor.setSize(450, 350);
      jsonEditor.setIconImage((new ImageIcon("raw/trident.png")).getImage());
      JPanel TextViewer = new JPanel();
      File jsonFile = new File("configurations.json");
      FileReader fr = new FileReader(jsonFile);
      BufferedReader br = new BufferedReader(fr);
      String jsonContents = "";
      for (String line = br.readLine(); line != null; line = br.readLine()) {
        jsonContents += line + System.lineSeparator();
      }
      fr.close();
      br.close();
      JTextArea jsonViewer = new JTextArea(jsonContents);
      JScrollPane jsonScrollController = new JScrollPane(jsonViewer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
          JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      jsonScrollController.setBorder(new EmptyBorder(-1, 0, -1, 0));
      TextViewer.setLayout(new GridLayout(1, 1, 1, 1));
      jsonEditor.setLayout(new BorderLayout());
      TextViewer.add(jsonScrollController);
      jsonEditor.getContentPane().add(TextViewer, BorderLayout.CENTER);
      jsonViewer.getDocument().addDocumentListener(new DocumentListener() {
        private void saveSettings() {
          try {
            String jsonContents = jsonViewer.getText();
            File jsonFile = new File("configurations.json");
            FileWriter fileWritter = new FileWriter(jsonFile, false);
            BufferedWriter bw = new BufferedWriter(fileWritter);
            bw.write(jsonContents);
            bw.close();
          } catch (IOException fIoException) {
            ErrorDialog("JSON_THREAD_IO", fIoException);
          }
        }

        public void changedUpdate(DocumentEvent e) {
          saveSettings();
        }

        public void removeUpdate(DocumentEvent e) {
          saveSettings();
        }

        public void insertUpdate(DocumentEvent e) {
          saveSettings();
        }
      });
      jsonEditor.setVisible(true);
    } catch (Exception unknownException) {
      ErrorDialog("UNKNOWN_JSON_ERR", unknownException);
    }
  }

  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
    case "Fonts":
      SettingsEditor();
      break;
    case "Themes":
      SettingsEditor();
      break;
    case "Settings":
      SettingsEditor();
      break;
    }
  }
}
