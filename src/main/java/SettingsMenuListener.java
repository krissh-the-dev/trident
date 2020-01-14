
/*
 *  SettingsMenuListener.java
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/*
 * (GPL v3) Trident > SettingsMenuListener
 * @author: Krishna Moorthy
 */

class SettingsMenuListener implements ActionListener, ItemListener {
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
    // Note: For JMenuCheckBoxes refer itemStateChanged()
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
