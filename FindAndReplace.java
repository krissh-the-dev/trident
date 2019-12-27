import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

class FindAndReplace {
  public static JDialog findDialog, replaceDialog;
  public static JButton findButton, findNextButton, replaceButton, replaceAllButton, closeButton;
  public static JTextField findField, replaceField;
  public static JCheckBox matchCase, wholeWords;
  static boolean fimOpen, rimOpen;
  static FindReplaceButtonsListener frbl = new FindReplaceButtonsListener();

  public static void findUI() {
    if (fimOpen) {
      findDialog.requestFocus();
      return;
    }
    if (rimOpen) {
      rimOpen = false;
      replaceDialog.dispose();
    }
    fimOpen = true;
    findDialog = new JDialog(Trident.frame, "Find");
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BorderLayout(5, 5));

    JLabel findLabel = new JLabel("Find ");
    findField = new JTextField(43);
    // findField.setComponentPopupMenu(Trident.editorMenu);
    JPanel mainPanel = new JPanel(new GridLayout(2, 1, 1, 1));
    JPanel fieldPane = new JPanel();
    fieldPane.setBorder(new EmptyBorder(5, 0, 0, 0));
    fieldPane.add(findLabel);
    fieldPane.add(findField);
    mainPanel.add(fieldPane);

    wholeWords = new JCheckBox("Whole Words only");
    matchCase = new JCheckBox("Match Case");
    JPanel opsPane = new JPanel(new GridLayout(1, 2, 1, 1));
    opsPane.setBorder(new TitledBorder(new EtchedBorder(), "Options", 0, TitledBorder.CENTER));
    opsPane.add(wholeWords);
    opsPane.add(matchCase);
    mainPanel.add(opsPane);

    findButton = new JButton("Find");
    findButton.addActionListener(frbl);
    findNextButton = new JButton("Find Next");
    findNextButton.addActionListener(frbl);
    closeButton = new JButton("Close");
    closeButton.addActionListener(frbl);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(findButton);
    buttonPanel.add(findNextButton);
    buttonPanel.add(closeButton);

    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPanel.add(mainPanel, BorderLayout.NORTH);
    contentPanel.add(buttonPanel, BorderLayout.SOUTH);
    findDialog.add(contentPanel);
    findDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    findDialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent we) {
        fimOpen = false;
        findDialog.dispose();
      }
    });
    closeButton.addActionListener(new FindReplaceButtonsListener());
    findDialog.setSize(400, 165);
    findDialog.setLocationRelativeTo(Trident.frame);
    findDialog.setResizable(false);
    findDialog.setVisible(true);
  }

  public static void replaceUI() {
    if (rimOpen) {
      replaceDialog.requestFocus();
      return;
    }
    if (fimOpen) {
      fimOpen = false;
      findDialog.dispose();
    }
    rimOpen = true;
    replaceDialog = new JDialog(Trident.frame, "Find and Replace");
    JPanel contentPanel = new JPanel();

    JLabel findLabel = new JLabel("Find ");
    findField = new JTextField(40);
    JLabel replaceLabel = new JLabel("Replace ");
    replaceField = new JTextField(38);
    JPanel mainPanel = new JPanel(new GridLayout(2, 1, 1, 1));
    JPanel fPane = new JPanel(new FlowLayout());
    JPanel rPane = new JPanel(new FlowLayout());
    fPane.add(findLabel);
    fPane.add(findField);
    rPane.add(replaceLabel);
    rPane.add(replaceField);
    mainPanel.add(fPane);
    mainPanel.add(rPane);

    wholeWords = new JCheckBox("Whole Words only");
    matchCase = new JCheckBox("Match Case");
    JPanel opsPane = new JPanel(new GridLayout(1, 2, 1, 1));
    opsPane.setBorder(new TitledBorder(new EtchedBorder(), "Options", 0, TitledBorder.CENTER));
    opsPane.add(wholeWords);
    opsPane.add(matchCase);
    // mainPanel.add(opsPane);

    findButton = new JButton("Find");
    findButton.addActionListener(frbl);

    findNextButton = new JButton("Find Next");
    findNextButton.addActionListener(frbl);

    replaceButton = new JButton("Replace");
    replaceButton.addActionListener(frbl);

    replaceAllButton = new JButton("Replace All");
    replaceAllButton.addActionListener(frbl);

    closeButton = new JButton("Close");
    closeButton.addActionListener(frbl);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(findButton);
    buttonPanel.add(findNextButton);
    buttonPanel.add(replaceButton);
    buttonPanel.add(replaceAllButton);
    buttonPanel.add(closeButton);

    contentPanel.setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPanel.add(mainPanel, BorderLayout.NORTH);
    contentPanel.add(opsPane, BorderLayout.CENTER);
    contentPanel.add(buttonPanel, BorderLayout.SOUTH);
    replaceDialog.add(contentPanel);
    replaceDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    replaceDialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent we) {
        rimOpen = false;
        replaceDialog.dispose();
      }
    });
    closeButton.addActionListener(new FindReplaceButtonsListener());
    replaceDialog.setSize(400, 195);
    replaceDialog.setLocationRelativeTo(Trident.frame);
    replaceDialog.setResizable(false);
    replaceDialog.setVisible(true);
  }
}

