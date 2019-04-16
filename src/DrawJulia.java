import javax.swing.*;
import java.awt.*;

class DrawJulia extends JPanel {
    /* Цвет */
    Color myColor = Color.WHITE;
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
        DrawJulia(MainWindow.vCntI, MainWindow.vRe, MainWindow.vIm, g);
    }

    /* Вывод на физический экран ТОЧКИ по физическим координатам */
    public void myDrawPoint(int mylX1, int mylY1, Color myColor, Graphics myG) {
        myG.setColor(myColor);
        myG.drawLine(mylX1, mylY1, mylX1, mylY1 + 1);
    }

    /* Построение фрактала Заполняющее множество Жюлия */
    public void DrawJulia(int myDepth, double myCr, double myCi, Graphics myG) {
        /* при каждой итерации, вычисляется Z[n+1] = Z[n]^2 + С */
        double cR, cI;                                /* вещественная  и мнимая части постоянной C */
        double newR, newI, oldR, oldI;                /* вещественная и мнимая части старой и новой */
        int iter;                                    /* счетчик итераций */
        int depth;                                /* количество итераций, чем больше тем глубже картинка (1000 по умолчанию для отладки) */
        int pnlW = gpnlW - 1;                            /* Физические (экранные) размеры панели X */
        int pnlH = gpnlH - 1;                            /* Физические (экранные) размеры панели YX */
        double dX, dY;                                /* Приращение аргументов */

        /* Присвоение Depth из myDepth*/
        depth = myDepth;

        /* выбираем значение константы С, это определяет форму фрактала Жюлиа */
        cR = myCr;
        cI = myCi;

        /* Установка мировой системы координат (логического окна) для Жюлиа */
        Xmin = -2.0;
        Ymin = -2.0;
        Xmax = 2.0;
        Ymax = 2.0;

        /* Находим приращение аргументов исходя из физических (экранных размеров) */
        dX = (Xmax - Xmin) / pnlW;
        dY = (Ymax - Ymin) / pnlH;

        /* Проход по всем пикселям панели (x,y) и попиксельный вывод с обратными преобразование в */

        for (int x = 0; x < pnlW; x++)
            for (int y = 0; y < pnlH; y++) {

                /* вычисляется реальная и мнимая части числа z */
                /* Преобразуем экранные координаты в комплексные  */
                newR = Xmin + x * dX;
                newI = Ymin + y * dY;

                /* iter представляет собой число итераций */
                iter = 0;
                while (iter < depth) {
                    iter = iter + 1;
                    /* запоминаем значение предыдущей итерации */
                    oldR = newR;
                    oldI = newI;
                    /* для текущей итерации вычисляем действительную и мнимую части */
                    newR = (oldR * oldR) - (oldI * oldI) + cR;
                    newI = 2.0 * oldR * oldI + cI;
                    /* если точка находится за пределами круга с радиусом 2 - останавливаем цикл */
                    if ((newR * newR + newI * newI) > 4) break;
                }

                /* Раскрашиваем карандаш */
                myColor = new Color((iter * 8) % 255, 0, (iter * 8) % 255);
                /* Рисуем сразу на физическом экране, перевернув ось Y: pnlH-y */
                myDrawPoint(x, pnlH - y, myColor, myG);
            }
    }
}
