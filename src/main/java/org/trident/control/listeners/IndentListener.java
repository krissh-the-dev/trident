package org.trident.control.listeners;/*
                                      *  org.trident.control.listeners.IndentListener.java
                                      *  (c) Copyright, 2020 - 2021 Krishna Moorthy
                                      *  akrishnamoorthy007@gmail.com | github.com/KrishnaMoorthy12
                                      *
                                      *  Modified code from Stack Overflow
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

import org.trident.util.TridentLogger;

import javax.swing.text.*;

public class IndentListener extends DocumentFilter {
    /*
     * Automatic indent matching utility Maintains the same indent level as previous
     * line whenever a new line is inserted (Apache v2) Trident >
     * org.trident.control.listeners.IndentListener
     *
     * @author: Krishna Moorthy
     */

    public static boolean isRunning = true;

    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) {
        /*
         * Inserts the calculated amount of white spaces into the document -function
         * call is dependent on Event triggered
         */
        try {
            if ("\n".equals(str))
                str = addWhiteSpace(fb.getDocument(), offs);
            super.insertString(fb, offs, str, a);
        } catch (BadLocationException be) {
            TridentLogger.getInstance().error(this.getClass(), "AUTO-INDENT_ERR: " + be);
        }
    }

    public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) {
        /*
         * Inserts the calculated amount of white spaces into the document -function
         * call is dependent on Event triggered
         */
        try {
            if ("\n".equals(str))
                str = addWhiteSpace(fb.getDocument(), offs);

            super.replace(fb, offs, length, str, a);
        } catch (BadLocationException be) {
            TridentLogger.getInstance().error(this.getClass(), "AUTO-INDENT_ERR: " + be);
        }
    }

    private String addWhiteSpace(Document doc, int offset) {
        /*
         * Calculates the indent level for current line and @returns the string to be
         * inserted
         */
        try {
            StringBuilder whiteSpace = new StringBuilder("\n");
            Element rootElement = doc.getDefaultRootElement();
            int line = rootElement.getElementIndex(offset);
            int i = rootElement.getElement(line).getStartOffset();

            while (true) {
                String temp = doc.getText(i, 1);

                if (temp.equals(" ") || temp.equals("\t")) {
                    whiteSpace.append(temp);
                    i++;
                } else
                    break;
            }
            return whiteSpace.toString();
        } catch (Exception be) {
            TridentLogger.getInstance().error(this.getClass(), "AUTO-INDENT_ERR: " + be);
            return "";
        }
    }
}