class FindReplaceButtonsListener implements ActionListener {
  static boolean found = false;
  static ArrayList<Integer> foundStarts;
  static ArrayList<Integer> foundEnds;

  public static void find() {
    foundStarts = new ArrayList<>();
    foundEnds = new ArrayList<>();
    Pattern pattern;
    if (FindAndReplace.wholeWords.isSelected() && !FindAndReplace.matchCase.isSelected()) {
      Trident.textarea.setCaretPosition(Trident.textarea.getCaretPosition());
      pattern = Pattern.compile("\\b" + FindAndReplace.findField.getText() + "\\b");
    } else if (FindAndReplace.wholeWords.isSelected() && FindAndReplace.matchCase.isSelected()) {
      Trident.textarea.setCaretPosition(Trident.textarea.getCaretPosition());
      pattern = Pattern.compile("\\b" + FindAndReplace.findField.getText() + "\\b", Pattern.CASE_INSENSITIVE);
    } else if (!FindAndReplace.wholeWords.isSelected() && !FindAndReplace.matchCase.isSelected()) {
      pattern = Pattern.compile(FindAndReplace.findField.getText(), Pattern.CASE_INSENSITIVE);
    } else
      pattern = Pattern.compile(FindAndReplace.findField.getText());
    Matcher matcher = pattern.matcher(Trident.textarea.getText());
    while (matcher.find()) {
      foundStarts.add(matcher.start());
      foundEnds.add(matcher.end());
      Trident.status1.setText("Found " + foundStarts.size() + " matches.");
      found = true;
    }
    if (!found) {
      Trident.status1.setText("No match found.");
    }
  }

  static int i = 0;

  public static void findNext() {
    find();
    try {
      Trident.textarea.setSelectionStart(foundStarts.get(i));
      Trident.textarea.setSelectionEnd(foundEnds.get(i));
      i++;
      if (i == foundEnds.size()) {
        i = 0;
      }
    } catch (IndexOutOfBoundsException iob) {
      Trident.status1.setText("No more matchs found");
    }
  }

  public static void replace() {
    if (foundStarts.size() == 0)
      return;
    String replacement = FindAndReplace.replaceField.getText();
    Trident.textarea.replaceSelection(replacement);
    Trident.status1.setText("One occurance was replaced.");
    find();
    findNext();
  }

  public static void replaceAll() {
    find();
    for (int i = 0; i < foundStarts.size(); i++) {
      find();
      replace();
    }
    replace();
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
      find();
      replace();
      break;

    case "Replace All":
      replaceAll();
      break;

    case "Close":
      if (FindAndReplace.fimOpen) {
        FindAndReplace.fimOpen = false;
        FindAndReplace.findDialog.dispose();
      }
      if (FindAndReplace.rimOpen) {
        FindAndReplace.rimOpen = false;
        FindAndReplace.replaceDialog.dispose();
      }
      break;
    }
  }
}
