import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

class AutoSaver implements DocumentListener {
  private static void saveNow() {
    try {
      String extension = "";
      int i = Trident.path.lastIndexOf('.');
      if (i > 0)
        extension = Trident.path.substring(i + 1);
      File file = new File(Trident.path + "-autoSaved." + extension);
      file.createNewFile();
      FileWriter fw = new FileWriter(file, false);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(Trident.textarea.getText());
      file.deleteOnExit();

      bw.close();
      fw.close();
    } catch (IOException ioe) {
      Trident.ErrorDialog("AUTO_SAVE_IO", ioe);
    } catch (SecurityException se) {
      Trident.ErrorDialog("PERMISSION_ERR", se);
    } catch (Exception exp) {
      Trident.ErrorDialog("AUTO_SAVE_ERR", exp);
    }
  }

  @Override
  public void changedUpdate(DocumentEvent e) {
    saveNow();
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    saveNow();
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    saveNow();
  }

}
