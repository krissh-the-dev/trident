import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

class FindAndReplace {
  public static JDialog findDialog, replaceDialog;
  public static JButton findButton, findAllButton, replaceButton, replaceAllButton, closeButton;
  public static JTextField findField, replaceField;
  public static JCheckBox matchCase, wholeWords;
  static boolean fimOpen, rimOpen;

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
    findField = new JTextField(40);
    JPanel mainPanel = new JPanel(new FlowLayout());
    mainPanel.add(findLabel);
    mainPanel.add(findField);

    findButton = new JButton("Find");
    findAllButton = new JButton("Find All");
    closeButton = new JButton("Close");
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(findButton);
    buttonPanel.add(findAllButton);
    buttonPanel.add(closeButton);

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
    findDialog.setSize(400, 100);
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
    JPanel mainPanel = new JPanel(new GridLayout(3, 1, 1, 1));
    JPanel fPane = new JPanel(new FlowLayout());
    JPanel rPane = new JPanel(new FlowLayout());
    fPane.add(findLabel);
    fPane.add(findField);
    rPane.add(replaceLabel);
    rPane.add(replaceField);
    mainPanel.add(fPane);
    mainPanel.add(rPane);

    findButton = new JButton("Find");
    findAllButton = new JButton("Find All");
    replaceButton = new JButton("Replace");
    replaceAllButton = new JButton("Replace All");
    closeButton = new JButton("Close");
    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(findButton);
    buttonPanel.add(findAllButton);
    buttonPanel.add(replaceButton);
    buttonPanel.add(replaceAllButton);
    buttonPanel.add(closeButton);

    mainPanel.add(buttonPanel);
    replaceDialog.add(mainPanel);
    replaceDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    replaceDialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent we) {
        rimOpen = false;
        replaceDialog.dispose();
      }
    });
    closeButton.addActionListener(new FindReplaceButtonsListener());
    replaceDialog.setSize(400, 150);
    replaceDialog.setLocationRelativeTo(Trident.frame);
    replaceDialog.setResizable(false);
    replaceDialog.setVisible(true);
  }
}

class FindReplaceButtonsListener implements ActionListener {
  @Override
  public void actionPerformed(ActionEvent ae) {
    switch (ae.getActionCommand()) {
    case "Find":
      break;

    case "Find All":
      break;

    case "Replace":
      break;

    case "Replace All":
      break;

    case "Close":
      FindAndReplace.fimOpen = false;
      FindAndReplace.findDialog.dispose();

      FindAndReplace.rimOpen = false;
      FindAndReplace.replaceDialog.dispose();
      break;
    }
  }
}
