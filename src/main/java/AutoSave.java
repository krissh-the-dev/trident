/*
 *  AutoSave.java
 *  (c) Copyright, 2020 - 2021 Krishna Moorthy
 *  akrishnamoorthy007@gmail.com | github.com/KrishnaMoorthy12
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*
 * Auto Save v1.4
 * (Apache v2) Trident > AutoSave
 * @author: Krishna Moorthy
 * Since: Trident v1.2.1
 */

class AutoSave implements DocumentListener {
  private static boolean Enabled = true;

  public static boolean isRunning = true;

  private static File file;

  public static void setEnabled(Boolean enable) {
    /*
     * Toggles auto save feature
     * 
     * @param: boolean
     * 
     * enables auto save on true/ disables on false
     */
    Enabled = enable;
    isRunning = Enabled;
    if (Enabled == false)
      deleteSaved();
    else
      saveNow();
  }

  public static void deleteSaved() {
    /*
     * Deletes the autosaved copy of current file
     */
    file.delete();
  }

  private static void saveNow() {
    /*
     * Saves an autosaved copy of the current file with latest changes on the
     * working directory
     * 
     * Part of autosave feature
     */
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
