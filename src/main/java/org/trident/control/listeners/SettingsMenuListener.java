package org.trident.control.listeners;
/*
 *  org.trident.control.listeners.SettingsMenuListener.java
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

import org.trident.Trident;
import org.trident.control.listeners.AutoSave;
import org.trident.model.Configurations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/*
 * (Apache v2) Trident > org.trident.control.listeners.SettingsMenuListener
 * @author: Krishna Moorthy
 */

public class SettingsMenuListener implements ActionListener, ItemListener {
  /*
   * Controls the actions of Settings Menu
   */

  public static boolean isRunning = true;

  public void actionPerformed(ActionEvent e) {
    /*
     * Controls actions of Settings menu items (Style editor and
     * org.trident.model.Configurations options)
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
    // AutoSave.setEnable(Trident.getInstance().getAutoSave().isSelected()); TODO
    // look the auto save problem
  }

}
