package org.trident.model;
/*
 *  org.trident.model.Configurations.java
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
import java.net.URISyntaxException;

import mdlaf.*;
import mdlaf.themes.*;
import org.trident.Trident;
import org.trident.util.TridentLogger;


/*
 * (GPL v3) Trident > ChangeListener
 * @author: Krishna Moorthy
 */

public class Configurations {

    private static final Class TAG = Configurations.class;
    private static final Configurations SINGLETON = new Configurations();

    public static Configurations getInstance() {
        return SINGLETON;
    }

    static boolean imOpen;
    static boolean EOpen;

    public static JDialog configWindow;
    public static JDialog tsEditor;
    public static JComboBox<String> themeBox, fontsBox;
    public static JSpinner sizesBox, tabSizesBox;
    public static JRadioButton light, dark;

    // * Configs
    protected Color menubg = null;
    protected Color menufg = null;
    protected Color statusbg = new Color(225, 225, 225);
    protected Color statusfg = Color.BLACK;
    protected Color selectionbg = new Color(23, 135, 227);
    protected Color selectionfg = Color.WHITE;
    protected Color primary = Color.WHITE;
    protected Color secondary = Color.BLACK;

    protected String themeName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
    protected static String fontName = "Consolas";
    protected static int fontSize = 14;
    protected static int tabSize = 4;

    //Getter and setter
    public static boolean isImOpen() {
        return imOpen;
    }

    public static void setImOpen(boolean imOpen) {
        Configurations.imOpen = imOpen;
    }

    public static boolean isEOpen() {
        return EOpen;
    }

    public static void setEOpen(boolean EOpen) {
        Configurations.EOpen = EOpen;
    }

    public Color getPrimary() {
        return primary;
    }

    public void setPrimary(Color primary) {
        this.primary = primary;
    }

    public Color getSecondary() {
        return secondary;
    }

