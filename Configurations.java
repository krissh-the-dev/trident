import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.BorderLayout;

public class Configurations extends Trident {
  JFrame ConfigWindow;

  public Configurations() {
    try {
      if (uitheme == null) {
        if (checkOS() == 1) {
          uitheme = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"; // TODO: Will be read from file
        } else {
          uitheme = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
        }
      }
      UIManager.setLookAndFeel(uitheme);
    } catch (Exception themeError) {
      ErrorDialog("ERR_LOOK_AND_FEEL", themeError);
    }

    ConfigWindow = new JFrame();
    ConfigWindow.setLayout(new GridLayout(1, 1, 1, 1));
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel ThemePanel = new JPanel(new GridLayout(5, 1, 1, 1));
    JPanel FontPanel = new JPanel(new GridLayout(3, 2, 1, 1));
    JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
    buttonPanel.setSize(400, 100);
    ThemePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Theme"));
    FontPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Font"));

    JLabel theme = new JLabel("UI Theme");
    String[] themes = { "Default", "Nimbus" };
    JComboBox themeBox = new JComboBox<String>(themes);
    themeBox.setSize(250, 10);

    JLabel scheme = new JLabel("Color Scheme");
    ButtonGroup schemeGroup = new ButtonGroup();
    JRadioButton light = new JRadioButton("Light");
    JRadioButton dark = new JRadioButton("Dark");

    ThemePanel.add(theme);
    ThemePanel.add(themeBox);
    ThemePanel.add(scheme);
    ThemePanel.add(light);
    ThemePanel.add(dark);

    JLabel fontFace = new JLabel("Font Face");
    String[] fonts = { "Default", "Courier New", "Consolas" };
    JComboBox fontsBox = new JComboBox<String>(fonts);

    JLabel fontSize = new JLabel("Font Size");
    Integer[] sizes = { 8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 40, 56, 64, 72, 88 };
    JComboBox sizesBox = new JComboBox<Integer>(sizes);

    JLabel tabSize = new JLabel("Tab size");
    Integer[] tabSizes = { 2, 4 };
    JComboBox tabSizesBox = new JComboBox<Integer>(tabSizes);

    FontPanel.add(fontFace);
    FontPanel.add(fontsBox);
    FontPanel.add(fontSize);
    FontPanel.add(sizesBox);
    FontPanel.add(tabSize);
    FontPanel.add(tabSizesBox);

    JButton apply = new JButton("Apply");
    JButton save = new JButton("Save");
    JButton restore = new JButton("Restore");
    JButton cancel = new JButton("Cancel");

    buttonPanel.add(apply);
    buttonPanel.add(save);
    buttonPanel.add(restore);
    buttonPanel.add(cancel);

    mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    mainPanel.add(ThemePanel, BorderLayout.NORTH);
    mainPanel.add(FontPanel, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    ConfigWindow.add(mainPanel);

    ConfigWindow.setSize(400, 350);
    ConfigWindow.setVisible(true);
    ConfigWindow.setTitle("Configurations");
    ImageIcon ic = new ImageIcon("raw/trident.png");
    ConfigWindow.setIconImage(ic.getImage());
    ConfigWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public static void main(String[] args) {
    new Configurations();
  }
}
