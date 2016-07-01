import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Renderer_table1 extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Color color = new Color(207, 151, 60);
        //Color color_plus = new Color(0xFFFFFF);
        //Color color_minus = new Color(0x000000);
        //Color color0 = new Color(0x828275);

        if (MainWindow.Arr[row][column] == 0) {
            if (MainWindow.ChessField[row][column].BeatenByWhite - MainWindow.ChessField[row][column].BeatenByBlack > 0) {
                cell.setForeground(Color.WHITE);
            } else if (MainWindow.ChessField[row][column].BeatenByWhite - MainWindow.ChessField[row][column].BeatenByBlack < 0) {
                cell.setForeground(Color.black);
            } else if (MainWindow.ChessField[row][column].BeatenByWhite - MainWindow.ChessField[row][column].BeatenByBlack == 0) {
                cell.setForeground(Color.GRAY);
            }
        } else
        {
            cell.setForeground(Color.black);
        }

        if ((row + column) % 2 == 0)                // раскраска доски
        {
            cell.setBackground(Color.orange);
        } else {
            cell.setBackground(color);
        }

        return cell;
    }
}


