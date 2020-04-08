package org.trident.control.listeners;
/*
 *  org.trident.control.listeners.FileMenuListener.java
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
import org.trident.exception.UnsupportedFileException;
import org.trident.exception.UnsupportedOperatingSystemException;
import org.trident.model.RecentsTracker;
import org.trident.model.TridentCompiler;
import org.trident.util.FileTypeParser;
import org.trident.util.TridentLogger;
import org.trident.view.Toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Paths;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoManager;

/*
 * (Apache v2) Trident > org.trident.control.listeners.FileMenuListener
 * @author: Krishna Moorthy
 */
public class FileMenuListener implements ActionListener {

    public void FileOpener() {

        try {
            // text files include plaint text, markdown, maniests, encrypt files etc.
            FileNameExtensionFilter textFiles = new FileNameExtensionFilter("Text Files (*.txt, *.mf, *.md, *.rtf)",
                    "txt", "mf", "md", "rtf");
            // Source files include major program source code files formats
            FileNameExtensionFilter SourceFiles = new FileNameExtensionFilter(
                    "Source Files (*.py, *.java, *.c, *.cpp, *.h, *.kt)", "py", "java", "c", "cpp", "h", "kt");

            // Web files contain files related to web pages and web apps
            FileNameExtensionFilter WebFiles = new FileNameExtensionFilter(
                    "Web Files (*.html, *.htm, *.mhtml, *.css, *.less,*.js, *.php)", "html", "htm", "mhtml", "css",
                    "less", "js", "php");
            // General other categories like object notations etc.
            FileNameExtensionFilter OtherFiles = new FileNameExtensionFilter("Scripts (*.json, *.config, *.bat, *.sh)",
                    "json", "config", "bat", "sh");
            JFileChooser openDialog = new JFileChooser(FileSystemView.getFileSystemView());
            openDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
            openDialog.setFileFilter(textFiles);
            openDialog.setFileFilter(WebFiles);
            openDialog.setFileFilter(OtherFiles);
            openDialog.setFileFilter(SourceFiles);
            openDialog.setAcceptAllFileFilterUsed(true);
            int command = openDialog.showOpenDialog(Trident.getInstance().getFrame());

            if (command == JFileChooser.APPROVE_OPTION) {
                Trident.getInstance().setPath(openDialog.getSelectedFile().getAbsolutePath());
                openFile();
            } else if (command == JFileChooser.CANCEL_OPTION) {
                Trident.getInstance().getStatus1().setText("Operation cancelled by the user.");
                return;
            }
        } catch (Exception enr) {
            TridentLogger.getInstance().error(this.getClass(), "FILE_OPENER_ERR: " + enr.getLocalizedMessage());
            Trident.getInstance().getStatus1().setText("There was an error opening the file.");
        }
    }

