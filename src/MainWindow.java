/*
 * Тимуркаева Л.М. Группа 381505
 * Построение фракталов Мандельброта и Жюлия
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

class MainWindow extends JFrame {
    /* Глобальные переменные, используются как входные параметры для построения фракталов */
    public static int vCntI = 100;
    public static double vRe = -0.1244D;
    public static double vIm = 0.7560D;
    public static Locale locRU = new Locale("ru", "RU");
    public static DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(locRU);
    /* Кнопки */
    private JButton btnMandelbrot = new JButton("Мандельброт");
    private JButton btnJulia = new JButton("Жюлия");
    /* Количество итераций */
    private JTextField cntIt = new JTextField(Integer.toString(vCntI), 5);
    private JLabel lbIt = new JLabel("Кол.итер.");
    /* Для Жюлия */
    private JLabel lblC = new JLabel(" C =");
    private JTextField tfRe = new JTextField(DecimalFormat.getInstance(locRU).format(vRe), 5);
    private JLabel lblRe = new JLabel("Re");
    private JTextField tfIm = new JTextField(DecimalFormat.getInstance(locRU).format(vIm), 5);
    private JLabel lblIm = new JLabel("Im");
    private JPanel pnlInit = new JPanel();
    /* Меню Файл->Выход и Справка->О программе */
    private JMenuBar mnBar = new JMenuBar();
    private JMenu mnFile = new JMenu("Файл");
    private JMenu mnInfo = new JMenu("Справка");
    private JMenuItem itmExit = new JMenuItem("Выход");
    private JMenuItem itmAbout = new JMenuItem("О программе");

    public MainWindow() {
        setTitle("Fractals Timurkaeva 381505");
        setSize(600, 700);
        /* Начальное размещение на экране */
        setLocation(200, 50);
        setResizable(false);
        setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /* Формирование меню */
        /* Пункт меню Выход */
        mnFile.add(itmExit);
        /* Привязываем к пункту меню слушателя событий */
        itmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        /* Пункт меню "О программе" */
        mnInfo.add(itmAbout);
        /* Привязываем к пункту меню слушателя событий */
        itmAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Программа построения фракталов " +
                        "Мандельброта и Жюлия.\nАвтор: Тимуркаева Л.М., 2019 г.\nВерсия: v02.01.",
                        "О программе", INFORMATION_MESSAGE);
            }
        });
        /* Добавление в Меню */
        mnBar.add(mnFile);
        mnBar.add(mnInfo);
        /* Добавление меню на панель */
        setJMenuBar(mnBar);

        /* Установка компоновки на окне */
        /* Привязываем к нашей кнопке btnMandelbrot слушателя событий */
        btnMandelbrot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* try - catch используется для потенциально опасного кода
                 * что бы обработать исключения */
                try {
                    vCntI = NumberFormat.getInstance(locRU).parse(cntIt.getText()).intValue();
                } catch (Exception x) {
                    /* Вывод в случае ошибки
                     * помогает программисту понять, где возникла настоящая проблема. */
                    x.printStackTrace();
                }
                /* Построение множества Мандельброта */
                add(new DrawMandelbrot(), BorderLayout.CENTER);
                setVisible(true);
            }
        });
        /* Привязываем к нашей кнопке btnJulia слушателя событий */
        btnJulia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* try - catch используется для потенциально опасного кода
                 * что бы обработать исключения */
                try {
                    vCntI = NumberFormat.getInstance(locRU).parse(cntIt.getText()).intValue();
                    vRe = NumberFormat.getInstance(locRU).parse(tfRe.getText()).doubleValue();
                    vIm = NumberFormat.getInstance(locRU).parse(tfIm.getText()).doubleValue();
                } catch (Exception x) {
                    /* Вывод в случае ошибки
                     * помогает программисту понять, где возникла настоящая проблема. */
                    x.printStackTrace();
                }
                /* Построение множества Жюлия */
                add(new DrawJulia(), BorderLayout.CENTER);
                setVisible(true);
            }
        });
        /* Вводим проверку на ввод только цифр для JTextField cntIt */
        cntIt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != KeyEvent.VK_DELETE)) {
                    /* Поглощаем */
                    e.consume();
                    /* Остальное non-numbers */
                }
            }
        });
        /* Вводим проверку на ввод числа с плавающей точкой JTextField tfRe */
        tfRe.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                CheckTextField(e, tfRe);
            }
        });
        /* Вводим проверку на ввод числа с плавающей точкой JTextField tfIm */
        tfIm.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                CheckTextField(e, tfIm);
            }
        });
        /* Формируем панель начальных параметров pnlInit*/
        /* Добавляем метки, кнопки и текстовые поля в порядке вывода на экран*/
        pnlInit.add(lbIt);
        pnlInit.add(cntIt);
        pnlInit.add(btnMandelbrot);
        pnlInit.add(lblC);
        pnlInit.add(tfRe);
        pnlInit.add(lblRe);
        pnlInit.add(tfIm);
        pnlInit.add(lblIm);
        pnlInit.add(btnJulia);
        /* Добавляем панель pnlInit во фрейм MainWindow
         * и указываем диспетчеру размещения расположение панели на Севере
         */
        add(pnlInit, BorderLayout.NORTH);
        /* Добавляем панель для построения фракталов
         * и указываем диспетчеру размещения расположение панели на Юге
         */
        setVisible(true);
    }

    /* Проверка на ввод числа с плавающей точкой JTextField cJTF */
    public void CheckTextField(KeyEvent cE, JTextField cJTF) {
        char c = cE.getKeyChar();
        /* Флаг поиска разделительного знака */
        boolean TZFound = false;
        /* знаки + и - разрешены первым символом */
        String FirstChar = new String("");
        if (cJTF.getText().length() > 0) FirstChar = cJTF.getText().substring(0, 1);
        boolean badPosition = (FirstChar == "-" && cJTF.getSelectionStart() == 0);
        if (Character.isDigit(c) == true) {
            if (badPosition) cE.consume();
            /* цифрра найдена */
            return;
        }
        /* Найден BackSpace */
        if (c == KeyEvent.VK_BACK_SPACE) return;
        if (c == '-') {
            if (FirstChar == "-") cJTF.setText(cJTF.getText().substring(1));
            if (cJTF.getSelectionStart() == 0) return;
        }
        /* Найден разделитель целой и дробной частей */
        if (cJTF.getText().indexOf(otherSymbols.getDecimalSeparator()) != -1) TZFound = true;
        /* Если разделитель есть, второй не добавляем */
        if (TZFound) {
            cE.consume();
            return;
        }
        /* А первый - можно добавить */
        if (c == otherSymbols.getDecimalSeparator() && !badPosition) return;
        /* Не разрешать дальнейшую обработку */
        cE.consume();
    }

    /* Main */
    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}
