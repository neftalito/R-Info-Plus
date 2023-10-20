
package form;

import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;
import javax.swing.text.DocumentFilter;

class DocumentTabFilter extends DocumentFilter {
    @Override
    public void insertString(final FilterBypass fb, final int offset, String string, final AttributeSet attr)
            throws BadLocationException {
        string = string.replace("\t", "  ");
        string = string.replace("\r", "");
        string = string.replace("^M", "");
        super.insertString(fb, offset, string, attr);
    }

    @Override
    public void replace(final FilterBypass fb, final int offset, final int length, String text,
            final AttributeSet attrs) throws BadLocationException {
        text = text.replace("\t", "  ");
        text = text.replace("\r", "");
        text = text.replace("^M", "");
        super.replace(fb, offset, length, text, attrs);
    }
}