    public void setSecondary(Color secondary) {
        this.secondary = secondary;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public void generateTheme(Color Primary, Color Secondary) {
        if (Primary.equals(Color.WHITE)) {
            statusbg = new Color(225, 225, 225);
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
                themeName = (new MaterialLookAndFeel()).getClass().getName(); // Dependency based
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

    public void applyTheme() {
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
                TridentLogger.getInstance().error(TAG, "ERR_LOOK_AND_FEEL" + therr.getLocalizedMessage());
                themeName = UIManager.getSystemLookAndFeelClassName();
                UIManager.setLookAndFeel(themeName);
            }
        } catch (Exception cnf) {
            TridentLogger.getInstance().error(TAG, "ERR_LOOK_AND_FEEL:" + cnf);
        }
        SwingUtilities.updateComponentTreeUI(Trident.getInstance().getFrame());
        SwingUtilities.updateComponentTreeUI(configWindow);
    }

    public void applyConfigs() {
        /*
         * Applies the selected configurations such as colour scheme and font styles
         */
        // * FONT SETTINGS
        Trident.getInstance().getTextarea().setWrapStyleWord(true);
        Trident.getInstance().getTextarea().setFont(new Font(fontName, Font.PLAIN, fontSize));
        Trident.getInstance().getTextarea().setTabSize(tabSize);

        // * COLORS
        Trident.getInstance().getEditor().setBackground(primary);
        Trident.getInstance().getTextarea().setBackground(primary);
        Trident.getInstance().getTextarea().setForeground(secondary);
        Trident.getInstance().getTextarea().setCaretColor(secondary);
        Trident.getInstance().getTextarea().setSelectedTextColor(selectionfg);
        Trident.getInstance().getTextarea().setSelectionColor(selectionbg);

        Trident.getInstance().getStatusBar().setBackground(statusbg);
        Trident.getInstance().getCommentPanel().setBackground(statusbg);
        Trident.getInstance().getOthersPanel().setBackground(statusbg);

        Trident.getInstance().getStatus1().setForeground(statusfg);
        Trident.getInstance().getStatus2().setForeground(statusfg);
        Trident.getInstance().getStatus3().setForeground(statusfg);
        Trident.getInstance().getStatus4().setForeground(statusfg);
    }

    public void showUI() {

        if (imOpen) {
            configWindow.requestFocus();
            return;
        }
        imOpen = true;
        configWindow = new JDialog(Trident.getInstance().getFrame());
        configWindow.setTitle("org.trident.model.Configurations");
        configWindow.setLayout(new GridLayout(1, 1, 1, 1));
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel themePanel = new JPanel(new GridLayout(5, 1, 1, 0));
        JPanel fontPanel = new JPanel(new GridLayout(3, 2, 1, 3));
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        themePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Theme"));
        fontPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Font"));

        JLabel theme = new JLabel("UI Theme");
        String[] themes = {"Current Theme", "Default", "Material Light", "Metal", "Motif", "Nimbus", "Windows"};
        themeBox = new JComboBox<String>(themes);

        JLabel scheme = new JLabel("Color Scheme");
        ButtonGroup schemeGroup = new ButtonGroup();
        light = new JRadioButton("Light");
        dark = new JRadioButton("Dark");

        schemeGroup.add(light);
        schemeGroup.add(dark);

        themePanel.add(theme);
        themePanel.add(themeBox);
        themePanel.add(scheme);
        themePanel.add(light);
        themePanel.add(dark);

        JLabel fontFace = new JLabel("Font Face");
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontsBox = new JComboBox<String>(fonts);

        JLabel fontSize = new JLabel("Font Size");
        Integer[] sizes = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 28, 32, 40, 48, 56, 64, 72, 86, 100, 124, 148, 196};
        sizesBox = new JSpinner(new SpinnerListModel(sizes));

        JLabel tabSize = new JLabel("Tab size");
        Integer[] tabSizes = {2, 4, 8};
        tabSizesBox = new JSpinner(new SpinnerListModel(tabSizes));

        fontPanel.add(fontFace);
        fontPanel.add(fontsBox);
        fontPanel.add(fontSize);
        fontPanel.add(sizesBox);
        fontPanel.add(tabSize);
        fontPanel.add(tabSizesBox);

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
        mainPanel.add(themePanel, BorderLayout.NORTH);
        mainPanel.add(fontPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setData();
        configWindow.add(mainPanel);

        configWindow.pack();
        configWindow.setResizable(false);
        ImageIcon ic = new ImageIcon("raw/trident_icon.png");
        configWindow.setIconImage(ic.getImage());
        configWindow.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        WindowListener ConfigWindowCloseListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                imOpen = false;
                configWindow.dispose();
            }
        };
        configWindow.addWindowListener(ConfigWindowCloseListener);

