package org.trident.control.listeners;/*
                                      *  org.trident.control.listeners.AutoSave.java
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
 * (Apache v2) Trident > org.trident.control.listeners.AutoSave
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
        if (!this.enable) {
            deleteSaved();
        } else {
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
