package org.trident.control.listeners;/*
 *  org.trident.control.listeners.EditActionsListener.java
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
import org.trident.util.TridentLogger;

import java.awt.datatransfer.Clipboard;
import java.awt.Toolkit;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.datatransfer.DataFlavor;

/*
 * (GPL v3) Trident > org.trident.control.listeners.EditActionsListener
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
                } catch (NullPointerException npe) { //TODO change this is very dangerus
                    trident.getPaste().setEnabled(false);
                    trident.getpPaste().setEnabled(false);
                    trident.getShowClipboard().setEnabled(false);
                    trident.getEraseClipboard().setEnabled(false);
                }

                try {
                    if (trident.getTextarea().getSelectedText().equals("") || trident.getTextarea().getSelectedText().equals(null)) {
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
                } catch (NullPointerException npe) { //TODO change this is dangerus
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
