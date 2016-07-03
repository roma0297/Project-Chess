import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.*;

import static java.lang.Math.abs;
import static java.lang.StrictMath.min;
import static sun.swing.MenuItemLayoutHelper.max;

/**
 * Created by Рома on 29.06.2016.
 */

class MainWindow extends JFrame {

    /**
     * Ниже описаны основные структуры данных, используемые в программе
     */

    static Cell [][] ChessField = new Cell[TableSize.Hight][TableSize.Width];
    static String [][] ChessField1 = new String[TableSize.Hight][TableSize.Width];
    static String [][] ChessField2 = new String[TableSize.Hight][TableSize.Width];
    static byte [][] Arr = new byte[TableSize.Hight][TableSize.Width];

    String[] Column_names = {"a", "b", "c", "d", "e", "f", "g", "h"};

    JPanel panel = new JPanel();
    JPanel tables_panel = new JPanel();
    JPanel buttons_panel = new JPanel();

    JPanel table1_panel = new JPanel();
    JPanel table2_panel = new JPanel();

    JPanel button1_panel = new JPanel();
    JPanel button2_panel = new JPanel();
    JPanel button3_panel = new JPanel();

    JTable table2 = new JTable();

    JLabel label1 = new JLabel("Результат работы программы");
    JLabel label2 = new JLabel("Поле для входных данных");
    JLabel label3 = new JLabel("<html>  Содержимое клетки задаётся в формате [цвет][фигура], цвет - одна буква: w - белая фигура, b - чёрная . <br> Шахматная фигура задаётся одной буквой: <br>  K - король; Q - ферзь;<br> R - ладья; N - конь;<br> B - слон; p - пешка.</html>");

    JButton button1 = new JButton("Сохранить в файл");
    JButton button2 = new JButton("Загрузить из файла");
    JButton button3 = new JButton("Рассчитать");

    JLayeredPane chessPane;
    JPanel newpanel = new JPanel();
    JLabel chessPiece;
    JPanel chessBoard;
    JPanel squares[][] = new JPanel[TableSize.Hight + 1][TableSize.Width + 1];

    class Cell                                                                                                          // Класс, описывающий ячейку таблицы ChessField
    {
        public String UnitName;
        public int BeatenByWhite, BeatenByBlack;

        public Cell(String UnitName, int BeatenByWhite, int BeatenByBlack)
        {
            this.UnitName = UnitName;
            this.BeatenByWhite = BeatenByWhite;
            this.BeatenByBlack = BeatenByBlack;
        }

        public String getUnitName()
        {
            return UnitName;
        }

        public int getBeatenByWhite()
        {
            return BeatenByWhite;
        }

        public int getBeatenByBlack()
        {
            return BeatenByBlack;
        }
    }

    public abstract class TableSize {
        public static final int Width = 8;
        public static final int Hight = 8;
        private TableSize() {
        }
    }

    /**
     * Настройка врешнего вида окна
     */

    public MainWindow() {
        panel.setLayout(new BorderLayout());
        panel.add(tables_panel, BorderLayout.CENTER);
        panel.add(buttons_panel, BorderLayout.EAST);

        tables_panel.setLayout(new GridLayout(1, 2));
        tables_panel.add(table1_panel);
        tables_panel.add(table2_panel);
        tables_config();

        table1_panel.setLayout(new BoxLayout(table1_panel, BoxLayout.Y_AXIS));

        label1.setFont(new Font("Helvetica", Font.BOLD, 25));
        label1.setPreferredSize(new Dimension(400,30));
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        table1_panel.add(label1);

        newpanel.setLayout(new GridLayout(TableSize.Hight, TableSize.Width));
        newpanel.setPreferredSize(new Dimension(400,400));
        chessPane = new JLayeredPane();
        getContentPane().add(chessPane);


        for (int a = 0; a < TableSize.Hight; a++)                                                                       // Раскрашивание доски для результатов
        {
            for (int b = 0; b < TableSize.Width; b++)
            {
                squares[a][b] = new JPanel();

                if ((a + b) % 2 == 0) {
                    squares[a][b].setBackground(new Color(238, 221, 187));
                } else {
                    squares[a][b].setBackground(new Color(204, 136, 68));
                }
                newpanel.add(squares[a][b]);
            }
        }



        table1_panel.add(newpanel);

        label2.setFont(new Font("Helvetica", Font.BOLD, 25));
        label2.setPreferredSize(new Dimension(320,30));
        label3.setPreferredSize(new Dimension(320,30));
        table2_panel.setLayout(new BoxLayout(table2_panel, BoxLayout.Y_AXIS));
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        table2.setAlignmentX(Component.CENTER_ALIGNMENT);
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);
        table2_panel.add(label2);
        table2_panel.add(table2);
        table2_panel.add(label3);

