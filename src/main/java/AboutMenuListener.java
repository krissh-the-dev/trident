/*
 *  AboutMenuListener.java
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

// * Listeners

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
// * Others
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Paths;
import java.util.Date;

// * UI Elements

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/*
 * (GPL v3) Trident > AboutMenuListener
 * @author: Krishna Moorthy
 */

class AboutMenuListener implements ActionListener {
  /*
   * Action listener for the About menu
   * 
   * Handles chosen menu item action from the about menu
   */

  public static boolean isRunning = true;

  public void actionPerformed(ActionEvent e) {
    try {
      switch (e.getActionCommand()) {
        case "About Trident":
          // TODO: Add link to version.config
          JDialog aboutDialog = new JDialog(Trident.frame, "About Trident");
          JPanel infoPanel = new JPanel();
          ImageIcon ic = new ImageIcon("raw/trident_icon.png");
          ImageIcon logo = new ImageIcon("raw/trident_logo.png");
          aboutDialog.setIconImage(ic.getImage());

          JLabel icon = new JLabel(logo);
          icon.setSize(50, 50);
          JLabel l1 = new JLabel(
              "<html><style> h1 {font-family: \"Segoe UI\", 'Google Sans', 'Product Sans', 'Roboto'; color:rgb(6,113,193);} h3 {font-family: \"Segoe UI Semilight\", 'Google Sans', 'Product Sans', 'Roboto';} </style> <center><h1> <br/><i>- Trident Text Editor -</i></h1> <h3> Version 5.0 <br/>BETA</h3></html>");
          JLabel l2 = new JLabel(
              "<html><style>h3 {font-family: \"Segoe UI\", 'Google Sans', 'Product Sans', 'Roboto', monospace; color:rgb(6,113,193); border:2px solid rgb(66,133,244); padding: 5px;} h3:hover {font-family: \"Segoe UI\", monospace; color:rgb(6,113,193); border:2px solid rgb(66,133,244); padding: 5px; background-color: rgb(6,113,193);} </style><h3>Visit Home Page</h3></html>");
          l2.setCursor(new Cursor(Cursor.HAND_CURSOR));
          l2.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
              /*
               * For the mouse click on Visit Home Page button in About Trident Dialog
               */
              try {
                Desktop.getDesktop().browse(java.net.URI.create("https://krishnamoorthy12.github.io/trident"));
              } catch (Exception de) {
                Trident.ErrorDialog("DESKTOP_ERR", de);
              }
            }
          });

          // !Not working
          aboutDialog.setBackground(Configurations.primary);

          infoPanel.add(icon);
          infoPanel.add(l1);
          infoPanel.add(l2);
          l2.setBounds(120, 400, 50, 10);
          aboutDialog.add(infoPanel);
          aboutDialog.setSize(350, 500);
          // aboutDialog.pack();

          aboutDialog.setLocationRelativeTo(Trident.frame);
          aboutDialog.setResizable(false);
          aboutDialog.setVisible(true);
          break;

        case "File Properties":
          String fileName = Paths.get(Trident.path).getFileName().toString();
          JDialog aboutFileDialog = new JDialog(Trident.frame, "File Properties");
          JPanel leftPane = new JPanel();
          JPanel rightPane = new JPanel();

          leftPane.setLayout(new GridLayout(5, 1, 2, 2));
          rightPane.setLayout(new GridLayout(5, 1, 2, 2));
          aboutFileDialog.setLayout(new BorderLayout());

          leftPane.setBackground(new Color(230, 230, 230));
          leftPane.setBorder(new EmptyBorder(1, 5, 1, 5));
          rightPane.setBackground(new Color(240, 240, 240));
          rightPane.setBorder(new EmptyBorder(1, 5, 1, 5));

          File theFile = new File(Trident.path);
          JLabel filenameLabel = new JLabel("File Name :", SwingConstants.RIGHT);
          JLabel fileLocationLabel = new JLabel("File Location :", SwingConstants.RIGHT);
          JLabel fileTypeLabel = new JLabel("File Type :", SwingConstants.RIGHT);
          JLabel fileSizeLabel = new JLabel("File Size :", SwingConstants.RIGHT);
          JLabel lastModifiedLabel = new JLabel("Last modified :", SwingConstants.RIGHT);

          JLabel filenameProperty = new JLabel(fileName);
          JLabel fileLocationProperty = new JLabel(Trident.path);
          JLabel fileTypeProperty = new JLabel(FileTypeParser.getType(Trident.path));
          JLabel fileSizeProperty = new JLabel((theFile.length() / 1024) + " KB (" + theFile.length() + " B)");
          JLabel lastModifiedProperty = new JLabel(new Date(theFile.lastModified()) + "");

          leftPane.add(filenameLabel);
          rightPane.add(filenameProperty);

          leftPane.add(fileLocationLabel);
          fileLocationProperty.setBorder(new EmptyBorder(2, 5, 0, 2));
          JScrollPane flsp = new JScrollPane(fileLocationProperty, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
              JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
          rightPane.add(flsp);
          flsp.setBorder(new EmptyBorder(-1, -1, -1, -1));

          leftPane.add(fileTypeLabel);
          rightPane.add(fileTypeProperty);

          leftPane.add(fileSizeLabel);
          rightPane.add(fileSizeProperty);

          leftPane.add(lastModifiedLabel);
          rightPane.add(lastModifiedProperty);

          aboutFileDialog.getContentPane().add(leftPane, BorderLayout.WEST);
          aboutFileDialog.getContentPane().add(rightPane, BorderLayout.CENTER);
          aboutFileDialog.setSize(450, 300);
          aboutFileDialog.setResizable(false);

          aboutFileDialog.setLocationRelativeTo(Trident.frame);
          aboutFileDialog.setVisible(true);
          break;

        case "Help":
          Desktop.getDesktop().browse(java.net.URI.create("https://www.github.com/KrishnaMoorthy12/trident/issues"));
          break;

        case "Updates":
          Desktop.getDesktop()
              .browse(java.net.URI.create("https://github.com/KrishnaMoorthy12/trident/releases/latest"));
          break;
      }
    } catch (Exception exc) {
      Trident.ErrorDialog("ABOUT_MENU_CRASH", exc);
    }
  }
}
