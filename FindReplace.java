import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

class FindReplace {
  public static JDialog frDialog;
  public static JButton findButton, findNextButton, replaceButton, replaceAllButton, closeButton;
  public static JTextField findField, replaceField;
  public static JCheckBox matchCase, wholeWords;
  static boolean imOpen;
  static FRButtonsListener frbl = new FRButtonsListener();

  public static void showUI(String activeTab) {
    if (imOpen) {
      frDialog.requestFocus();
      return;
    }
    imOpen = true;
    frDialog = new JDialog(Trident.frame, "Find and Replace");
    JTabbedPane tabbedPane = new JTabbedPane();

    // * Find Tab * //
    JPanel findTab = new JPanel();
    findTab.setLayout(new BorderLayout(5, 5));
    JPanel mainPanel = new JPanel(new BorderLayout(0, 5));

    JPanel fieldPane = new JPanel();
    JLabel findLabel = new JLabel("Find ");
    findField = new JTextField(40); // TODO Add copy, paste pop up
    fieldPane.setBorder(new EmptyBorder(5, 0, 0, 0));
    fieldPane.add(findLabel);
    fieldPane.add(findField);
    mainPanel.add(fieldPane, BorderLayout.NORTH);

    JPanel opsPane = new JPanel(new GridLayout(1, 2, 1, 1));
    wholeWords = new JCheckBox("Whole Words only");
    matchCase = new JCheckBox("Match Case");
    opsPane.setBorder(new TitledBorder(new EtchedBorder(), "Options", 0, TitledBorder.CENTER));
    opsPane.add(wholeWords);
    opsPane.add(matchCase);
    mainPanel.add(opsPane, BorderLayout.SOUTH);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    findButton = new JButton("Find");
    findButton.addActionListener(frbl);
    findNextButton = new JButton("Find Next");
    findNextButton.addActionListener(frbl);
    closeButton = new JButton("Close");
    closeButton.addActionListener(frbl);

    buttonPanel.add(findButton);
    buttonPanel.add(findNextButton);
    buttonPanel.add(closeButton);

    findTab.setBorder(new EmptyBorder(5, 5, 5, 5));
    findTab.add(mainPanel, BorderLayout.CENTER);
    findTab.add(buttonPanel, BorderLayout.SOUTH);

    // * Replace Tab * //
    JPanel replaceTab = new JPanel();
    JPanel rMainPanel = new JPanel(new GridLayout(2, 1, 1, 1));

    JPanel fPane = new JPanel(new FlowLayout());
    findLabel = new JLabel("Find ");
    findField = new JTextField(40);
    fPane.add(findLabel);
    fPane.add(findField);

    JPanel rPane = new JPanel(new FlowLayout());
    JLabel replaceLabel = new JLabel("Replace ");
    replaceField = new JTextField(38);
    rPane.add(replaceLabel);
    rPane.add(replaceField);

    rMainPanel.add(fPane);
    rMainPanel.add(rPane);

    opsPane = new JPanel(new GridLayout(1, 2, 1, 1));
    wholeWords = new JCheckBox("Whole Words only");
    matchCase = new JCheckBox("Match Case");
    opsPane.setBorder(new TitledBorder(new EtchedBorder(), "Options", 0, TitledBorder.CENTER));
    opsPane.add(wholeWords);
    opsPane.add(matchCase);

    JPanel rButtonPanel = new JPanel(new FlowLayout());
    findButton = new JButton("Find");
    findButton.addActionListener(frbl);
    findNextButton = new JButton("Find Next");
    findNextButton.addActionListener(frbl);
    closeButton = new JButton("Close");
    closeButton.addActionListener(frbl);
    replaceButton = new JButton("Replace");
    replaceButton.addActionListener(frbl);
    replaceAllButton = new JButton("Replace All");
    replaceAllButton.addActionListener(frbl);

    rButtonPanel.add(findButton);
    rButtonPanel.add(findNextButton);
    rButtonPanel.add(replaceButton);
    rButtonPanel.add(replaceAllButton);
    rButtonPanel.add(closeButton);

    replaceTab.setLayout(new BorderLayout());
    replaceTab.setBorder(new EmptyBorder(5, 5, 5, 5));
    replaceTab.add(rMainPanel, BorderLayout.NORTH);
    replaceTab.add(opsPane, BorderLayout.CENTER);
    replaceTab.add(rButtonPanel, BorderLayout.SOUTH);

    frDialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent we) {
        imOpen = false;
        frDialog.dispose();
      }
    });

    tabbedPane.addTab("Find", findTab);
    tabbedPane.addTab("Replace", replaceTab);

    if (activeTab.equals("Find")) {
      tabbedPane.setSelectedIndex(0);
    } else if (activeTab.equals("Replace")) {
      tabbedPane.setSelectedIndex(1);
    }

    frDialog.add(tabbedPane);
    frDialog.setSize(400, 220);
    frDialog.setLocationRelativeTo(Trident.frame);
    frDialog.setResizable(false);
    frDialog.setVisible(true);
  }
}

