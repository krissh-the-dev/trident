
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

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
                // System.out.println("Open File");
            }
        };
        Action saveAction = new AbstractAction("Save", saveIcon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println("Save File");
            }
        };
        Action newAction = new AbstractAction("New", newIcon) {
            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println("New File");
            }
        };

        Trident.toolBar.add(Box.createHorizontalGlue());
        // Trident.toolBar.setSize((int)(Trident.frame.getSize().getWidth()), 50);

        Trident.toolBar.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        Trident.toolBar.setLayout(new FlowLayout());
        Trident.toolBar.add(newAction);
        Trident.toolBar.add(openAction);
        Trident.toolBar.add(saveAction);
    }
}