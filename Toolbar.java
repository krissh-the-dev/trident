
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

public class Toolbar {
    public Toolbar() {
        ImageIcon openIcon = new ImageIcon("raw/open.png");
        ImageIcon saveIcon = new ImageIcon("raw/save.png");
        ImageIcon newIcon = new ImageIcon("raw/new.png");

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

        JButton newButton = new JButton(newAction);
        JButton openButton = new JButton(openAction);
        JButton saveButton = new JButton(saveAction);

        newButton.setFocusable(false);
        openButton.setFocusable(false);
        saveButton.setFocusable(false);

        Trident.toolBar.add(newButton);
        Trident.toolBar.add(openButton);
        Trident.toolBar.add(saveButton);

        Trident.toolBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.LIGHT_GRAY));
        Trident.toolBar.setRequestFocusEnabled(false);
        Trident.textarea.requestFocusInWindow();
        // Trident.toolBar.add(Box.createHorizontalGlue());
    }
}
