
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
import java.awt.Desktop;

public class Toolbar {
    public Toolbar() {
        ImageIcon openIcon = new ImageIcon("raw/open.png");
        ImageIcon saveIcon = new ImageIcon("raw/save.png");
        ImageIcon newIcon = new ImageIcon("raw/new.png");
        ImageIcon helpIcon = new ImageIcon("raw/help.png");

        Action openAction = new AbstractAction("Open", openIcon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileMenuListener.FileOpener();
            }
        };
        Action saveAction = new AbstractAction("Save", saveIcon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileMenuListener.FileSaver(Trident.path);
            }
        };
        Action newAction = new AbstractAction("New", newIcon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileMenuListener.newFile();
            }
        };

        Action helpAction = new AbstractAction("Help", helpIcon) {
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

        JButton newButton = new JButton(newAction);
        JButton openButton = new JButton(openAction);
        JButton saveButton = new JButton(saveAction);
        JButton helpButton = new JButton(helpAction);

        newButton.setFocusable(false);
        openButton.setFocusable(false);
        saveButton.setFocusable(false);
        helpButton.setFocusable(false);

        Trident.toolBar.add(newButton);
        Trident.toolBar.add(openButton);
        Trident.toolBar.add(saveButton);
        Trident.toolBar.add(Box.createHorizontalGlue());
        Trident.toolBar.add(helpButton);

        Trident.toolBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.LIGHT_GRAY));
        Trident.toolBar.setRequestFocusEnabled(false);
        Trident.textarea.requestFocusInWindow();
    }
}
