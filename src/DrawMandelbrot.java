import javax.swing.*;
import java.awt.*;

class DrawMandelbrot extends JPanel {
    /* Цвета */
    Color myColorRed = Color.RED;
    Color myColorBlack = Color.BLACK;
    /* Переменные для мировой системы (логической) координат */
    Double Xmin, Xmax, Ymin, Ymax;
    /* Физические размеры панелей */
    int gpnlW = 600;
    int gpnlH = 600;

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        /* Сглаживание */
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        DrawBenoitMandelbrot(MainWindow.vCntI, g);
    }

    /* Вывод на физический экран ТОЧКИ по мировым (логическим) координатам */
    private void DrawToScreenPoint(double mylX1, double mylY1, Color myColor, Graphics myG) {
        /* Физические размеры панелей */
        int pnlW = gpnlW - 1;
        int pnlH = gpnlH - 1;

        /* Физические F координаты экрана */
        int xF1, yF1;

        xF1 = (int) ((mylX1 - Xmin) * (pnlW / (Xmax - Xmin)));
        yF1 = (int) (pnlH - ((mylY1 - Ymin) * (pnlH / (Ymax - Ymin))));

        myG.setColor(myColor);
        myG.drawLine(xF1, yF1, xF1, yF1 + 1);
    }

    public void DrawBenoitMandelbrot(int myDepth, Graphics myG) {

        /* Переменные */
        int s;                                          /* размер окна вывода s x s */
        int p;                                          /* число пикселей по координате */
        int iter;                                       /* счетчик итераций */
        int depth;                                      /* количество итераций, чем больше тем глубже картинка (20 по умолчанию для отладки) */
        double zR, zI;                                  /* вещественная и мнимая часть комплексного числа */
        double cR, cI, calc;                            /* мнимая и вещественная  часть */
        int red_color;                                  /* цвет */

        /* Присвоение Depth из myDepth*/
        depth = myDepth;


        /* Находим минимум из высоты и ширины окна и умножаем на два и получаем минимальный размер окна */
        s = ((((gpnlH)) < ((gpnlW)) ? ((gpnlH)) : ((gpnlW))));

        /* Установка мировой системы координат (логического окна) */
        Xmin = -((double) s / 2);
        Ymin = -((double) s / 2);
        Xmax = ((double) s / 2);
        Ymax = (double) s / 2;

        /* Ограничиваем число пикселей как 45% от окна вывода */
        p = (int) ((s / 2) * 0.45);

        for (int x = -(s / 2); x < (s / 2); x++) {
            /* присваиваем мнимой части с - x/s */
            cI = ((double) x) / ((double) p);

            for (int y = -(s / 2); y < (s / 2); y++) {
                /* присваиваем вещественной части с - y/s */
                cR = ((double) y) / ((double) p);
                /* присваиваем вещественной и мнимой части z - 0 */
                zI = zR = 0.0;
                /* Вычисляем множество Мандельброта */
                iter = 0;
                while (iter <= depth) {
                    iter = iter + 1;
                    calc = zR * zR - zI * zI;
                    zI = 2.0 * zR * zI + cI;
                    zR = calc + cR;
                    /* если | z | слишком велико, то выходим из цикла */
                    if (((zR * zR) + (zI * zI)) > 1.0E14) break;
                }
                if (iter < depth) {
                    /* iter % 8 = остаток от деления + 1*/
                    red_color = (iter % 8) + 1;
                    /* RGB Color) */
                    myColorRed = new Color(32 * red_color - 1, 0, 0);
                    DrawToScreenPoint(y, x, myColorRed, myG);
                } else {
                    /* Внутренняя точка и закрашиваем её в чёрный цвет */
                    DrawToScreenPoint(y, x, myColorBlack, myG);
                }
            }
        }
    }
}