    public void openFile() {

        try {
            // AutoSave.deleteSaved(); TODO look AutoSave
            File OpenedFile = new File(Trident.getInstance().getPath());
            FileReader fr = new FileReader(OpenedFile);
            BufferedReader br = new BufferedReader(fr);
            String contents = "";
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                contents += line + System.lineSeparator();
            }
            Trident.getInstance().getTextarea().setText(contents);
            Trident.getInstance().getStatus1().setText("Editing existing file.");
            Trident.getInstance().getStatus2().setText("Saved");
            Trident.getInstance().getStatus3().setText(
                    FileTypeParser.getType(Paths.get(Trident.getInstance().getPath()).getFileName().toString()));
            Trident.getInstance().setWarned(false);

            Trident.getInstance().setUndoManager(new UndoManager());
            Trident.getInstance().getTextarea().getDocument()
                    .addUndoableEditListener(Trident.getInstance().getUndoManager());

            Trident.getInstance().getUndo().setEnabled(false);
            Toolbar.undoButton.setEnabled(false);
            Trident.getInstance().getRedo().setEnabled(false);
            Toolbar.redoButton.setEnabled(false);

            Trident.getInstance().getFrame().setTitle(
                    "Trident Text Editor - " + Paths.get(Trident.getInstance().getPath()).getFileName().toString());

            contents = null;
            RecentsTracker.addRecord(Trident.getInstance().getPath());
            fr.close();
            br.close();
            System.gc();
        } catch (Exception ioe) {
            TridentLogger.getInstance().error(this.getClass(), "FILE_OPEN_ERR: " + ioe);
            Trident.getInstance().getStatus1().setText("Could not open the specified file.");
        }
    }

    @Deprecated
    public void FileSaver(String filepath) {
        /*
         * Saves the changes made to the file in 'filepath'
         *
         * @param: path of the file to save
         */
        try {
            if (!filepath.equals("New File")) {
                File f1 = new File(filepath);
                if (!f1.exists()) {
                    f1.createNewFile();
                }
                String contents = Trident.getInstance().getTextarea().getText();
                FileWriter fileWritter = new FileWriter(f1, false);
                BufferedWriter bw = new BufferedWriter(fileWritter);
                bw.write(contents);
                bw.close();
                Trident.getInstance().setWarned(false);
                Trident.getInstance().getFrame()
                        .setTitle("Trident Text Editor - " + Paths.get(filepath).getFileName().toString());
                Trident.getInstance().getStatus1().setText("File saved successfully.");
                Trident.getInstance().getStatus2().setText("Saved");
                Trident.getInstance().getStatus3()
                        .setText(FileTypeParser.getType(Paths.get(filepath).getFileName().toString()));
            } else
                FileSaveAs();
        } catch (Exception ioe) {
            TridentLogger.getInstance().error(this.getClass(), "FILE_SAVE_IO:" + ioe.getLocalizedMessage());
            Trident.getInstance().getStatus1().setText("Error saving the file.");
        }
    }

    public void saveFile() {
        /*
         * Saves the changes made to the file in 'Trident.path' i.e current file
         */
        try {
            if (!Trident.getInstance().getPath().equals("New File")) {
                File f1 = new File(Trident.getInstance().getPath());
                if (!f1.exists()) {
                    f1.createNewFile();
                }
                String contents = Trident.getInstance().getTextarea().getText();
                FileWriter fileWritter = new FileWriter(f1, false);
                BufferedWriter bw = new BufferedWriter(fileWritter);
                bw.write(contents);
                bw.close();
                Trident.getInstance().setWarned(false);
                Trident.getInstance().getFrame().setTitle(
                        "Trident Text Editor - " + Paths.get(Trident.getInstance().getPath()).getFileName().toString());
                Trident.getInstance().getStatus1().setText("File saved successfully.");
                Trident.getInstance().getStatus2().setText("Saved");
                Trident.getInstance().getStatus3().setText(
                        FileTypeParser.getType(Paths.get(Trident.getInstance().getPath()).getFileName().toString()));
            } else
                FileSaveAs();
        } catch (Exception ioe) {
            TridentLogger.getInstance().error(this.getClass(), "FILE_SAVE_IO: " + ioe);
            Trident.getInstance().getStatus1().setText("Error saving the file.");
        }
    }

    public void FileSaveAs() {
        /*
         * Opens up JFileChooser Save As dialog to choose a path to save the current
         * file
         */
        JFileChooser saveAsDialog = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int command = saveAsDialog.showSaveDialog(Trident.getInstance().getFrame());

        if (command == JFileChooser.APPROVE_OPTION) {
            Trident.getInstance().setPath(saveAsDialog.getSelectedFile().getAbsolutePath());
            saveFile();
            RecentsTracker.addRecord(saveAsDialog.getSelectedFile().getAbsolutePath());
        } else if (command == JFileChooser.CANCEL_OPTION) {
            Trident.getInstance().getStatus1().setText("File is not saved.");
        }
    }

    public int warningDialog() {
        /*
         * Displays a warning Dialog box when user attempts to leave the current file
         * without saving any recent changes, irrespective of autosaved copies
         */
        int opt = JOptionPane.showConfirmDialog(Trident.getInstance().getFrame(),
                "There are some unsaved changes in the file." + System.lineSeparator()
                        + "Do you want to save the changes and continue?",
                "Warning: Unsaved changes", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
                (new ImageIcon("raw/warning.png")));
        if (opt == JOptionPane.YES_OPTION) {
            saveFile();
        }
        return opt;
    }

    public boolean newFile() {
        /*
         * Creates a new temporary file in arbitrary location. Refreshes and empties all
         * the older contents
         */
        if (Trident.getInstance().getWarned()) {
            int opt = warningDialog();
            if (opt == JOptionPane.CANCEL_OPTION) {
                Trident.getInstance().getStatus1().setText("Ready.");
                return false;
            }
        }
        Trident.getInstance().setPath("New File"); // TODO Why??/
        Trident.getInstance().getTextarea().setText("");
        Trident.getInstance().getStatus1().setText("Ready.");
        Trident.getInstance().getStatus2().setText("Unsaved");
        Trident.getInstance().getStatus3().setText("Plain File");
        Trident.getInstance().getFrame().setTitle("Trident Text Editor - New File");
        // Trident.getInstance().setPath(false); //TODO I lose this
        Trident.getInstance().getUndo().setEnabled(false);
        Trident.getInstance().getRedo().setEnabled(false);
        Toolbar.undoButton.setEnabled(false);
        Toolbar.redoButton.setEnabled(false);
        Trident.getInstance().setUndoManager(new UndoManager());
        Trident.getInstance().getTextarea().getDocument()
                .addUndoableEditListener(Trident.getInstance().getUndoManager());
        return true;
    }

    protected void boil(String choice)
            throws UnsupportedFileException, UnsupportedOperatingSystemException, IOException, InterruptedException {

        String boiler = null;
        switch (choice) {
            case "C Source File":
                boiler = "boilers/c.c";
                break;

            case "C++ Source File":
                boiler = "boilers/c++.cpp";
                break;

            case "Python Source File":
                boiler = "boilers/python.py";
                break;

            case "Java Source File":
                boiler = "boilers/java.java";
                break;

            case "HTML File":
                boiler = "boilers/html5.html";
                break;

            case "Bootstrap":
                boiler = "boilers/bootstrap.html";
                break;

            case "Open PowerBoil":
                TridentCompiler.execute("boilers/powerboil/powerboil.py");
                return;

            default:
                throw new UnsupportedFileException(Trident.getInstance().getPath());
        }
        try {
            if (newFile()) {
                BufferedReader be = new BufferedReader(new FileReader(new File(boiler)));
                String content = be.readLine();
                String contents = "";
                while (content != null) {
                    contents += content + System.lineSeparator();
                    content = be.readLine();
                }
                Trident.getInstance().getTextarea().setText(contents);
                Trident.getInstance().getUndoManager().discardAllEdits();
                Trident.getInstance().getUndo().setEnabled(false);
                Toolbar.undoButton.setEnabled(false);
                Trident.getInstance().getStatus3().setText(choice);
                be.close();
            } else {
                Trident.getInstance().getStatus1().setText("Operation cancelled by the user.");
            }
        } catch (FileNotFoundException fnf) {
            TridentLogger.getInstance().error(this.getClass(), "BOILER_NOT_FOUNT: " + fnf);
        } catch (Exception e) {
            throw e;
        }
    }

    public void actionPerformed(ActionEvent e) {
        /*
         * Controls the actions of File Menu
         */
        try {
            switch (e.getActionCommand()) {
                case "New":
                    newFile();
                    break;

                case "New Window":
                    // new Trident("New File");
                    ProcessBuilder pb = new ProcessBuilder("Trident.bat");
                    pb.start();
                    break;

                case "Open":
                    if (Trident.getInstance().getWarned()) {
                        int opt = warningDialog();
                        if (opt == JOptionPane.CANCEL_OPTION) {
                            Trident.getInstance().getStatus1().setText("Ready.");
                            break;
                        }
                    }
                    FileOpener();
                    break;

                case "Exit":
                    Trident.getInstance().getStatus1().setText("Exiting Trident...");
                    if (Trident.getInstance().getWarned()) {
                        int opt = warningDialog();
                        if (opt == JOptionPane.NO_OPTION) {
                            System.exit(0);
                        } else {
                            Trident.getInstance().getStatus1().setText("Ready.");
                            break;
                        }
                    } else {
                        System.exit(0);
                    }

                case "Save":
                    saveFile();
                    break;

                case "Save As":
                    FileSaveAs();
                    break;

                default:
                    if (e.getActionCommand().contains("/") | e.getActionCommand().contains("\\")) {
                        Trident.getInstance().setPath(e.getActionCommand());
                        openFile();
                    } else {
                        boil(e.getActionCommand());
                    }
                    break;
            }
        } catch (UnsupportedFileException ufe) {
            TridentLogger.getInstance().error(this.getClass(), "SOURCE_ACTION_ERR:" + ufe.getLocalizedMessage());
        } catch (Exception exp) {
            TridentLogger.getInstance().error(this.getClass(), "FILE_MENU_CRASH" + exp.getLocalizedMessage());
        }
    }
}