        configWindow.setLocationRelativeTo(Trident.getInstance().getFrame());
        configWindow.setVisible(true);
    }

    public void showEditor() {
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

            tsEditor = new JDialog(Trident.getInstance().getFrame(), "Style Editor");
            tsEditor.setSize(450, 350);
            tsEditor.setIconImage((new ImageIcon("raw/trident.png")).getImage());
            JPanel TextViewer = new JPanel();
            String path = this.getClass().getResource("configurations.tcf").toURI().getPath();
            TridentLogger.getInstance().debug(this.getClass(), path);
            File tsFile = new File(path);
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
                     * Saves the configurations.tcf open in Conig Editor window
                     */
                    try {
                        String tsContents = tsViewer.getText();
                        String path = this.getClass().getResource("configurations.tcf").toURI().getPath();
                        TridentLogger.getInstance().debug(this.getClass(), path);
                        File tsFile = new File(path);
                        FileWriter fileWritter = new FileWriter(tsFile, false);
                        BufferedWriter bw = new BufferedWriter(fileWritter);
                        bw.write(tsContents);
                        bw.close();
                    } catch (IOException | URISyntaxException fIoException) {
                        TridentLogger.getInstance().error(TAG, "TS_THREAD_IO: " + fIoException.getLocalizedMessage());
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
            tsEditor.setLocationRelativeTo(Trident.getInstance().getFrame());
            tsEditor.setVisible(true);
        } catch (Exception unknownException) {
            TridentLogger.getInstance().error(TAG, "UNKNOWN_TS_ERR: " + unknownException.getLocalizedMessage());
        }
    }

    public void setData() {
        /*
         * Reads the stored settings from configurations.tcf and sets the values in the
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

    public void apply() {
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
            TridentLogger.getInstance().error(TAG, "CONFIG_ERR: " + exp.getLocalizedMessage());
        }
    }

    public void write() {
        try {
            String contents;
            String path = this.getClass().getResource("configurations.tcf").toURI().getPath();
            TridentLogger.getInstance().debug(this.getClass(), path);
            File tsf = new File(path);
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
        } catch (IOException | URISyntaxException wre) {
            TridentLogger.getInstance().error(TAG, "SETTINGS_WRITE_ERR: " + wre.getLocalizedMessage());
        }
    }

    public void read() {

        try {
            String path = this.getClass().getResource("/configurations.tcf").toURI().getPath();
            TridentLogger.getInstance().debug(this.getClass(), path);
            File settingsFile = new File(path);
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
        } catch (IOException | URISyntaxException ioe) {
            TridentLogger.getInstance().error(TAG, "SETTINGS_READ_ERR: " + ioe.getLocalizedMessage());
        }
    }

    public void raw_apply() {
        /*
         * Directly reads the configurations.tcf and apply the settings (independent of
         * Configuration Window)
         */
        read();
        try {
            if (primary.equals(Color.WHITE)) {
                statusbg = new Color(225, 225, 225);
                statusfg = Color.BLACK;
            } else if (primary.equals(Color.BLACK)) {
                statusbg = new Color(25, 25, 25);
                statusfg = Color.WHITE;
            }
            applyConfigs();

            //AutoSave.setEnabled(true); TODO refactorning autosave
            Trident.getInstance().getTextarea().setLineWrap(false);
        } catch (Exception exp) {
            TridentLogger.getInstance().error(TAG, "APPLY_BEG_ERR: " + exp);
        }
    }

    public class ConfigurationsListener implements ActionListener {

        private final Class TAG = ConfigurationsListener.class;

        @Override
        public void actionPerformed(ActionEvent ae) {
            switch (ae.getActionCommand()) {
                case "Apply":
                    Configurations.getInstance().apply();
                    break;
                case "Save":
                    Configurations.getInstance().apply();
                    Configurations.getInstance().write();
                    setImOpen(false);
                    configWindow.dispose();
                    break;
                case "Reset":
                    try {
                        setImOpen(false);
                        configWindow.dispose();
                        Configurations.getInstance().showUI();
                        String defaults = "themeName:" + UIManager.getSystemLookAndFeelClassName() + ',' + System.lineSeparator();
                        defaults += "colorScheme:light," + System.lineSeparator();
                        defaults += "fontName:Monospaced," + System.lineSeparator();
                        defaults += "fontSize:14," + System.lineSeparator();
                        defaults += "tabSize:4," + System.lineSeparator();
                        String path = this.getClass().getResource("configurations.tcf").toURI().getPath();
                        TridentLogger.getInstance().debug(this.getClass(), path);
                        File tsf = new File(path);
                        FileWriter fileWritter = new FileWriter(tsf, false);
                        BufferedWriter bw = new BufferedWriter(fileWritter);
                        bw.write(defaults);
                        bw.close();
                        fileWritter.close();
                        Configurations.getInstance().setData();
                        Configurations.getInstance().apply();
                    } catch (Exception ex) {
                        TridentLogger.getInstance().error(TAG, "THEME_RESET_ERR" + ex.getLocalizedMessage());
                    }
                    break;
                case "Cancel":
                    Configurations.imOpen = false;
                    Configurations.configWindow.dispose();
                    Configurations.getInstance().showUI();
                    Configurations.getInstance().read();
                    Configurations.getInstance().setData();
                    Configurations.getInstance().apply();
                    Configurations.imOpen = false;
                    Configurations.configWindow.dispose();
            }
        }
    }
}

