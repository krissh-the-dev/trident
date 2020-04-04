/*
 *  IndentListener.java
 *  (c) Copyright, 2020 - 2021 Krishna Moorthy
 *  akrishnamoorthy007@gmail.com | github.com/KrishnaMoorthy12
 * 
 *  Modified code from Stack Overflow
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

import javax.swing.text.*;

public class IndentListener extends DocumentFilter {
  /*
   * Automatic indent matching utility Maintains the same indent level as previous
   * line whenever a new line is inserted (GPL v3) Trident > IndentListener
   * 
   * @author: Krishna Moorthy
   */

  public static boolean isRunning = true;

  public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) {
    /*
     * Inserts the calculated amount of white spaces into the document -function
     * call is dependent on Event triggered
     */
    try {
      if ("\n".equals(str))
        str = addWhiteSpace(fb.getDocument(), offs);
      super.insertString(fb, offs, str, a);
    } catch (BadLocationException be) {
      Trident.ErrorDialog("AUTO-INDENT_ERR", be);
    }
  }

  public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) {
    /*
     * Inserts the calculated amount of white spaces into the document -function
     * call is dependent on Event triggered
     */
    try {
      if ("\n".equals(str))
        str = addWhiteSpace(fb.getDocument(), offs);

      super.replace(fb, offs, length, str, a);
    } catch (BadLocationException be) {
      Trident.ErrorDialog("AUTO-INDENT_ERR", be);
    }
  }

  private String addWhiteSpace(Document doc, int offset) {
    /*
     * Calculates the indent level for current line and @returns the string to be
     * inserted
     */
    try {
      StringBuilder whiteSpace = new StringBuilder("\n");
      Element rootElement = doc.getDefaultRootElement();
      int line = rootElement.getElementIndex(offset);
      int i = rootElement.getElement(line).getStartOffset();

      while (true) {
        String temp = doc.getText(i, 1);

        if (temp.equals(" ") || temp.equals("\t")) {
          whiteSpace.append(temp);
          i++;
        } else
          break;
      }
      return whiteSpace.toString();
    } catch (Exception be) {
      Trident.ErrorDialog("AUTO-INDENT_ERR", be);
      return "";
    }
  }
}
