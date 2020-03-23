/*
 *  Configurations.java
 *  (c) Copyright, 2020 - 2021 Krishna Moorthy
 *  akrishnamoorthy007@gmail.com | github.com/KrishnaMoorthy12
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerListModel;
import java.awt.GraphicsEnvironment;

import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;

import java.awt.GridLayout;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import mdlaf.*;
import mdlaf.themes.*;

/*
 * Trident Configurations Toolkit v2.4
 * @author: Krishna Moorthy
 * 
 * [GPL v3] Trident > Configurations
 * This is only a minor sub-software of Project Trident
 * Trident Text Editor v3.0 Style Configuration Toolkit
 * Stable since @version 3.0
 * Introduced in @version 2.1
 */

/*
 * (GPL v3) Trident > ChangeListener
 * @author: Krishna Moorthy
 */

public class Configurations {
  /*
   * Configurations Dialog for the Trident Text Editor Controls the look, feel,
   * theme and font customization options in Tricdent with a Graphical User
   * Interface
   */
  static boolean ImOpen;
  static boolean EOpen;

  public static JDialog ConfigWindow;
  public static JDialog tsEditor;
  public static JComboBox<String> themeBox, fontsBox;
  public static JSpinner sizesBox, tabSizesBox;
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

    switch (themeBox.getSelectedItem().toString()) {
      case "Windows":
        themeName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        break;

      case "Nimbus":
        themeName = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
        break;

      case "Motif":
        themeName = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
        break;

      case "Metal":
        themeName = "javax.swing.plaf.metal.MetalLookAndFeel";
        break;

      case "Material Light":
        themeName = (new MaterialLookAndFeel()).getClass().getName();
        break;

      case "Default":
        themeName = UIManager.getSystemLookAndFeelClassName();
        break;

      case "Current Theme":
        break;
    }
    fontName = fontsBox.getSelectedItem().toString();
    fontSize = Integer.parseInt(sizesBox.getValue().toString());
    tabSize = Integer.parseInt(tabSizesBox.getValue().toString());
  }

  public static void applyTheme() {
    /*
     * Applys the selected Look and feel by reading the value stored in public
     * variable themeName and updates the Component Tree UI
     */
    try {
      try {
        if (themeName == null) {
          themeName = UIManager.getSystemLookAndFeelClassName();
        }
        UIManager.setLookAndFeel(themeName);
        if (UIManager.getLookAndFeel() instanceof MaterialLookAndFeel) {
          MaterialLookAndFeel.changeTheme(new MaterialLiteTheme());
        }
      } catch (Exception therr) {
        Trident.ErrorDialog("ERR_LOOK_AND_FEEL", therr);
        themeName = UIManager.getSystemLookAndFeelClassName();
        UIManager.setLookAndFeel(themeName);
      }
    } catch (Exception cnf) {
      Trident.ErrorDialog("ERR_LOOK_AND_FEEL", cnf);
    }
    SwingUtilities.updateComponentTreeUI(Trident.frame);
    SwingUtilities.updateComponentTreeUI(ConfigWindow);
  }

  public static void applyConfigs() {
    /*
     * Applies the selected configurations such as colour scheme and font styles
     */
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
    /*
     * opens up the Configurations window If already opened, Configurations window
     * gains focus
     */
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
    ConfigWindow = new JDialog(Trident.frame);
    ConfigWindow.setTitle("Configurations");
    ConfigWindow.setLayout(new GridLayout(1, 1, 1, 1));
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel ThemePanel = new JPanel(new GridLayout(5, 1, 1, 0));
    JPanel FontPanel = new JPanel(new GridLayout(3, 2, 1, 3));
    JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
    ThemePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Theme"));
    FontPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Font"));

    JLabel theme = new JLabel("UI Theme");
    String[] themes = { "Current Theme", "Default", "Material Light", "Metal", "Motif", "Nimbus", "Windows" };
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
    String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    fontsBox = new JComboBox<String>(fonts);

    JLabel fontSize = new JLabel("Font Size");
    Integer[] sizes = { 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 28, 32, 40, 48, 56, 64, 72, 86, 100, 124, 148, 196 };
    sizesBox = new JSpinner(new SpinnerListModel(sizes));

    JLabel tabSize = new JLabel("Tab size");
    Integer[] tabSizes = { 2, 4, 8 };
    tabSizesBox = new JSpinner(new SpinnerListModel(tabSizes));

    FontPanel.add(fontFace);
    FontPanel.add(fontsBox);
    FontPanel.add(fontSize);
    FontPanel.add(sizesBox);
    FontPanel.add(tabSize);
    FontPanel.add(tabSizesBox);

    JButton apply = new JButton("Apply");
    JButton save = new JButton("Save");
    JButton restore = new JButton("Reset");
    JButton cancel = new JButton("Cancel");

    buttonPanel.add(apply);
    buttonPanel.add(save);
    buttonPanel.add(restore);
    buttonPanel.add(cancel);
    ConfigurationsListener cl = new ConfigurationsListener();
    apply.addActionListener(cl);
    save.addActionListener(cl);
    restore.addActionListener(cl);
    cancel.addActionListener(cl);

    buttonPanel.setBorder(new EmptyBorder(2, 0, 0, 0));

    mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    mainPanel.add(ThemePanel, BorderLayout.NORTH);
    mainPanel.add(FontPanel, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    setData();
    ConfigWindow.add(mainPanel);

    ConfigWindow.pack();
    ConfigWindow.setResizable(false);
    ImageIcon ic = new ImageIcon("raw/trident_icon.png");
    ConfigWindow.setIconImage(ic.getImage());
    ConfigWindow.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

    WindowListener ConfigWindowCloseListener = new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        ImOpen = false;
        ConfigWindow.dispose();
      }
    };
    ConfigWindow.addWindowListener(ConfigWindowCloseListener);

    ConfigWindow.setLocationRelativeTo(Trident.frame);
    ConfigWindow.setVisible(true);
  }

  protected static void showEditor() {
    /*
     * Opens up the TS File editor that stores the current values of saved
     * configurations If already open, then the window gains focus. Any changes made
     * to the text is automatically saved
     */
    try {
      if (EOpen) {
        tsEditor.requestFocus();
        return;
      }
      EOpen = true;

      tsEditor = new JDialog(Trident.frame, "Style Editor");
      tsEditor.setSize(450, 350);
      tsEditor.setIconImage((new ImageIcon("raw/trident.png")).getImage());
      JPanel TextViewer = new JPanel();
      File tsFile = new File("configurations.ts");
      FileReader fr = new FileReader(tsFile);
      BufferedReader br = new BufferedReader(fr);
      String tsContents = "";
      for (String line = br.readLine(); line != null; line = br.readLine()) {
        tsContents += line + System.lineSeparator();
      }
      fr.close();
      br.close();
      JTextArea tsViewer = new JTextArea(tsContents);
      JScrollPane tsScrollController = new JScrollPane(tsViewer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
          JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      tsScrollController.setBorder(new EmptyBorder(-1, 0, -1, 0));
      TextViewer.setLayout(new GridLayout(1, 1, 1, 1));
      tsEditor.setLayout(new BorderLayout());
      TextViewer.add(tsScrollController);
      tsEditor.getContentPane().add(TextViewer, BorderLayout.CENTER);
      tsViewer.getDocument().addDocumentListener(new DocumentListener() {
        private void saveSettings() {
          /*
           * Saves the configurations.ts open in Conig Editor window
           */
          try {
            String tsContents = tsViewer.getText();
            File tsFile = new File("configurations.ts");
            FileWriter fileWritter = new FileWriter(tsFile, false);
            BufferedWriter bw = new BufferedWriter(fileWritter);
            bw.write(tsContents);
            bw.close();
          } catch (IOException fIoException) {
            Trident.ErrorDialog("TS_THREAD_IO", fIoException);
          }
        }

        public void changedUpdate(DocumentEvent e) {
          saveSettings();
        }

        public void removeUpdate(DocumentEvent e) {
          saveSettings();
        }

        public void insertUpdate(DocumentEvent e) {
          saveSettings();
        }
      });
      tsEditor.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

      tsEditor.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
          EOpen = false;
          tsEditor.dispose();
        }
      });
      tsEditor.setLocationRelativeTo(Trident.frame);
      tsEditor.setVisible(true);
    } catch (Exception unknownException) {
      Trident.ErrorDialog("UNKNOWN_TS_ERR", unknownException);
    }
  }

  public static void setData() {
    /*
     * Reads the stored settings from configurations.ts and sets the values in the
     * Confurations Window UI
     */
    read();
    themeBox.setSelectedItem(themeName);
    if (primary.equals(Color.WHITE))
      light.setSelected(true);
    else
      dark.setSelected(true);

    fontsBox.setSelectedItem(fontName);
    sizesBox.setValue(fontSize);
    tabSizesBox.setValue(tabSize);
  }

  public static void apply() {
    /*
     * Generates theme colors for selected colour scheme and applys all the settings
     */
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

  public static void write() {
    /*
     * Writes the selected settings (in Config UI) to the conigurations.ts file
     */
    try {
      String contents;
      File tsf = new File("./configurations.ts");
      FileWriter fileWritter = new FileWriter(tsf, false);
      BufferedWriter bw = new BufferedWriter(fileWritter);
      contents = "themeName:" + themeName + "," + System.lineSeparator();
      contents += "colorScheme:";
      if (primary.equals(Color.WHITE)) {
        contents += "light,";
      } else {
        contents += "dark,";
      }
      contents += System.lineSeparator();
      contents += "fontName:" + fontName + "," + System.lineSeparator();
      contents += "fontSize:" + fontSize + "," + System.lineSeparator();
      contents += "tabSize:" + tabSize + "," + System.lineSeparator();
      bw.write(contents);
      bw.close();
    } catch (IOException wre) {
      Trident.ErrorDialog("SETTINGS_WRITE_ERR", wre);
    }
  }

  public static void read() {
    /*
     * Reads the stored settings from the configurations.ts file
     */
    try {
      File settingsFile = new File("configurations.ts");
      FileReader sfr = new FileReader(settingsFile);
      BufferedReader sbr = new BufferedReader(sfr);
      String tsContents = "";
      for (String setting = sbr.readLine(); setting != null; setting = sbr.readLine()) {
        tsContents += setting + System.lineSeparator();
      }
      sfr.close();
      sbr.close();
      String settings[] = tsContents.split(",");
      String settingSet[] = new String[5];
      for (int i = 0; i < 5; i++) {
        settingSet[i] = settings[i].split(":")[1];
      }

      themeName = settingSet[0];
      String colorScheme = settingSet[1];
      if (colorScheme.equals("light")) {
        primary = Color.WHITE;
        secondary = Color.BLACK;
      } else {
        primary = Color.BLACK;
        secondary = Color.WHITE;
      }
      fontName = settingSet[2];
      fontSize = Integer.parseInt(settingSet[3]);
      tabSize = Integer.parseInt(settingSet[4]);
    } catch (IOException ioe) {
      Trident.ErrorDialog("SETTINGS_READ_ERR", ioe);
    }
  }

  public static final void raw_apply() {
    /*
     * Directly reads the configurations.ts and apply the settings (independent of
     * Configuration Window)
     */
    read();
    try {
      if (primary.equals(Color.WHITE)) {
        statusbg = new Color(210, 210, 210);
        statusfg = Color.BLACK;
      } else if (primary.equals(Color.BLACK)) {
        statusbg = new Color(25, 25, 25);
        statusfg = Color.WHITE;
      }
      applyConfigs();

      AutoSave.setEnabled(true);
      Trident.textarea.setLineWrap(false);
    } catch (Exception exp) {
      Trident.ErrorDialog("APPLY_BEG_ERR", exp);
    }
  }
}

