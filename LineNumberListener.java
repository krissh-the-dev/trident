import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

/*
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Highlighter.HighlightPainter;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Shape;
*/

public class LineNumberListener implements CaretListener {

  @Override
  public void caretUpdate(CaretEvent ce) {
    try {
      int offset = Trident.textarea.getCaretPosition();
      int line = Trident.textarea.getLineOfOffset(offset);
      int col = offset - Trident.textarea.getLineStartOffset(line);

      Trident.status4.setText("Line: " + (line + 1) + " Col: " + (col + 1));

      /*
       * HighlightPainter hp = new
       * DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY);
       * Trident.textarea.getHighlighter().removeHighlight(hp);
       * Trident.textarea.getHighlighter().addHighlight(Trident.textarea.
       * getLineStartOffset(line), Trident.textarea.getLineEndOffset(line), hp);
       */

    } catch (BadLocationException badexp) {
      Trident.ErrorDialog("CARET_LOCATION_ERR", badexp);
      Trident.status4.setText("Aw snap!");
    }
  }
}
