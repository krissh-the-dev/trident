
import java.awt.datatransfer.Clipboard;
import java.awt.Toolkit;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.DataFlavor;

class EditActionsListener extends Thread {
  @Override
  public void run() {
    try {
      while (true) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String contents = clipboard.getData(DataFlavor.stringFlavor).toString();
        try {
          if (contents.equals("")) {
            Trident.Paste.setEnabled(false);
            Trident.pPaste.setEnabled(false);
            Trident.ShowClipboard.setEnabled(false);
            Trident.EraseClipboard.setEnabled(false);
          } else {
            Trident.pPaste.setEnabled(true);
            Trident.Paste.setEnabled(true);
            Trident.ShowClipboard.setEnabled(true);
            Trident.EraseClipboard.setEnabled(true);
          }
        } catch (NullPointerException npe) {
          Trident.Paste.setEnabled(false);
          Trident.pPaste.setEnabled(false);
          Trident.ShowClipboard.setEnabled(false);
          Trident.EraseClipboard.setEnabled(false);
        }

        try {
          if (Trident.textarea.getSelectedText().equals("")) {
            Trident.Copy.setEnabled(false);
            Trident.pCopy.setEnabled(false);
            Trident.Cut.setEnabled(false);
            Trident.pCut.setEnabled(false);
          } else {
            Trident.Copy.setEnabled(true);
            Trident.pCopy.setEnabled(true);
            Trident.Cut.setEnabled(true);
            Trident.pCut.setEnabled(true);
          }
        } catch (NullPointerException npe) {
          Trident.Copy.setEnabled(false);
          Trident.pCopy.setEnabled(false);
          Trident.Cut.setEnabled(false);
          Trident.pCut.setEnabled(false);
        }
        Thread.sleep(100);
      }
    } catch (InterruptedException inte) {
      Trident.ErrorDialog("EAL_INTERRUPTION", inte);
    } catch (UnsupportedFlavorException ufe) {
      // We don't wanna throw error just while checking [Listening in this context]
    } catch (Exception some) {
      Trident.ErrorDialog("UNKNOWN_ERR_EAL", some);
    }
  }
}
