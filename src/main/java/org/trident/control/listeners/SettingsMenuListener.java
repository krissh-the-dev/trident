package org.trident.control.listeners;
/*
 *  org.trident.control.listeners.SettingsMenuListener.java
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

import org.trident.Trident;
import org.trident.control.listeners.AutoSave;
import org.trident.model.Configurations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/*
 * (GPL v3) Trident > org.trident.control.listeners.SettingsMenuListener
 * @author: Krishna Moorthy
 */

public class SettingsMenuListener implements ActionListener, ItemListener {
  /*
   * Controls the actions of Settings Menu
   */

  public static boolean isRunning = true;

  public void actionPerformed(ActionEvent e) {
    /*
     * Controls actions of Settings menu items (Style editor and org.trident.model.Configurations
     * options)
     */
    switch (e.getActionCommand()) {
    // Note: For JMenuCheckBoxes refer itemStateChanged()
    case "Style Editor":
      Configurations.getInstance().showEditor();
      break;
    case "org.trident.model.Configurations":
      Configurations.getInstance().showUI();
      break;
    }
  }

  public void itemStateChanged(ItemEvent ie) {
    /*
     * Controls actions of Settings menu items (Auto save and Word Wrap options)
     */
    Trident.getInstance().getTextarea().setLineWrap(Trident.getInstance().getWordWrap().isSelected());
    //AutoSave.setEnable(Trident.getInstance().getAutoSave().isSelected()); TODO look the auto save problem
  }

}
