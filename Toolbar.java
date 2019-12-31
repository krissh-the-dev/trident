
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JSeparator;
import javax.swing.JToolTip;
import javax.swing.SwingConstants;

import java.awt.Desktop;

public class Toolbar {
    public static JButton undoButton, redoButton;

    public Toolbar() {
        ImageIcon openIcon = new ImageIcon("raw/open.png");
        ImageIcon saveIcon = new ImageIcon("raw/save.png");
        ImageIcon newIcon = new ImageIcon("raw/new.png");
        ImageIcon helpIcon = new ImageIcon("raw/help.png");
        ImageIcon findIcon = new ImageIcon("raw/find.png");
        ImageIcon undoIcon = new ImageIcon("raw/undo.png");
        ImageIcon redoIcon = new ImageIcon("raw/redo.png");

        Action openAction = new AbstractAction("", openIcon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileMenuListener.FileOpener();
            }
        };
        Action saveAction = new AbstractAction("", saveIcon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileMenuListener.FileSaver(Trident.path);
            }
        };
        Action newAction = new AbstractAction("", newIcon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileMenuListener.newFile();
            }
        };

        Action findAction = new AbstractAction("", findIcon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                FindAndReplace.findUI();
            }
        };

        Action helpAction = new AbstractAction("", helpIcon) {
            @Override
            public void actionPerformed(ActionEvent e) {
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
