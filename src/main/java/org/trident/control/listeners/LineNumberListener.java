package org.trident.control.listeners;
/*
 *  org.trident.control.listeners.LineNumberListener.java
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
import org.trident.util.TridentLogger;

import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.text.BadLocationException;

/*
 * Status Bar Line number Panel Controller
 * (Apache v2) Trident > Status Bar > org.trident.control.listeners.LineNumberListener
 * @author: Krishna Moorthy
 */

public class LineNumberListener implements CaretListener {
    /*
     * Controls the Line number and Column Number display areas in status bar and
     * the side line number panel
     */

    public static boolean isRunning = true;

    @Override
    public void caretUpdate(CaretEvent ce) {

        // TODO : Add code to control line number panel
        try {
            int offset = Trident.getInstance().getTextarea().getCaretPosition();
            int line = Trident.getInstance().getTextarea().getLineOfOffset(offset);
            int col = offset - Trident.getInstance().getTextarea().getLineStartOffset(line);

            Trident.getInstance().getStatus4().setText("Line: " + (line + 1) + " Col: " + (col + 1));

        } catch (BadLocationException badexp) {
            TridentLogger.getInstance().error(this.getClass(), "CARET_LOCATION_ERR: " + badexp);
            Trident.getInstance().getStatus4().setText("Aw snap!");
        }
    }
}