        buttons_panel.setLayout(new GridLayout(3, 1));
        buttons_panel.add(button1_panel);
        buttons_panel.add(button2_panel);
        buttons_panel.add(button3_panel);

        button1_panel.setLayout(new BorderLayout());

        button2_panel.setLayout(new GridLayout(3, 1, 10, 10));
        button2_panel.add(button1, BorderLayout.WEST);
        button2_panel.add(button2, BorderLayout.WEST);
        button2_panel.add(button3, BorderLayout.WEST);

        button3_panel.setLayout(new BorderLayout());

        this.getContentPane().add(panel);

        this.button1.addActionListener(new MyButtonListener1());
        this.button2.addActionListener(new MyButtonListener2());
        this.button3.addActionListener(new MyButtonListener3());
    }

    /**
     * Создание таблиц по массивам
     */

    public void tables_config()
    {
        table2 = new JTable(ChessField2, Column_names);

        table2.setFont(new Font("Helvetica", Font.PLAIN, 23));

        table2.setRowHeight(40);

        table2.setMinimumSize(new Dimension(320,320));
        table2.setMaximumSize(new Dimension(320,320));

        for (int i = 0; i < TableSize.Width; i++) {
            table2.getColumnModel().getColumn(i).setCellRenderer(new Renderer_table2());
        }

        for (int i = 0; i < TableSize.Hight; i++)
            for (int j = 0; j < TableSize.Width; j++)
            {
                ChessField1[i][j] = new String("");
                ChessField2[i][j] = new String("");
                Arr[i][j] = 0;
                ChessField[i][j] = new Cell("",0, 0);
            }
    }

    /**
     * Ниже описаны функции обновления результатов для различных видов фигур
     */

    public static void KingUpdate(boolean isWhite, int row, int column)                                                 // Обновление для короля
    {
        for (int i = row - 1; i <= row + 1; i++)
            for (int j = column - 1; j <= column + 1; j++)
                if (areCorrectCoordinates(i, j))
                    if (isWhite)
                        ChessField[i][j].BeatenByWhite++;
                    else
                        ChessField[i][j].BeatenByBlack++;

    }


    public static void QueenUpdate(boolean isWhite, int row, int column)                                                // Обновление для ферзя
    {
        RookUpdate(isWhite, row, column);
        BishopUpdate(isWhite, row, column);
    }


    public static void RookUpdate(boolean isWhite, int row, int column)                                                 // Обновление для ладьи
    {
        boolean dir1 = true, dir2 = true, dir3 = true, dir4 = true;

        for (int i = 1; (i < TableSize.Width) && dir1; i++) {
            if (column + i < TableSize.Width) {
                if (isWhite)
                    ChessField[row][column + i].BeatenByWhite++;
                else
                    ChessField[row][column + i].BeatenByBlack++;

                if (Arr[row][column + i] == 1)
                    dir1 = false;
            }
        }

        for (int i = 1; (i < TableSize.Width) && dir2; i++) {
            if (column - i >= 0) {
                if (isWhite)
                    ChessField[row][column - i].BeatenByWhite++;
                else
                    ChessField[row][column - i].BeatenByBlack++;

                if (Arr[row][column - i] == 1)
                    dir2 = false;
            }
        }

        for (int i = 1; (i < TableSize.Hight) && dir3; i++) {
            if (row + i < TableSize.Hight) {
                if (isWhite)
                    ChessField[row + i][column].BeatenByWhite++;
                else
                    ChessField[row + i][column].BeatenByBlack++;

                if (Arr[row + i][column] == 1)
                    dir3 = false;
            }
        }

        for (int i = 1; (i < TableSize.Hight) && dir4; i++) {
            if (row - i >= 0) {
                if (isWhite)
                    ChessField[row - i][column].BeatenByWhite++;
                else
                    ChessField[row - i][column].BeatenByBlack++;

                if (Arr[row - i][column] == 1)
                    dir4 = false;
            }
        }
    }


    public static void KnightUpdate(boolean isWhite, int row, int column)                                               // Обновление для коня
    {
        if (areCorrectCoordinates(row + 1, column + 2))
            if (isWhite)
                ChessField[row + 1][column + 2].BeatenByWhite++;
            else
                ChessField[row + 1][column + 2].BeatenByBlack++;

        if (areCorrectCoordinates(row + 1, column - 2))
            if (isWhite)
                ChessField[row + 1][column - 2].BeatenByWhite++;
            else
                ChessField[row + 1][column - 2].BeatenByBlack++;

        if (areCorrectCoordinates(row - 1, column + 2))
            if (isWhite)
                ChessField[row - 1][column + 2].BeatenByWhite++;
            else
                ChessField[row - 1][column + 2].BeatenByBlack++;

        if (areCorrectCoordinates(row - 1, column - 2))
            if (isWhite)
                ChessField[row - 1][column - 2].BeatenByWhite++;
            else
                ChessField[row - 1][column - 2].BeatenByBlack++;


        if (areCorrectCoordinates(row + 2, column + 1))
            if (isWhite)
                ChessField[row + 2][column + 1].BeatenByWhite++;
            else
                ChessField[row + 2][column + 1].BeatenByBlack++;

        if (areCorrectCoordinates(row + 2, column - 1))
            if (isWhite)
                ChessField[row + 2][column - 1].BeatenByWhite++;
            else
                ChessField[row + 2][column - 1].BeatenByBlack++;

        if (areCorrectCoordinates(row - 2, column + 1))
            if (isWhite)
                ChessField[row - 2][column + 1].BeatenByWhite++;
            else
                ChessField[row - 2][column + 1].BeatenByBlack++;

        if (areCorrectCoordinates(row - 2, column - 1))
            if (isWhite)
                ChessField[row - 2][column - 1].BeatenByWhite++;
            else
                ChessField[row - 2][column - 1].BeatenByBlack++;
    }


    public static void BishopUpdate(boolean isWhite, int row, int column)                                               // Обновление для слона
    {
        boolean dir1 = true, dir2 = true, dir3 = true, dir4 = true;

        for (int i = 1; (i < max(TableSize.Width, TableSize.Hight)) && dir1; i++) {
            if (row + i < TableSize.Hight && column + i < TableSize.Width) {
                if (isWhite)
                    ChessField[row + i][column + i].BeatenByWhite++;
                else
                    ChessField[row + i][column + i].BeatenByBlack++;

                if (Arr[row + i][column + i] == 1)
                    dir1 = false;
            }
        }

        for (int i = 1; (i < max(TableSize.Width, TableSize.Hight)) && dir2; i++) {
            if (row - i >= 0 && column - i >= 0) {
                if (isWhite)
                    ChessField[row - i][column - i].BeatenByWhite++;
                else
                    ChessField[row - i][column - i].BeatenByBlack++;

                if (Arr[row - i][column - i] == 1)
                    dir2 = false;
            }
        }

        for (int i = 1; (i < max(TableSize.Width, TableSize.Hight)) && dir3; i++) {
            if (row + i < TableSize.Hight && column - i >= 0) {
                if (isWhite)
                    ChessField[row + i][column - i].BeatenByWhite++;
                else
                    ChessField[row + i][column - i].BeatenByBlack++;

                if (Arr[row + i][column - i] == 1)
                    dir3 = false;
            }
        }

        for (int i = 1; (i < max(TableSize.Width, TableSize.Hight)) && dir4; i++) {
            if (row - i >= 0 && column + i < TableSize.Width)
            {
                if (isWhite)
                    ChessField[row - i][column + i].BeatenByWhite++;
                else
                    ChessField[row - i][column + i].BeatenByBlack++;

                if (Arr[row - i][column + i] == 1)
                    dir4 = false;
            }
        }
    }


    public static void PawnUpdate(boolean isWhite, int row, int column)                                                 // Обновление для пешки
    {
        if (isWhite)
        {
            if (row - 1 >= 0) {
                if (column - 1 >= 0)
                    ChessField[row - 1][column - 1].BeatenByWhite++; //сомнения!!!!!!!!!!!!!
                if (column + 1 < TableSize.Width)
                    ChessField[row - 1][column + 1].BeatenByWhite++;
            }
        }
        else
        {
            if (row + 1 < TableSize.Hight) {
                if (column - 1 >= 0)
                    ChessField[row + 1][column - 1].BeatenByBlack++;
                if (column + 1 < TableSize.Width)
                    ChessField[row + 1][column + 1].BeatenByBlack++;
            }
        }
    }


    /**
     * Ниже описана проверка корректности координат
     */

    public static boolean areCorrectCoordinates(int x, int y)
    {
        if (x >= 0 && x < TableSize.Hight && y >= 0 && y < TableSize.Width)
            return true;
        else
            return false;
    }


    /**
     * Ниже описана обработка событий нажатий на кнопки
     */

    private class MyButtonListener1 implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            //C:\\Users\\Рома\\IdeaProjects\\Chess (v 2.0)\\out\\production\\Chess (v 2.0)\\table.txt
            String fileName = "", text = "";
            File file = new File(fileName);
            JFileChooser fileopen = new JFileChooser();


            int ret = fileopen.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION)
                file = fileopen.getSelectedFile();


            try {
                if(!file.exists()){
                    file.createNewFile();
                }
            } catch(IOException e) {
                throw new RuntimeException(e);}


            for (int i = 0; i < TableSize.Hight; i++)
                for (int j = 0; j < TableSize.Width; j++)
                    if (!ChessField2[i][j].equals(""))
                    {
                        text += ChessField2[i][j] + " " + i + " " + j + "\n";
                    }

            try {
                PrintWriter out = new PrintWriter(file.getAbsoluteFile());
                out.print(text);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();}

        }

    }

    private class MyButtonListener2 implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            //"C:\\Users\\Рома\\IdeaProjects\\Chess (v 2.0)\\out\\production\\Chess (v 2.0)\\table.txt
            String fileName = "", textLine = "";
            int x = 0, y = 0, c;
            File file = new File(fileName);
            JFileChooser fileopen = new JFileChooser();

            int ret = fileopen.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                file = fileopen.getSelectedFile();
                fileName = file.getAbsolutePath();
            }


            for (int i = 0; i < TableSize.Hight; i++)
                for (int j = 0; j < TableSize.Width; j++)
                {
                    ChessField1[i][j] = new String("");
                    ChessField2[i][j] = new String("");
                    Arr[i][j] = 0;
                    ChessField[i][j] = new Cell("",0, 0);
                }

            try(FileReader reader = new FileReader(fileName))
            {
                char[] buffer = new char[(int)file.length()];
                reader.read(buffer);

                for (int i = 0; i < (int)file.length() ; i++)
                {
                    if (i % 7 == 0 || i % 7 == 1)
                    {
                        textLine += "" + buffer[i];
                    } else if (i % 7 == 3)
                    {
                        x = buffer[i] - 48;
                    } else if (i % 7 == 5)
                    {
                        y = buffer[i] - 48;
                        ChessField2[x][y] = new String(textLine);
                        textLine = "";
                    }


                }
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }

            table2.repaint();
        }

    }

    private class MyButtonListener3 implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            boolean isWhite = true;

            for (int i = 0; i < TableSize.Hight; i++)
                for (int j = 0; j < TableSize.Width; j++)
                {
                    if ( !ChessField2[i][j].equals(""))
                        Arr[i][j] = 1;
                    else {
                        Arr[i][j] = 0;
                    }

                    squares[i][j].removeAll();
                    squares[i][j].repaint();

                    ChessField[i][j].BeatenByBlack = 0;
                    ChessField[i][j].BeatenByWhite = 0;
                }

            for (int i = 0; i < TableSize.Hight; i++)                                                                   // Пересчёт результатов
                for (int j = 0; j < TableSize.Width; j++)
                    if (Arr[i][j] == 1)
                    {
                        if (ChessField2[i][j].charAt(0) == 'w')
                            isWhite = true;
                        else if (ChessField2[i][j].charAt(0) == 'b')
                            isWhite = false;
                        else
                            errMessage();

                        switch (ChessField2[i][j].charAt(1)) {
                            case 'K':
                                KingUpdate(isWhite, i, j);
                                break;
                            case 'Q':
                                QueenUpdate(isWhite, i, j);
                                break;
                            case 'R':
                                RookUpdate(isWhite, i, j);
                                break;
                            case 'N':
                                KnightUpdate(isWhite, i, j);
                                break;
                            case 'B':
                                BishopUpdate(isWhite, i, j);
                                break;
                            case 'p':
                                PawnUpdate(isWhite, i, j);
                                break;
                            default:
                                errMessage();
                        }
                    }


            for (int i = 0; i < TableSize.Hight; i++)                                                                   // Вывод результатов в таблицу
                for (int j = 0; j < TableSize.Width; j++)
                {

                        squares[i][j].add(MakeLabel(i, j));
                        squares[i][j].validate();
                }

        }
    }



    /**
     * Проверка существования файла
     */

    private static void exists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()){
            throw new FileNotFoundException(file.getName());
        }
    }


    /**
     * Вывод сообщения об ошибке
     */

    private  static void errMessage()
    {
        System.out.print("Something wrong with the format!");
        System.exit(1);
    }


    /**
     * Создание Label-ов для вывода результатов
     */

    public JLabel MakeLabel(int i, int j)
    {
        Color color = new Color(0x73AADE);
        JLabel answerLabel = new JLabel();
        int result = MainWindow.ChessField[i][j].BeatenByWhite - MainWindow.ChessField[i][j].BeatenByBlack;

        if (Arr[i][j] == 1)
        {
            //ImageIcon icon = new ImageIcon("\\src\\" + ChessField2[i][j] + ".png");
            ImageIcon icon = new ImageIcon(MainWindow.class.getResource(ChessField2[i][j] + ".png"));
            Image img = icon.getImage();
            Image bi = img.getScaledInstance(squares[i][j].getWidth() - 10, squares[i][j].getHeight() - 10, Image.SCALE_SMOOTH);
            ImageIcon newIcon = new ImageIcon(bi);

            answerLabel.setIcon(newIcon);

            if (MainWindow.ChessField[i][j].BeatenByWhite > 0 || MainWindow.ChessField[i][j].BeatenByBlack > 0)
            {
                answerLabel.setLayout(new BorderLayout());
                JLabel resultLabel = new JLabel("" + abs(result));
                resultLabel.setFont(new Font("Helvetica", Font.BOLD, 25));
                answerLabel.add(resultLabel, JLabel.CENTER);

                if (result > 0) {
                    resultLabel.setForeground(Color.WHITE);
                } else if (result < 0) {
                    resultLabel.setForeground(color);
                } else if (result == 0) {
                    resultLabel.setForeground(Color.LIGHT_GRAY);
                }
            }
        }
        else
        {
            if (MainWindow.ChessField[i][j].BeatenByWhite > 0 || MainWindow.ChessField[i][j].BeatenByBlack > 0)
            {
                answerLabel.setLayout(new BorderLayout());
                answerLabel.setFont(new Font("Helvetica", Font.BOLD, 25));
                answerLabel.setText("" + abs(result));

                if (result > 0) {
                    answerLabel.setForeground(Color.WHITE);
                } else if (result < 0) {
                    answerLabel.setForeground(color);
                } else if (result == 0) {
                    answerLabel.setForeground(Color.LIGHT_GRAY);
                }
            }
        }

        return answerLabel;
    }
}
