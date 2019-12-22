import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Configurations {
  private static boolean ImOpen;

  public static JFrame ConfigWindow;
  public static JComboBox themeBox, fontsBox, sizesBox, tabSizesBox;
  public static JRadioButton light, dark;

  // * Configs 
  public static Color menubg = null;
  public static Color menufg = null;
  public static Color statusbg = new Color(210, 210, 210);
  public static Color statusfg = Color.BLACK;
  public static Color selectionbg = new Color(23, 135, 227);
  public static Color selectionfg = Color.WHITE;
  public static Color primary = Color.WHITE, secondary = Color.BLACK;

  public static String themeName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
  public static String fontName = "Consolas";
  public static int fontSize = 14;
  public static int tabSize = 4;

  public static void generateTheme(Color Primary, Color Secondary) {
    if (Primary.equals(Color.WHITE)) {
      statusbg = new Color(210, 210, 210);
      statusfg = Color.BLACK;
    } else if (Primary.equals(Color.BLACK)) {
      statusbg = new Color(25, 25, 25);
      statusfg = Color.WHITE;
    }

    primary = Primary;
    secondary = Secondary;

    Color a, b;
    if (light.isSelected()) {
      a = Color.WHITE;
      b = Color.BLACK;
    } else {
      b = Color.WHITE;
      a = Color.BLACK;
    }

    switch (themeBox.getSelectedItem().toString()) {
    case "Windows":
      themeName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
      break;

    case "Nimbus":
      themeName = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
      break;

    default:
      try {
        if (Trident.checkOS() == 1) {
          themeName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        } else {
          themeName = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
        }
      } catch (Exception ue) {
        Trident.ErrorDialog("OS_UNSUPPORTED", ue);
      }
    }
    fontName = fontsBox.getSelectedItem().toString();
    fontSize = Integer.parseInt(sizesBox.getSelectedItem().toString());
    tabSize = Integer.parseInt(tabSizesBox.getSelectedItem().toString());
  }

  public static void applyTheme() {
    try {
      if (themeName == null) {
        if (Trident.checkOS() == 1) {
          themeName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        } else {
          themeName = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
        }
      }
      UIManager.setLookAndFeel(themeName);
    } catch (UnsupportedOperatingSystemException uos) {
      Trident.ErrorDialog("OS_UNSUPPORTED_ERR", uos);
    }catch (Exception therr) {
      Trident.ErrorDialog("ERR_LOOK_AND_FEEL", therr);
    }
    SwingUtilities.updateComponentTreeUI(Trident.frame);
    SwingUtilities.updateComponentTreeUI(ConfigWindow);
  }

  public static void applyConfigs() {
    // * FONT SETTINGS
    Trident.textarea.setWrapStyleWord(true);
    Trident.textarea.setFont(new Font(fontName, Font.PLAIN, fontSize));
    Trident.textarea.setTabSize(tabSize);

    // * COLORS
    Trident.editor.setBackground(primary);
    Trident.textarea.setBackground(primary);
    Trident.textarea.setForeground(secondary);
    Trident.textarea.setCaretColor(secondary);
    Trident.textarea.setSelectedTextColor(selectionfg);
    Trident.textarea.setSelectionColor(selectionbg);

    Trident.statusBar.setBackground(statusbg);
    Trident.commentPanel.setBackground(statusbg);
    Trident.othersPanel.setBackground(statusbg);

    Trident.status1.setForeground(statusfg);
    Trident.status2.setForeground(statusfg);
    Trident.status3.setForeground(statusfg);
    Trident.status4.setForeground(statusfg);
  }

  public static void showUI() {
    if (ImOpen) {
      ConfigWindow.requestFocus();
      return;
    }
    ImOpen = true;
    try {
      UIManager.setLookAndFeel(themeName);
    } catch (Exception thmerr) {
      Trident.ErrorDialog("THEME_ERR", thmerr);
    }
    ConfigWindow = new JFrame("Configurations");
    ConfigWindow.setLayout(new GridLayout(1, 1, 1, 1));
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel ThemePanel = new JPanel(new GridLayout(5, 1, 1, 1));
    JPanel FontPanel = new JPanel(new GridLayout(3, 2, 1, 10));
    JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
    buttonPanel.setSize(400, 100);
    ThemePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Theme"));
    FontPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Font"));

    JLabel theme = new JLabel("UI Theme");
    String[] themes = { "Default", "Nimbus", "Windows" };
    themeBox = new JComboBox<String>(themes);

    JLabel scheme = new JLabel("Color Scheme");
    ButtonGroup schemeGroup = new ButtonGroup();
    light = new JRadioButton("Light");
    dark = new JRadioButton("Dark");

    schemeGroup.add(light);
    schemeGroup.add(dark);

    ThemePanel.add(theme);
    ThemePanel.add(themeBox);
    ThemePanel.add(scheme);
    ThemePanel.add(light);
    ThemePanel.add(dark);

    JLabel fontFace = new JLabel("Font Face");
    String[] fonts = { "Courier New", "Consolas", "Courier", "Times New Roman" };
    fontsBox = new JComboBox<String>(fonts);

    JLabel fontSize = new JLabel("Font Size");
    Integer[] sizes = { 8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 40, 56, 64, 72, 88 };
    sizesBox = new JComboBox<Integer>(sizes);

    JLabel tabSize = new JLabel("Tab size");
    Integer[] tabSizes = { 2, 4 };
    tabSizesBox = new JComboBox<Integer>(tabSizes);

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
    ConfigurationsListener cl = new ConfigurationsListener();
    apply.addActionListener(cl);
    apply.addActionListener(cl);
    cancel.addActionListener(cl);

    buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));

    mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    mainPanel.add(ThemePanel, BorderLayout.NORTH);
    mainPanel.add(FontPanel, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    setData();
    ConfigWindow.add(mainPanel);

    ConfigWindow.setSize(360, 325);
    ConfigWindow.setResizable(false);
    ImageIcon ic = new ImageIcon("raw/trident_icon.png");
    ConfigWindow.setIconImage(ic.getImage());
    ConfigWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    WindowListener ConfigWindowCloseListener = new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        ImOpen = false;
        ConfigWindow.dispose();
      }
    };
    ConfigWindow.addWindowListener(ConfigWindowCloseListener);

    ConfigWindow.setLocationRelativeTo(null);
    ConfigWindow.setVisible(true);
  }

  public static void setData() {
    themeBox.setSelectedItem(themeName);
    if (primary.equals(Color.WHITE))
      light.setSelected(true);
    else
      dark.setSelected(true);

    fontsBox.setSelectedItem(fontName);
    sizesBox.setSelectedItem(fontSize);
    tabSizesBox.setSelectedItem(tabSize);
  }

  public static void apply() {
    try {
      Color a = Color.WHITE, b = Color.BLACK;
      if (light.isSelected()) {
        a = Color.WHITE;
        b = Color.BLACK;
      } else {
        b = Color.WHITE;
        a = Color.BLACK;
      }
      generateTheme(a, b);
      applyConfigs();
      applyTheme();
    } catch (Exception exp) {
      Trident.ErrorDialog("CONFIG_ERR", exp);
    }
  }
}

class ConfigurationsListener implements ActionListener {
  @Override
  public void actionPerformed(ActionEvent ae) {
    switch (ae.getActionCommand()) {
    case "Apply":
      Configurations.apply();
      break;
    case "Save":
      // TODO Yet to be writen
      break;
    case "Reset":
      break;
    case "Cancel":
      Configurations.ConfigWindow.dispose();
    }
  }
}
