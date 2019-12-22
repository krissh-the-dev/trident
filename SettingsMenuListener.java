
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

class SettingsMenuListener implements ActionListener, ItemListener {
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      // * Note: For JMenuCheckBoxes refer itemStateChanged()
    case "Style Editor":
      Configurations.showEditor();
      break;
    case "Configurations":
      Configurations.showUI();
      break;
    }
  }

  public void itemStateChanged(ItemEvent ie) {
    Trident.textarea.setLineWrap(Trident.wordWrap.isSelected());
    AutoSave.setEnabled(Trident.autoSave.isSelected());
  }

}
