
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
import java.io.File;
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
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

class EditMenuListener extends Trident implements ActionListener {
  public void actionPerformed(ActionEvent e) {
    try {
      switch (e.getActionCommand()) {
      case "Show Contents":
        Clipboard clipboard;
        try {
          clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
          JDialog cbviewer = new JDialog();
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
          cbviewer.setVisible(true);
        } catch (UnsupportedFlavorException ufe) {
          ErrorDialog("FLAVOR_ERR", ufe);
        } catch (IOException ioe) {
          ErrorDialog("IOE_CLIPBOARD", ioe);
        }
        break;

      case "Erase Contents":
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null);
        break;

      case "Cut":
        textarea.cut();
        break;

      case "Copy":
        textarea.copy();
        break;

      case "Paste":
        textarea.paste();
        break;

      case "Undo":
        // undoManager.undoTo(undoManager.editToBeUndone());
        // TODO: To be implemented soon
        undoManager.undo();
        status1.setText("Ready.");
        Redo.setEnabled(true);
        break;

      case "Redo":
        // undoManager.redoTo(undoManager.editToBeUndone());
        undoManager.redo();
        Undo.setEnabled(true);
        status1.setText("Ready.");
        break;

      case "Go To":
        JDialog Goto = new JDialog(frame, "Go To");
        JSpinner line = new JSpinner(new SpinnerNumberModel(1, 1, textarea.getLineCount(), 1));
        Goto.setSize(255, 90);
        JButton go = new JButton("Go");
        line.setSize(70, 15);
        go.setSize(30, 15);

        JLabel instruction = new JLabel("Enter the line number to set the insertion point:");
        go.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            try {
              int lineNum = Integer.parseInt(line.getValue().toString());
              textarea.setCaretPosition(textarea.getLineStartOffset(lineNum - 1));
              textarea.requestFocus();
            } catch (BadLocationException ble) {
              ErrorDialog("GOTO_LOCATION_ERR", ble);
            } catch (NullPointerException npe) {
              ErrorDialog("GOTO_NULL_ERR", npe);
            }
          }
        });
        Goto.setLayout(new FlowLayout());
        Goto.add(instruction);
        Goto.add(line);
        Goto.add(go);
        Goto.setVisible(true);
        break;
      }
    } catch (CannotRedoException redoErr) {
      status1.setText("No more Redos available.");
      Redo.setEnabled(false);
    } catch (CannotUndoException undoErr) {
      status1.setText("No more Undos available.");
      Undo.setEnabled(false);
    } catch (HeadlessException noHead) {
      ErrorDialog("HEADLESS_ERR", noHead);
    } catch (Exception oopsErr) {
      ErrorDialog("EDIT_MENU_CRASH", oopsErr);
    }
  }
}
