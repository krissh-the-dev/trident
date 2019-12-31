
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

class EditMenuListener implements ActionListener {
  public void actionPerformed(ActionEvent e) {
    try {
      switch (e.getActionCommand()) {
      case "Show Contents":
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
          cbviewer.setLocationRelativeTo(Trident.frame);
          cbviewer.setVisible(true);
        } catch (UnsupportedFlavorException ufe) {
          // Trident.ErrorDialog("FLAVOR_ERR", ufe); // Don't throw unnecessary errors
          Trident.status1.setText("Clipboard has some unsupported content.");
          Thread.sleep(200);
          cbviewer.dispose();
        } catch (IOException ioe) {
          Trident.ErrorDialog("IOE_CLIPBOARD", ioe);
        }
        break;

      case "Erase Contents":
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(""), null);
        break;

      case "Cut":
        Trident.textarea.cut();
        break;

      case "Copy":
        Trident.textarea.copy();
        break;

      case "Paste":
        Trident.textarea.paste();
        break;

      case "Undo":
        Trident.undoManager.undo();
        Trident.status1.setText("Ready.");
        Trident.Redo.setEnabled(true);
        Toolbar.redoButton.setEnabled(true);
        break;

      case "Redo":
        Trident.undoManager.redo();
        Trident.Undo.setEnabled(true);
        Toolbar.undoButton.setEnabled(true);
        Trident.status1.setText("Ready.");
        break;

      case "Find":
        // FindAndReplace.findUI();
        FindReplace.showUI("Find");
        break;

      case "Replace":
        // FindAndReplace.replaceUI();
        FindReplace.showUI("Replace");
        break;

      case "Go To":
        JDialog Goto = new JDialog(Trident.frame, "Go To");

        JSpinner lineSpinner;
        lineSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Trident.textarea.getLineCount(), 1));
        Goto.setSize(255, 90);
        JButton go = new JButton("Go");
        lineSpinner.setSize(70, 15);
        go.setSize(30, 15);

        JLabel instruction = new JLabel("Enter the line number to set the insertion point:");
        go.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            try {
              int lineNum = Integer.parseInt(lineSpinner.getValue().toString());
              Trident.textarea.setCaretPosition(Trident.textarea.getLineStartOffset(lineNum - 1));
              Trident.textarea.requestFocus();
            } catch (BadLocationException ble) {
              Trident.ErrorDialog("GOTO_LOCATION_ERR", ble);
            } catch (NullPointerException npe) {
              Trident.ErrorDialog("GOTO_NULL_ERR", npe);
            }
          }
        });
        Goto.setLayout(new FlowLayout());
        Goto.add(instruction);
        Goto.add(lineSpinner);
        Goto.add(go);
        Goto.setLocationRelativeTo(Trident.frame);
        Goto.setVisible(true);
        break;
      }
    } catch (CannotRedoException redoErr) {
      Trident.status1.setText("No more Redos available.");
      Trident.Redo.setEnabled(false);
      Toolbar.redoButton.setEnabled(false);
    } catch (CannotUndoException undoErr) {
      Trident.status1.setText("No more Undos available.");
      Trident.Undo.setEnabled(false);
      Toolbar.undoButton.setEnabled(false);
    } catch (HeadlessException noHead) {
      Trident.ErrorDialog("HEADLESS_ERR", noHead);
    } catch (Exception oopsErr) {
      Trident.ErrorDialog("EDIT_MENU_CRASH", oopsErr);
    }
  }
}
