import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
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

public class Configurations {
  public static JFrame ConfigWindow;
  public static JComboBox themeBox, fontsBox, sizesBox, tabSizesBox;
  public static JRadioButton light, dark;
  public static JComponent items[] = { themeBox, fontsBox, sizesBox, tabSizesBox, light, dark };

  public static Color menuColor = Color.BLACK;
  public static Color menuBG = Color.WHITE;
  public static Color statusColor = new Color(210, 210, 210);
  public static Color statusTextColor = Color.BLACK;

  public static Color primary = Color.WHITE;
  public static Color secondary = Color.BLACK;

  public static String fontName = "Consolas";
  public static int fontSize = 14;
  public static int tabSize = 4;

  public static String themeName;  // conflicting with Trident.uitheme

  public static void colorSchemeGenerator(Color Primary, Color Secondary) {
    if (Primary.equals(Color.WHITE)) {
      statusColor = new Color(210, 210, 210);
      menuColor = Color.BLACK;
      menuBG = Primary;
      statusTextColor = Color.BLACK;
    } else if (Primary.equals(Color.BLACK)) {
      statusColor = new Color(25, 25, 25);
      menuColor = Secondary;
      menuBG = statusColor; // todo
      statusTextColor = Color.WHITE;
    }
    primary = Primary;
    secondary = Secondary;
  }

  public static void applyTheme() {
    try {
      if (Trident.checkOS() == 1) {
        themeName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
      } else {
        themeName = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
      }
      UIManager.setLookAndFeel(themeName);
      UIManager.put("MenuBar.background", primary);
      UIManager.put("Menu.background", primary);
      UIManager.put("MenuItem.background", primary);

    } catch (Exception themeError) {
      Trident.ErrorDialog("ERR_LOOK_AND_FEEL", themeError);
    }
  }

  public static boolean applyConfigs() {
    Trident.textarea.setLineWrap(false); // will conflict with menu bar - but can be allowed
    Trident.textarea.setWrapStyleWord(true);
    Trident.textarea.setFont(new Font(fontName, Font.PLAIN, fontSize));
    Trident.textarea.setTabSize(tabSize);
    Trident.textarea.setBorder(new EmptyBorder(4, 4, 0, 0));
    Trident.editor.setBackground(primary);
    Trident.textarea.setBackground(primary);
    Trident.textarea.setForeground(secondary);
    Trident.textarea.setCaretColor(secondary);
    Trident.textarea.setSelectedTextColor(primary);
    Trident.textarea.setSelectionColor(new Color(23, 135, 227));

    Trident.statusBar.setBackground(statusColor);
    Trident.commentPanel.setBackground(statusColor);
    Trident.othersPanel.setBackground(statusColor);

    Trident.status1.setForeground(statusTextColor);
    Trident.status2.setForeground(statusTextColor);
    Trident.status3.setForeground(statusTextColor);
    Trident.status4.setForeground(statusTextColor);

    Trident.mb.setBackground(menuBG);
    Trident.mb.setForeground(secondary);
    // Trident.toolBar.setBackground(statusColor);

    JMenu menus[] = { Trident.fileMenu, Trident.editMenu, Trident.settingsMenu, Trident.toolsMenu, Trident.about };

    for (JMenu menu : menus) {
      menu.setForeground(menuColor);
      menu.setBackground(menuBG);
    }

    JMenuItem menuItems[] = { Trident.newFile, Trident.OpenFile, Trident.SaveFile, Trident.SaveAs, Trident.Exit,
        Trident.Undo, Trident.Redo, Trident.Copy, Trident.Cut, Trident.Paste, Trident.goTo, Trident.pCopy, Trident.pCut,
        Trident.pPaste, Trident.ShowClipboard, Trident.EraseClipboard, Trident.StyleEditor, Trident.configs,
        Trident.Compile, Trident.Run, Trident.CRun, Trident.console, Trident.AboutFile, Trident.help,
        Trident.AboutTrident, Trident.updates };

    for (JMenuItem menuItem : menuItems) {
      // menuItem.setForeground(menuColor);
      menuItem.setBackground(primary);
    }

    Trident.frame.setBackground(primary);

    AutoSave.setEnabled(true);

    return true;
  }

  @Deprecated
  public static void applyForMe() {
    ConfigWindow.setBackground(primary);

    themeBox.setForeground(secondary);
    themeBox.setBackground(primary);
    fontsBox.setForeground(secondary);
    fontsBox.setBackground(primary);
    sizesBox.setForeground(secondary);
    sizesBox.setBackground(primary);
    tabSizesBox.setForeground(secondary);
    tabSizesBox.setBackground(primary);
    light.setForeground(secondary);
    light.setBackground(primary);
    dark.setForeground(secondary);
    dark.setBackground(primary);

    // for (JComponent x : items) {
    // x.setBackground(primary);
    // x.setForeground(secondary);
    // }
  }

  public static void showUI() {
    try {
      if (themeName == null) {
        if (Trident.checkOS() == 1) {
          themeName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        } else {
          themeName = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
        }
      }
      UIManager.setLookAndFeel(themeName);
    } catch (Exception themeError) {
      Trident.ErrorDialog("ERR_LOOK_AND_FEEL", themeError);
    }

    ConfigWindow = new JFrame();
    ConfigWindow.setLayout(new GridLayout(1, 1, 1, 1));
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel ThemePanel = new JPanel(new GridLayout(5, 1, 1, 1));
    JPanel FontPanel = new JPanel(new GridLayout(3, 2, 1, 10));
    JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
    buttonPanel.setSize(400, 100);
    ThemePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Theme"));
    FontPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Font"));

    JLabel theme = new JLabel("UI Theme");
    String[] themes = { "Default", "Nimbus" };
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

    ConfigWindow.add(mainPanel);

    /* set selections */
    read();

    ConfigWindow.setSize(360, 325);
    ConfigWindow.setResizable(false);

    ConfigWindow.setTitle("Configurations");
    ImageIcon ic = new ImageIcon("raw/trident.png");
    ConfigWindow.setIconImage(ic.getImage());
    ConfigWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    ConfigWindow.setLocationRelativeTo(null);
    ConfigWindow.setVisible(true);
  }

  public static void read() {
    themeBox.setSelectedItem("Default");
    light.setSelected(true);

    fontsBox.setSelectedItem(fontName);
    sizesBox.setSelectedItem(fontSize);
    tabSizesBox.setSelectedItem(tabSize);
  }

  public static void apply() {
    try {
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
        if (Trident.checkOS() == 1) {
          themeName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        } else {
          themeName = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
        }
      }
      fontName = fontsBox.getSelectedItem().toString();
      fontSize = Integer.parseInt(sizesBox.getSelectedItem().toString());
      tabSize = Integer.parseInt(tabSizesBox.getSelectedItem().toString());

      colorSchemeGenerator(a, b);
      applyConfigs();
      applyTheme();
      SwingUtilities.updateComponentTreeUI(Trident.frame);
      // applyForMe();
    } catch (Exception exp) {
      Trident.ErrorDialog("CONFIG_ERR", exp);
    }
  }

  public static void write() {

  }

  public static void main(String[] args) {
    showUI();
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
