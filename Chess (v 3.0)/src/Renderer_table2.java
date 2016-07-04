import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class Renderer_table2 extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Color color = new Color(204, 136, 68);
        Color color2 = new Color(0xEEDDBB);

        if ((row + column) % 2 == 0)                // раскраска доски
        {
            cell.setBackground(color2);
        }
        else {
            cell.setBackground(color);
        }

        if (MainWindow.ChessField2[row][column].length() > 0) {
            String charToString = Character.toString(MainWindow.ChessField2[row][column].charAt(0));
            charToString = charToString.toLowerCase();
            if (MainWindow.ChessField2[row][column].charAt(1) != 'p' && MainWindow.ChessField2[row][column].charAt(1) != 'P') {
                String charToString2 = Character.toString(MainWindow.ChessField2[row][column].charAt(1));
                charToString2 = charToString2.toUpperCase();
                MainWindow.ChessField2[row][column] = charToString + charToString2;
                MainWindow.table2.repaint();
            }
            else
            {
                String charToString2 = Character.toString(MainWindow.ChessField2[row][column].charAt(1));
                charToString2 = charToString2.toLowerCase();
                MainWindow.ChessField2[row][column] = charToString + charToString2;
                MainWindow.table2.repaint();
            }
        }

        return cell;
    }

}