class ConfigurationsListener implements ActionListener {
  /*
   * Controls the actions invoked from the Configurations Window
   */
  @Override
  public void actionPerformed(ActionEvent ae) {
    switch (ae.getActionCommand()) {
      case "Apply":
        Configurations.apply();
        break;
      case "Save":
        Configurations.apply();
        Configurations.write();
        Configurations.ImOpen = false;
        Configurations.ConfigWindow.dispose();
        break;
      case "Reset":
        try {
          Configurations.ImOpen = false;
          Configurations.ConfigWindow.dispose();
          Configurations.showUI();
          String defaults = "themeName:" + UIManager.getSystemLookAndFeelClassName() + ',' + System.lineSeparator();
          defaults += "colorScheme:light," + System.lineSeparator();
          defaults += "fontName:Monospaced," + System.lineSeparator();
          defaults += "fontSize:14," + System.lineSeparator();
          defaults += "tabSize:4," + System.lineSeparator();
          File tsf = new File("./configurations.ts");
          FileWriter fileWritter = new FileWriter(tsf, false);
          BufferedWriter bw = new BufferedWriter(fileWritter);
          bw.write(defaults);
          bw.close();
          fileWritter.close();
          Configurations.setData();
          Configurations.apply();
        } catch (Exception ex) {
          Trident.ErrorDialog("THEME_RESET_ERR", ex);
        }
        break;
      case "Cancel":
        Configurations.ImOpen = false;
        Configurations.ConfigWindow.dispose();
        Configurations.showUI();
        Configurations.read();
        Configurations.setData();
        Configurations.apply();
        Configurations.ImOpen = false;
        Configurations.ConfigWindow.dispose();
    }
  }
}
