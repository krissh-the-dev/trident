/*
 *  EditActionsListener.java
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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

/*
 * (Apache v2) Trident > EditActionsListener
 * @author: Krishna Moorthy 
 */

class EditActionsListener extends Thread {
  /*
   * Influences the responsiveness of the Edit Menu items such as Copy, Paste etc.
   * 
   * Runs a thread in the background forever. Starts when Edit Menu is
   * initialized.
   */

  public static boolean isRunning = true;

  @Override
  public void run() {
    try {
      while (true) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String contents = clipboard.getData(DataFlavor.stringFlavor).toString();
        try {
          if (contents.equals("") || contents.equals(null)) {
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
          if (Trident.textarea.getSelectedText().equals("") || Trident.textarea.getSelectedText().equals(null)) {
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
    } catch (java.io.IOException | IllegalStateException | UnsupportedFlavorException ise) {
      // We don't wanna throw error just while checking [Listening in this context]
    }
  }
}
