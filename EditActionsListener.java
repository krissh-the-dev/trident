/*
 *  EditActionsListener.java
 *  (c) Copyright, 2020 - 2021 Krishna Moorthy
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

import java.awt.datatransfer.Clipboard;
import java.awt.Toolkit;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.DataFlavor;

/*
 * (GPL v3) Trident > EditActionsListener
 * @author: Krishna Moorthy 
 */

class EditActionsListener extends Thread {
  /*
   * Influences the responsiveness of the Edit Menu items such as Copy, Paste etc.
   * 
   * Runs a thread in the background forever. Starts when Edit Menu is
   * initialized.
   */
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
