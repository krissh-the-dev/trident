
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class SettingsMenuListener implements ActionListener, ItemListener {
  protected void SettingsEditor() {
    try {
      JDialog jsonEditor = new JDialog(Trident.frame, "Style Editor");
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
            Trident.ErrorDialog("JSON_THREAD_IO", fIoException);
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
      Trident.ErrorDialog("UNKNOWN_JSON_ERR", unknownException);
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

  public void itemStateChanged(ItemEvent ie) {
    Trident.textarea.setLineWrap(Trident.wordWrap.isSelected());
    AutoSave.setEnabled(Trident.autoSave.isSelected());
  }

}
