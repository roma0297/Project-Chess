import javax.swing.*;
import java.awt.*;

/**
 * Created by Рома on 29.06.2016.
 */
public class Main {
    public static void main(String[] args) //основной метод
    {
        MainWindow mainWindow = new MainWindow();

        mainWindow.setTitle("Шахматы");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(1200, 520);
        mainWindow.setMinimumSize(new Dimension(1000, 520));
        //mainWindow.setDefaultLookAndFeelDecorated(true);
        mainWindow.setVisible(true);
    }


}
