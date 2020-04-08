
/*
 *  Toolbar.java
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

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/*
 * Trident ToolBar contoller v2.0
 * (Apache v2) Trident > Toolbar
 * @author: Krishna Moorthy
 */

public class Toolbar {
    /*
     * Adds Toolbar items and controls actions
     */
    public static JButton undoButton, redoButton;

    public Toolbar() {
        /*
         * Initializes Toolbar and its items and also adds functionalities
         */
        ImageIcon openIcon = new ImageIcon("raw/open.png");
        ImageIcon saveIcon = new ImageIcon("raw/save.png");
        ImageIcon newIcon = new ImageIcon("raw/new.png");
        ImageIcon helpIcon = new ImageIcon("raw/help.png");
        ImageIcon findIcon = new ImageIcon("raw/find.png");
        ImageIcon undoIcon = new ImageIcon("raw/undo.png");
        ImageIcon redoIcon = new ImageIcon("raw/redo.png");

        Action openAction = new AbstractAction("", openIcon) {
            /*
             * Action for new icon in Toolbar
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                FileMenuListener.FileOpener();
            }
        };
        Action saveAction = new AbstractAction("", saveIcon) {
            /*
             * Action for save icon in Toolbar
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                FileMenuListener.saveFile();
            }
        };
        Action newAction = new AbstractAction("", newIcon) {
            /*
             * Action for new icon in Toolbar
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                FileMenuListener.newFile();
            }
        };

        Action findAction = new AbstractAction("", findIcon) {
            /*
             * Action for find icon in Toolbar
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                // FindAndReplace.findUI();
                FindReplace.showUI("Find");
            }
        };

        Action helpAction = new AbstractAction("", helpIcon) {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * Action for help icon in Toolbar
                 */
                try {
                    Desktop.getDesktop()
                            .browse(java.net.URI.create("https://www.github.com/KrishnaMoorthy12/trident/issues"));
                } catch (Exception exc) {
                    Trident.ErrorDialog("BROSWER_ERR_HELP_TB", exc);
                }
            }
        };

        undoButton = new JButton(undoIcon);
        undoButton.addActionListener(Trident.eml);
        undoButton.setActionCommand("Undo");

        redoButton = new JButton(redoIcon);
        redoButton.addActionListener(Trident.eml);
        redoButton.setActionCommand("Redo");

        JButton newButton = new JButton(newAction);
        JButton openButton = new JButton(openAction);
        JButton saveButton = new JButton(saveAction);
        JButton helpButton = new JButton(helpAction);
        JButton findButton = new JButton(findAction);

        newButton.setFocusable(false);
        openButton.setFocusable(false);
        saveButton.setFocusable(false);
        helpButton.setFocusable(false);
        undoButton.setFocusable(false);
        redoButton.setFocusable(false);
        findButton.setFocusable(false);

        newButton.setToolTipText("New file");
        openButton.setToolTipText("Open file");
        saveButton.setToolTipText("Save");
        undoButton.setToolTipText("Undo");
        redoButton.setToolTipText("Redo");
        findButton.setToolTipText("Find");
        helpButton.setToolTipText("Help");

        Trident.toolBar.add(newButton);
        Trident.toolBar.add(openButton);
        Trident.toolBar.add(saveButton);
        // Trident.toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        Trident.toolBar.add(undoButton);
        Trident.toolBar.add(redoButton);
        // Trident.toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        Trident.toolBar.add(findButton);
        // Trident.toolBar.add(new JSeparator(SwingConstants.VERTICAL));
        Trident.toolBar.add(Box.createHorizontalGlue());
        Trident.toolBar.add(helpButton);

        Trident.toolBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.LIGHT_GRAY));
        Trident.toolBar.setRequestFocusEnabled(false);
        Trident.textarea.requestFocusInWindow();
    }
}
