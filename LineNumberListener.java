
/*
 *  LineNumberListener.java
 *  (c) Copyright, 2019 - 2020 Krishna Moorthy
 *  akrishnamoorthy007@gmail.com | github.io/KrishnaMoorthy12
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

import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.text.BadLocationException;

/*
 * Status Bar Line number Panel Controller
 * (GPL v3) Trident > Status Bar > LineNumberListener
 * @author: Krishna Moorthy
 */

public class LineNumberListener implements CaretListener {

  @Override
  public void caretUpdate(CaretEvent ce) {
    try {
      int offset = Trident.textarea.getCaretPosition();
      int line = Trident.textarea.getLineOfOffset(offset);
      int col = offset - Trident.textarea.getLineStartOffset(line);

      Trident.status4.setText("Line: " + (line + 1) + " Col: " + (col + 1));

    } catch (BadLocationException badexp) {
      Trident.ErrorDialog("CARET_LOCATION_ERR", badexp);
      Trident.status4.setText("Aw snap!");
    }
  }
}
