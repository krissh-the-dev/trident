package org.trident.view;/*
                         *  org.trident.view.FindReplace.java
                         *  (c) Copyright, 2020 - 2021 Krishna Moorthy
                         *  akrishnamoorthy007@gmail.com | github.com/KrishnaMoorthy12
                         *
                         * Licensed under the Apache License, Version 2.0 (the "License");
                         * you may not use this file except in compliance with the License.
                         * You may obtain a copy of the License at
                         *
                         *    http://www.apache.org/licenses/LICENSE-2.0
                         *
                         * Unless required by applicable law or agreed to in writing, software
                         * distributed under the License is distributed on an "AS IS" BASIS,
                         * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
                         * See the License for the specific language governing permissions and
                         * limitations under the License.
                         */

import org.trident.Trident;

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

/*
 * The Find and Replace v2.0
 * (Apache v2) Trident > org.trident.view.FindReplace
 * @author: Krishna Moorthy
 * Replaces org.trident.view.FindAndReplace.java [Find and Replace v1]
 * Since: v4.0
 * Deprecates: org.trident.view.FindAndReplace.findUI(),
 *                           replaceUI();
 *           : org.trident.view.FindReplaceButtonsListener.find(),
 *                                        replace(),
 *                                        replaceAll()
 */

public class FindReplace {
  public static JDialog frDialog;
  public static JButton findButton, findNextButton, replaceButton, replaceAllButton, closeButton;
  public static JTextField findField, rfindField, replaceField;
  public static JCheckBox matchCase, wholeWords, rmatchCase, rwholeWords;
  static boolean imOpen;
  static FRButtonsListener frbl;
  static JTabbedPane tabbedPane;

  public static void showUI(String activeTab) {
    /*
     * Opens up the Find and Replace Dialog
     */
    if (imOpen) {
      frDialog.requestFocus();
      return;
    }
    imOpen = true;
    frDialog = new JDialog(Trident.getInstance().getFrame(), "Find and Replace");

    // * Find Tab * //
    JPanel findTab = new JPanel();
    findTab.setLayout(new BorderLayout(5, 5));
    JPanel mainPanel = new JPanel(new BorderLayout(0, 5));
    tabbedPane = new JTabbedPane();
    frbl = new FRButtonsListener();

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
    rfindField = new JTextField(40);
    fPane.add(findLabel);
    fPane.add(rfindField);

    JPanel rPane = new JPanel(new FlowLayout());
    JLabel replaceLabel = new JLabel("Replace ");
    replaceField = new JTextField(38);
    rPane.add(replaceLabel);
    rPane.add(replaceField);

    rMainPanel.add(fPane);
    rMainPanel.add(rPane);

    JPanel ropsPane = new JPanel(new GridLayout(1, 2, 1, 1));
    ropsPane = new JPanel(new GridLayout(1, 2, 1, 1));
    rwholeWords = new JCheckBox("Whole Words only");
    rmatchCase = new JCheckBox("Match Case");
    ropsPane.setBorder(new TitledBorder(new EtchedBorder(), "Options", 0, TitledBorder.CENTER));
    ropsPane.add(rwholeWords);
    ropsPane.add(rmatchCase);

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
    replaceTab.add(ropsPane, BorderLayout.CENTER);
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
    frDialog.pack();
    frDialog.setLocationRelativeTo(Trident.getInstance().getFrame());
    frDialog.setResizable(false);
    frDialog.setVisible(true);
  }
}

class FRButtonsListener implements ActionListener {
  /*
   * Controls the Find And Replace dialog
   */
  static boolean found = false;
  static ArrayList<Integer> foundStarts;
  static ArrayList<Integer> foundEnds;

  public static void find() {
    try {
      foundStarts = new ArrayList<>();
      foundEnds = new ArrayList<>();
      foundStarts.clear();
      foundEnds.clear();
      Pattern pattern;

      if (FindReplace.tabbedPane.getSelectedIndex() == 0) {
        if (FindReplace.findField.getText().isEmpty()) {
          Trident.getInstance().getstatusText().setText("Find field is empty.");
          return;
        }
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
      } else if (FindReplace.tabbedPane.getSelectedIndex() == 1) {
        if (FindReplace.findField.getText().isEmpty()) {
          Trident.getInstance().getstatusText().setText("Find field is empty.");
          return;
        }
        // Whole words only
        if (FindReplace.rwholeWords.isSelected() && !FindReplace.rmatchCase.isSelected()) {
          pattern = Pattern.compile("\\b" + FindReplace.rfindField.getText() + "\\b", Pattern.CASE_INSENSITIVE);
        } // WW Only and Match Case
        else if (FindReplace.rwholeWords.isSelected() && FindReplace.rmatchCase.isSelected()) {
          pattern = Pattern.compile("\\b" + FindReplace.rfindField.getText() + "\\b");
        } // Not Ww, not Mc
        else if (!FindReplace.rwholeWords.isSelected() && !FindReplace.rmatchCase.isSelected()) {
          pattern = Pattern.compile(FindReplace.rfindField.getText(), Pattern.CASE_INSENSITIVE);
        } // Match case alone
        else if (!FindReplace.rwholeWords.isSelected() && FindReplace.rmatchCase.isSelected())
          pattern = Pattern.compile(FindReplace.rfindField.getText());
        else // Exceptional cases : Taken as both selected to give accuracy
          pattern = Pattern.compile("\\b" + FindReplace.rfindField.getText() + "\\b");
      } else
        throw new Exception("Unknown tab exception in Find and Replace");

      Matcher matcher = pattern.matcher(Trident.getInstance().getTextarea().getText());
      while (matcher.find()) {
        foundStarts.add(matcher.start());
        foundEnds.add(matcher.end());
        Trident.getInstance().getstatusText().setText("Found " + foundStarts.size() + " matches.");
        found = true;
      }
      if (!found) {
        Trident.getInstance().getTextarea().setCaretPosition(Trident.getInstance().getTextarea().getCaretPosition());
        Trident.getInstance().getstatusText().setText("No match found.");
      }
    } catch (Exception ez) {
      // Trident.ErrorDialog("FNR_ERR", ez);
      // TODO add logger
      ez.printStackTrace();
    }
  }

  static int i = 0;

  public static void findNext() {
    /*
     * Moves the selection and caret to next match
     */
    find();
    try {
      if (i >= foundEnds.size()) {
        i = 0;
      }
      Trident.getInstance().getTextarea().setSelectionStart(foundStarts.get(i));
      Trident.getInstance().getTextarea().setSelectionEnd(foundEnds.get(i));
      i++;

    } catch (IndexOutOfBoundsException iob) {
      Trident.getInstance().getTextarea().setCaretPosition(Trident.getInstance().getTextarea().getCaretPosition());
      Trident.getInstance().getstatusText().setText("No more matches found");
    }
  }

  public static void replace() {
    /*
     * Replaces highlighted text with text in replace field
     */
    find();
    if (foundStarts.size() == 0)
      return;
    String replacement = FindReplace.replaceField.getText();
    Trident.getInstance().getTextarea().replaceSelection(replacement);
    Trident.getInstance().getstatusText().setText("One occurance was replaced.");
    find();
    findNext();
  }

  public static void replaceAll() {
    /*
     * Replaces all the occurences of the given string.
     */
    find();
    for (int i = 0; i <= foundStarts.size(); i++) {
      replace();
    }
  }

  @Override
  public void actionPerformed(ActionEvent ae) {
    /*
     * Controls the button actions of Find and Replace Dialog
     */
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
