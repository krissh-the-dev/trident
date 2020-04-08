package org.trident.control.listeners;/*
 *  org.trident.control.listeners.AutoSave.java
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

import org.slf4j.Logger;
import org.trident.Trident;
import org.trident.util.TridentLogger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*
 * Auto Save v1.4
 * (GPL v3) Trident > org.trident.control.listeners.AutoSave
 * @author: Krishna Moorthy
 * Since: Trident v1.2.1
 */

public class AutoSave implements DocumentListener {

    public static final Class TAG = AutoSave.class;

    private boolean enable = true;

    public boolean isRunning = true;

    private File file;

    public void setEnable(Boolean enable) {

        this.enable = enable;
        isRunning = this.enable;
        if (!this.enable){
            deleteSaved();
        }else{
            saveNow();
        }
    }

    public void deleteSaved() {
        file.delete();
    }

    private void saveNow() {

        try {
            String path = Trident.getInstance().getPath();
            String extension = "";
            int i = path.lastIndexOf('.');
            if (i > 0)
                extension = Trident.getInstance().getPath().substring(i + 1);
            if (path.equals("New File"))
                file = new File(System.getProperty("user.home") + "/Unsaved Document.txt");
            else
                file = new File(path + "-autoSaved." + extension);
            file.createNewFile();
            FileWriter fw = new FileWriter(file, false);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Trident.getInstance().getTextarea().getText());
            file.deleteOnExit();

            bw.close();
            fw.close();
        } catch (Exception exp) {
            TridentLogger.getInstance().debug(TAG, "AUTO_SAVE_ERR: " + exp);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (enable)
            saveNow();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (enable)
            saveNow();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (enable)
            saveNow();
    }
}
