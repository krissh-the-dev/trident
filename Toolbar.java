
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;

public class Toolbar {

    public Toolbar() {
        ImageIcon openIcon = new ImageIcon(
                Toolbar.class.getResource("/raw/open.png"));
        ImageIcon saveIcon = new ImageIcon(
                Toolbar.class.getResource("/raw/save.png"));
        ImageIcon newIcon = new ImageIcon(
                Toolbar.class.getResource("/raw/new.png"));
        
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
        Trident.toolBar.add(newAction);
        Trident.toolBar.add(openAction);
        Trident.toolBar.add(saveAction);
        Trident.toolBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.LIGHT_GRAY));
        Trident.toolBar.setRequestFocusEnabled(false);
        Trident.textarea.requestFocusInWindow();
        // Trident.toolBar.add(Box.createHorizontalGlue());
    }
}
