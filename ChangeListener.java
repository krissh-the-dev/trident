/*
 *  ChangeListener.java
 *  (c) Copyright, 2020 - 2021 Krishna Moorthy
 *  akrishnamoorthy007@gmail.com | github.com/KrishnaMoorthy12
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

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*
 * (GPL v3) Trident > ChangeListener
 * @author: Krishna Moorthy
 */

class ChangeListener implements DocumentListener {
  /*
   * Listens to the changes in the document [textarea]
   */

  private static void warn() {
    /*
     * Influences the behaviour of title bar and status bar
     * 
     * adds 'Unsaved' warning on title bar and status bar warning area [area 2]
     */
    if (!Trident.warned) {
      Trident.status2.setText("Unsaved");
      Trident.warned = true;
      Trident.frame.setTitle(Trident.frame.getTitle()  + " - Unsaved");
    }
    Trident.Undo.setEnabled(true);
    Toolbar.undoButton.setEnabled(true);
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