class FRButtonsListener implements ActionListener {
  static boolean found = false;
  static ArrayList<Integer> foundStarts;
  static ArrayList<Integer> foundEnds;

  public static void find() {
    foundStarts = new ArrayList<>();
    foundEnds = new ArrayList<>();
    foundStarts.clear();
    foundEnds.clear();
    Pattern pattern;
    // Whole words only
    if (FindReplace.wholeWords.isSelected() && !FindReplace.matchCase.isSelected()) {
      pattern = Pattern.compile("\\b" + FindReplace.findField.getText() + "\\b", Pattern.CASE_INSENSITIVE);
    } // WW Only and Match Case
    else if (FindReplace.wholeWords.isSelected() && FindReplace.matchCase.isSelected()) {
      pattern = Pattern.compile("\\b" + FindReplace.findField.getText() + "\\b");
    } // Not Ww, not Mc
    else if (!FindReplace.wholeWords.isSelected() && !FindReplace.matchCase.isSelected()) {
      pattern = Pattern.compile(FindReplace.findField.getText(), Pattern.CASE_INSENSITIVE);
    } // Match case alone
    else if (!FindReplace.wholeWords.isSelected() && FindReplace.matchCase.isSelected())
      pattern = Pattern.compile(FindReplace.findField.getText());
    else // Exceptional cases : Taken as both selected to give accuracy
      pattern = Pattern.compile("\\b" + FindReplace.findField.getText() + "\\b");

    Matcher matcher = pattern.matcher(Trident.textarea.getText());
    while (matcher.find()) {
      foundStarts.add(matcher.start());
      foundEnds.add(matcher.end());
      Trident.status1.setText("Found " + foundStarts.size() + " matches.");
      found = true;
    }
    if (!found) {
      Trident.textarea.setCaretPosition(Trident.textarea.getCaretPosition());
      Trident.status1.setText("No match found.");
    }
  }

  static int i = 0;

  public static void findNext() {
    find();
    try {
      if (i >= foundEnds.size()) {
        i = 0;
      }
      Trident.textarea.setSelectionStart(foundStarts.get(i));
      Trident.textarea.setSelectionEnd(foundEnds.get(i));
      i++;

    } catch (IndexOutOfBoundsException iob) {
      Trident.status1.setText("No more matches found");
    }
  }

  public static void replace() {
    find();
    if (foundStarts.size() == 0)
      return;
    String replacement = FindReplace.replaceField.getText();
    Trident.textarea.replaceSelection(replacement);
    Trident.status1.setText("One occurance was replaced.");
    find();
    findNext();
  }

  public static void replaceAll() {
    find();
    for (int i = 0; i <= foundStarts.size(); i++) {
      replace();
    }
  }

  @Override
  public void actionPerformed(ActionEvent ae) {
    switch (ae.getActionCommand()) {
    case "Find":
      find();
      break;

    case "Find Next":
      findNext();
      break;

    case "Replace":
      replace();
      break;

    case "Replace All":
      replaceAll();
      break;

    case "Close":
      if (FindReplace.imOpen) {
        FindReplace.imOpen = false;
        FindReplace.frDialog.dispose();
      }
      break;
    }
  }
}
