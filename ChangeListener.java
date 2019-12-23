import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class ChangeListener implements DocumentListener {
  private static void warn() {
    if (!Trident.warned) {
      Trident.status2.setText("Unsaved");
      Trident.warned = true;
      Trident.frame.setTitle(Trident.frame.getTitle() + " - Unsaved");
    }
    Trident.Undo.setEnabled(true);
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
