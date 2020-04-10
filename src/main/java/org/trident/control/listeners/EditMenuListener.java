package org.trident.control.listeners;
/*
 *  org.trident.control.listeners.EditMenuListener.java
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
import org.trident.view.FindReplace;
import org.trident.view.Toolbar;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/*
 * (Apache v2) Trident > org.trident.control.listeners.EditMenuListener
 * @author: Krishna Moorthy
 */

public class EditMenuListener implements ActionListener {
  /*
   * Influences the behaviour of Edit Menu
   */

  public static boolean isRunning = true;

  protected void showClipboard() {
    /*
     * Displays a text editor that shows clipboard contents
     */
    Clipboard clipboard;
    JDialog cbviewer = new JDialog();
    try {
      clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      cbviewer.setSize(450, 350);
      cbviewer.setTitle("Clipboard Viewer");
      JPanel TextViewer = new JPanel();
      JTextArea cta = new JTextArea(clipboard.getData(DataFlavor.stringFlavor).toString());
      JScrollPane spv = new JScrollPane(cta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
          JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      spv.setBorder(new EmptyBorder(-1, 0, -1, 0));
      TextViewer.setLayout(new GridLayout(1, 1, 1, 1));
      cbviewer.setLayout(new BorderLayout());
      TextViewer.add(spv);
      cbviewer.getContentPane().add(TextViewer, BorderLayout.CENTER);
      cbviewer.setLocationRelativeTo(Trident.getInstance().getFrame());
      cbviewer.setVisible(true);
    } catch (UnsupportedFlavorException ufe) {
      // Trident.ErrorDialog("FLAVOR_ERR", ufe); // Don't throw unnecessary errors
      Trident.getInstance().getstatusText().setText("Clipboard has some unsupported content.");
      cbviewer.dispose();
    } catch (IOException ioe) {
      // Trident.ErrorDialog("IOE_CLIPBOARD", ioe); <- Avoid
    }
  }

  public void actionPerformed(ActionEvent e) {
    /*
     * Controls the actions of Edit Menu items
     */
    try {
      switch (e.getActionCommand()) {
        case "Show Contents":
          showClipboard();
          break;

        case "Erase Contents":
          Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null);
          break;

        case "Cut":
          Trident.getInstance().getTextarea().cut();
          break;

        case "Copy":
          Trident.getInstance().getTextarea().copy();
          break;

        case "Paste":
          Trident.getInstance().getTextarea().paste();
          break;

        case "Undo":
          Trident.getInstance().getUndoManager().undo();
          Trident.getInstance().getstatusText().setText("Ready.");
          Trident.getInstance().getRedo().setEnabled(true);
          Toolbar.redoButton.setEnabled(true);
          break;

        case "Redo":
          Trident.getInstance().getUndoManager().redo();
          Trident.getInstance().getUndo().setEnabled(true);
          Toolbar.undoButton.setEnabled(true);
          Trident.getInstance().getstatusText().setText("Ready.");
          break;

        case "Find":
          FindReplace.showUI("Find");
          break;

        case "Replace":
          FindReplace.showUI("Replace");
          break;

        case "Go To":
          GoToController.go();
          break;
      }
    } catch (CannotRedoException redoErr) {
      Trident.getInstance().getstatusText().setText("No more Redos available.");
      Trident.getInstance().getRedo().setEnabled(false);
      Toolbar.redoButton.setEnabled(false);
    } catch (CannotUndoException undoErr) {
      Trident.getInstance().getstatusText().setText("No more Undos available.");
      Trident.getInstance().getUndo().setEnabled(false);
      Toolbar.undoButton.setEnabled(false);
    } catch (HeadlessException noHead) {
      // Trident.ErrorDialog("HEADLESS_ERR", noHead); <-Avoid
    } catch (Exception oopsErr) {
      TridentLogger.getInstance().debug(this.getClass(), "EDIT_MENU_CRASH: " + oopsErr);
    }
  }
}

class GoToController {
  static JSpinner lineSpinner;
  static JDialog Goto;

  static void go() {
    /*
     * Displays and controls the Goto dialog
     */
    Goto = new JDialog(Trident.getInstance().getFrame(), "Go To");

    lineSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Trident.getInstance().getTextarea().getLineCount(), 1));
    JButton go = new JButton("Go");

    JLabel instruction = new JLabel(" Enter the line number to set the insertion point: ");
    go.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          int lineNum = Integer.parseInt(lineSpinner.getValue().toString());
          Trident.getInstance().getTextarea()
              .setCaretPosition(Trident.getInstance().getTextarea().getLineStartOffset(lineNum - 1));
          Goto.dispose();
        } catch (BadLocationException | NullPointerException ble) {
          TridentLogger.getInstance().error(this.getClass(), "GOTO_LOCATION_ERR: " + ble);
        }
      }
    });
    Goto.setLayout(new BorderLayout());
    Goto.getContentPane().add(instruction, BorderLayout.NORTH);
    JPanel oprPane = new JPanel(new FlowLayout());
    oprPane.add(lineSpinner);
    oprPane.add(go);
    Goto.getContentPane().add(oprPane, BorderLayout.CENTER);
    Goto.pack();
    Goto.setLocationRelativeTo(Trident.getInstance().getFrame());
    Goto.setResizable(false);
    Goto.setVisible(true);
  }
}
