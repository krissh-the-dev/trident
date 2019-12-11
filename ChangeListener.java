import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class ChangeListener extends Trident implements DocumentListener {
  private static void warn() {
    if (!warned) {
      status2.setText("Unsaved");
      warned = true;
      frame.setTitle(frame.getTitle() + " - Unsaved");
      Undo.setEnabled(true);
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
