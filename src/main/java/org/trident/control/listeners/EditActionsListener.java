package org.trident.control.listeners;/*
                                      *  org.trident.control.listeners.EditActionsListener.java
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

import java.awt.datatransfer.Clipboard;
import java.awt.Toolkit;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.DataFlavor;

/*
 * (Apache v2) Trident > org.trident.control.listeners.EditActionsListener
 * @author: Krishna Moorthy
 */

public class EditActionsListener extends Thread {

    public static boolean isRunning = true;

    @Override
    public void run() {
        Trident trident = Trident.getInstance();
        try {
            while (true) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                String contents = clipboard.getData(DataFlavor.stringFlavor).toString();
                try {
                    if (contents.equals("") || contents.equals(null)) {
                        trident.getPaste().setEnabled(false);
                        trident.getpPaste().setEnabled(false);
                        trident.getShowClipboard().setEnabled(false);
                        trident.getEraseClipboard().setEnabled(false);
                    } else {
                        trident.getpPaste().setEnabled(true);
                        trident.getPaste().setEnabled(true);
                        trident.getShowClipboard().setEnabled(true);
                        trident.getEraseClipboard().setEnabled(true);
                    }
                } catch (NullPointerException npe) { // TODO change this is very dangerus
                    trident.getPaste().setEnabled(false);
                    trident.getpPaste().setEnabled(false);
                    trident.getShowClipboard().setEnabled(false);
                    trident.getEraseClipboard().setEnabled(false);
                }

                try {
                    if (trident.getTextarea().getSelectedText().equals("")
                            || trident.getTextarea().getSelectedText().equals(null)) {
                        trident.getCopy().setEnabled(false);
                        trident.getpCopy().setEnabled(false);
                        trident.getCut().setEnabled(false);
                        trident.getpCut().setEnabled(false);
                    } else {
                        boolean value = false;
                        trident.getCopy().setEnabled(value);
                        trident.getpCopy().setEnabled(value);
                        trident.getCut().setEnabled(value);
                        trident.getpCut().setEnabled(value);
                    }
                } catch (NullPointerException npe) { // TODO change this is dangerus
                    boolean value = false;
                    trident.getCopy().setEnabled(value);
                    trident.getpCopy().setEnabled(value);
                    trident.getCut().setEnabled(value);
                    trident.getpCut().setEnabled(value);
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException inte) {
            TridentLogger.getInstance().error(this.getClass(), "EAL_INTERRUPTION: " + inte.getMessage());
        } catch (java.io.IOException | IllegalStateException | UnsupportedFlavorException ise) {
            // We don't wanna throw error just while checking [Listening in this context]
        }
    }
}
