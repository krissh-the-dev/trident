package org.trident.control.listeners;
/*
 *  org.trident.control.listeners.ChangeListener.java
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
import org.trident.view.Toolbar;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*
 * (Apache v2) Trident > org.trident.control.listeners.ChangeListener
 * @author: Krishna Moorthy
 */

public class ChangeListener implements DocumentListener {
    /*
     * Listens to the changes in the document [textarea]
     */

    public static boolean isRunning = true;

    private static void warn() {

        if (!Trident.getInstance().getWarned()) {
            Trident.getInstance().getStatus2().setText("Unsaved");
            Trident.getInstance().setWarned(true);
            Trident.getInstance().getFrame().setTitle(Trident.getInstance().getFrame().getTitle() + " - Unsaved");
        }
        Trident.getInstance().getUndo().setEnabled(true);
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
