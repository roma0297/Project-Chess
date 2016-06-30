import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class Renderer extends DefaultTableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Color color = new Color(207, 151, 60);

        //cell.setMaximumSize(new Dimension(40,40)); //установка размера
        //cell.setMinimumSize(new Dimension(40,40));

        if ((row + column) % 2 == 0)                // раскраска доски
        {
            cell.setBackground(Color.orange);
            if (MainWindow.Arr[row][column] == 0 && MainWindow.ChessField[row][column].BeatenByWhite - MainWindow.ChessField[row][column].BeatenByBlack > 0)
                cell.setForeground(Color.WHITE);
            else if (MainWindow.Arr[row][column] == 0)
                cell.setForeground(Color.black);
        }
        else
            cell.setBackground(color);

        return cell;
    }

}
