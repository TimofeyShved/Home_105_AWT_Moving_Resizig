package com.AWT;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Surface extends JPanel {

    // переменные
    private Point2D[] points;
    private final int SIZE = 8;
    private int pos;

    public Surface() {// инициализация
        initUI();
    }

    private void initUI() {

        addMouseListener(new ShapeTestAdapter()); // добавляем действия для мышки
        addMouseMotionListener(new ShapeTestAdapter());
        pos = -1;

        points = new Point2D[2]; // указывем точки
        points[0] = new Point2D.Double(50, 50);
        points[1] = new Point2D.Double(150, 100);
    }

    private void doDrawing(Graphics g) { // ---------------------------------------------------- прорисовка

        Graphics2D g2 = (Graphics2D) g; // графика

        for (Point2D point : points) { // цикл по точкам
            double x = point.getX() - SIZE / 2;
            double y = point.getY() - SIZE / 2;
            g2.fill(new Rectangle2D.Double(x, y, SIZE, SIZE)); // отрисовка квадратов, по точкам
        }

        Rectangle2D r = new Rectangle2D.Double(); // создание основного квадрата
        r.setFrameFromDiagonal(points[0], points[1]);

        g2.draw(r); // отрисовка
    }

    @Override
    public void paintComponent(Graphics g) { // отрисовка компонента
        super.paintComponent(g);
        doDrawing(g);
    }

    private class ShapeTestAdapter extends MouseAdapter { // --------------------------------------- действия на мышке

        @Override
        public void mousePressed(MouseEvent event) { // нажатие

            Point p = event.getPoint(); // точка из выбора

            for (int i = 0; i < points.length; i++) { // цикл по точкам

                double x = points[i].getX() - SIZE / 2; // новые координаты
                double y = points[i].getY() - SIZE / 2;

                Rectangle2D r = new Rectangle2D.Double(x, y, SIZE, SIZE); // новый квадрат

                if (r.contains(p)) {  // позиция нового квадрат соответсвует точке?
                    pos = i;
                    return;
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent event) { // отпустили
            pos = -1;
        }

        @Override
        public void mouseDragged(MouseEvent event) { // переташили
            if (pos == -1) { // если не изменилась
                return;
            }

            points[pos] = event.getPoint();  // новая точка
            repaint(); // перерисовать
        }
    }
}

public class Main extends JFrame { // ------------------------------------------------------------ главный класс

    public Main()  {// конструктор

        initUI();
    }

    private void initUI() {     // инициализация

        add(new Surface()); // мой объект

        setTitle("Resize rectangle"); // заголовок
        setSize(300, 300); // размеры
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // выход
        setLocationRelativeTo(null); // что бы форма по центру была
    }

    public static void main(String[] args) { // запуск всей программы

        EventQueue.invokeLater(new Runnable() { // поток

            @Override
            public void run() { // запуск
                Main ex = new Main(); // создание нашего класса
                ex.setVisible(true); // видимость
            }
        });
    }
}
