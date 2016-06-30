import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Рома on 29.06.2016.
 */

public class MainWindow extends JFrame {
    static Cell [][] ChessField = new Cell[8][8];

    String [][] ChessField1 =
            {
                    {"", "", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", "", ""}
            };

    String [][] ChessField2 =
            {
                    {"", "", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", "", ""},
                    {"", "", "", "", "", "", "", ""}
            };

    static byte Arr[][] =
            {
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0}
            };

    String[] Column_names = {"a", "b", "c", "d", "e", "f", "g", "h"};

    JPanel panel = new JPanel();
    JPanel tables_panel = new JPanel();
    JPanel buttons_panel = new JPanel();

    JPanel table1_panel = new JPanel();
    JPanel table2_panel = new JPanel();

    JPanel button1_panel = new JPanel();
    JPanel button2_panel = new JPanel();
    JPanel button3_panel = new JPanel();

    JTable table1 = new JTable();
    JTable table2 = new JTable();

    JButton button1 = new JButton("Сохранить в файл");
    JButton button2 = new JButton("Загрузить из файла");
    JButton button3 = new JButton("Рассчитать");

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


    public MainWindow() {
        panel.setLayout(new BorderLayout());
        panel.add(tables_panel, BorderLayout.CENTER);
        panel.add(buttons_panel, BorderLayout.EAST);

        tables_panel.setLayout(new GridLayout(1, 2));
        tables_panel.add(table1_panel);
        tables_panel.add(table2_panel);
        tables_config();


        table1_panel.setLayout(new GridBagLayout());
        table1_panel.add(table1);
        table2_panel.setLayout(new GridBagLayout());
        table2_panel.add(table2);

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

        this.button3.addActionListener(new MyButtonListener3());
    }                                                                                           // Настройка внешнего вида окна

    public void tables_config()                                                                                         // Создание таблиц по массивам, заполнение значениями
    {


        table1 = new JTable(ChessField1, Column_names);
        table2 = new JTable(ChessField2, Column_names);

        table1.setFont(new Font("Helvetica", Font.PLAIN, 15));
        table2.setFont(new Font("Helvetica", Font.PLAIN, 15));

        table1.setRowHeight(40);
        table2.setRowHeight(40);

        table1.setMinimumSize(new Dimension(320,320));
        table1.setMaximumSize(new Dimension(320,320));

        table2.setMinimumSize(new Dimension(320,320));
        table2.setMaximumSize(new Dimension(320,320));

        for (int i = 0; i < 8; i++) {
            table1.getColumnModel().getColumn(i).setCellRenderer(new Renderer());
            table2.getColumnModel().getColumn(i).setCellRenderer(new Renderer());
        }

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
            {
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

        for (int i = 1; i < 8; i++)
        {
            if (dir1 && column + i < 8)
            {
                if (isWhite)
                    ChessField[row][column + i].BeatenByWhite++;
                else
                    ChessField[row][column + i].BeatenByBlack++;

                if (Arr[row][column + i] == 1)
                    dir1 = false;
            }

            if (dir2 && column - i >= 0)
            {
                if (isWhite)
                    ChessField[row][column - i].BeatenByWhite++;
                else
                    ChessField[row][column - i].BeatenByBlack++;

                if (Arr[row][column - i] == 1)
                    dir2 = false;
            }

            if (dir3 && row + i < 8)
            {
                if (isWhite)
                    ChessField[row + i][column].BeatenByWhite++;
                else
                    ChessField[row + i][column].BeatenByBlack++;

                if (Arr[row + i][column] == 1)
                    dir3 = false;
            }

            if (dir1 && row - i >= 0)
            {
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

        for (int i = 1; i < 8; i++)
        {
            if (dir1 && row + i < 8 && column + i < 8)
            {
                if (isWhite)
                    ChessField[row + i][column + i].BeatenByWhite++;
                else
                    ChessField[row + i][column + i].BeatenByBlack++;

                if (ChessField[row + i][column + i].UnitName != "")
                    dir1 = false;
            }

            if (dir2 && row - i >= 0 && column - i >= 0)
            {
                if (isWhite)
                    ChessField[row - i][column - i].BeatenByWhite++;
                else
                    ChessField[row - i][column - i].BeatenByBlack++;

                if (ChessField[row - i][column - i].UnitName != "")
                    dir2 = false;
            }

            if (dir3 && row + i < 8 && column - i >= 0)
            {
                if (isWhite)
                    ChessField[row + i][column - i].BeatenByWhite++;
                else
                    ChessField[row + i][column - i].BeatenByBlack++;

                if (ChessField[row + i][column - i].UnitName != "")
                    dir3 = false;
            }

            if (dir1 && row - i >= 0 && column + i < 8)
            {
                if (isWhite)
                    ChessField[row - i][column + i].BeatenByWhite++;
                else
                    ChessField[row - i][column + i].BeatenByBlack++;

                if (ChessField[row - i][column + i].UnitName != "")
                    dir4 = false;
            }
        }
    }


    public static void PawnUpdate(boolean isWhite, int row, int column)                                                 // Обновление для пешки
    {
        if (isWhite)
        {
            if (row - 1 > 0) {
                if (column - 1 > 0)
                    ChessField[row - 1][column - 1].BeatenByWhite++;
                if (column + 1 < 8)
                    ChessField[row - 1][column + 1].BeatenByWhite++;
            }
        }
        else
        {
            if (row + 1 < 8) {
                if (column - 1 > 0)
                    ChessField[row + 1][column - 1].BeatenByBlack++;
                if (column + 1 < 8)
                    ChessField[row + 1][column + 1].BeatenByBlack++;
            }
        }
    }

    /**
     * Ниже описана проверка корректности координат
     */
    public static boolean areCorrectCoordinates(int x, int y)
    {
        if (x >= 0 && x < 8 && y >= 0 && y < 8)
            return true;
        else
            return false;
    }

    /**
     * Ниже описана обработка событий нажатий на кнопки
     */
    private class MyButtonListener3 implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            char ch1, ch2;
            boolean isWhite;

            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                    if (!ChessField2[i][j].equals(""))
                        Arr[i][j] = 1;

            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                    if (!ChessField2[i][j].equals(""))
                    {
                        ChessField[i][j].UnitName = Transform(ChessField2[i][j]);
                        ch1 = ChessField2[i][j].charAt(0);
                        ch2 = ChessField2[i][j].charAt(1);

                        if (ch1 == 'w')
                            isWhite = true;
                        else
                            isWhite = false;


                        if (ch2 == 'K')
                            KingUpdate(isWhite, i, j);
                        else if(ch2 == 'Q')
                            QueenUpdate(isWhite, i, j);
                        else if(ch2 == 'R')
                            RookUpdate(isWhite, i, j);
                        else if(ch2 == 'N')
                            KnightUpdate(isWhite, i, j);
                        else if(ch2 == 'B')
                            BishopUpdate(isWhite, i, j);
                        else if(ch2 == 'p')
                            PawnUpdate(isWhite, i, j);
                    }

            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                    if ((ChessField[i][j].BeatenByWhite - ChessField[i][j].BeatenByBlack) > 0)
                    {
                        ChessField1[i][j] = ChessField[i][j].getUnitName() + (ChessField[i][j].BeatenByWhite - ChessField[i][j].BeatenByBlack);

                    }
                    else if ((ChessField[i][j].BeatenByWhite - ChessField[i][j].BeatenByBlack) < 0) {
                        ChessField1[i][j] = ChessField[i][j].getUnitName() + (-ChessField[i][j].BeatenByWhite + ChessField[i][j].BeatenByBlack);
                        table1.getColumnModel().getColumn(i).setCellRenderer(new Renderer());
                    }
                    else if (ChessField[i][j].BeatenByWhite != 0) {
                        ChessField1[i][j] = ChessField[i][j].getUnitName() + "0";
                    }
                    else
                        ChessField1[i][j] = ChessField[i][j].getUnitName();
        }

    }

    public String Transform(String Unit)
    {

        switch (Unit)
        {
            case "wK":
                return "♔";
            case "bK":
                return "♚";
            case "wQ":
                return "♕";
            case "bQ":
                return "♛";
            case "wR":
                return "♖";
            case "bR":
                return "♜";
            case "wN":
                return "♘";
            case "bN":
                return "♞";
            case "wB":
                return "♗";
            case "bB":
                return "♝";
            case "wp":
                return "♙";
            case "bp":
                return "♟";
            default:
                System.out.println("Something went wrong with the input format!");
                System.exit(1);
                break;
        }
        return "";
    }

}