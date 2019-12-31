/*
 *  AutoSave.java
 *  (c) Copyright, 2019 - 2020 Krishna Moorthy
 *  akrishnamoorthy007@gmail.com | github.io/KrishnaMoorthy12
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*
 * Auto Save v1.4
 * (GPL v3) Trident > AutoSave
 * @author: Krishna Moorthy
 * Since: Trident v1.2.1
 */

class AutoSave implements DocumentListener {
  private static boolean Enabled = true;

  private static File file;

  public static void setEnabled(Boolean enable) {
    Enabled = enable;
    if (Enabled == false)
      deleteSaved();
    else
      saveNow();
  }

  public static void deleteSaved() {
    file.delete();
  }

  private static void saveNow() {
    try {
      String extension = "";
      int i = Trident.path.lastIndexOf('.');
      if (i > 0)
        extension = Trident.path.substring(i + 1);
      if (Trident.path.equals("New File"))
        file = new File(System.getProperty("user.home") + "/Unsaved Document.txt");
      else
        file = new File(Trident.path + "-autoSaved." + extension);
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
    if (Enabled)
      saveNow();
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    if (Enabled)
      saveNow();
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    if (Enabled)
      saveNow();
  }
}
