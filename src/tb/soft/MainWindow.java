package tb.soft;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * klasa główna zawierająca metodę statyczną main
 */
public class MainWindow extends JFrame {
    private Sprajt shape;
    private Color color;
    private JSlider pion;
    private JSlider poziom;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow frame = new MainWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        });
    }

    public MainWindow() throws HeadlessException {
        this("untitled");
    }

    public void showCanva(){

        JPanel contentPane = new JPanel();

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        MyPanel panel = new MyPanel();
        panel.setBounds(10, 11, 569, 353);
        contentPane.add(panel);

        poziom = new JSlider();
        poziom.setPaintTicks(true);
        poziom.setMajorTickSpacing(10);
        poziom.setBounds(10, 375, 569, 25);
        poziom.setMinimum(0);
        poziom.setMaximum(panel.getWidth());
        poziom.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(panel.hasSprajt()) {
                    panel.sp.setX(poziom.getValue());
                    panel.repaint();
                }
            }
        });
        contentPane.add(poziom);

        pion = new JSlider();
        pion.setPaintTicks(true);
        pion.setMajorTickSpacing(10);
        pion.setOrientation(SwingConstants.VERTICAL);
        pion.setBounds(600, 11, 25, 353);
        pion.setMinimum(0);
        pion.setMaximum(panel.getHeight());
        pion.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(panel.hasSprajt()) {
                    panel.sp.setY(pion.getMaximum() - pion.getValue());
                    panel.repaint();
                }
            }
        });
        contentPane.add(pion);

        JButton pokBtn = new JButton("Ukryj");
        JButton dodBtn = new JButton("Dodaj");

        pokBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (panel.isRysuj()) {
                    panel.setRysuj(false);
                    pokBtn.setText("Pokaż");
                    pokBtn.setToolTipText("Pokaż rysowany element");
                } else {
                    panel.setRysuj(true);
                    pokBtn.setText("Ukryj");
                    pokBtn.setToolTipText("Ukryj rysowany element");
                }
                panel.repaint();
            }
        });
        pokBtn.setBounds(10, 417, 90, 23);
        pokBtn.setEnabled(false);
        contentPane.add(pokBtn);

        dodBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pokBtn.setEnabled(true);
                panel.addSprajt(shape);
                panel.addColor(color);
                panel.repaint();
            }
        });

        dodBtn.setBounds(105, 417, 90, 23);
        contentPane.add(dodBtn);
        contentPane.setVisible(false);
        contentPane.setVisible(true);

        JMenuBar mb = new JMenuBar();
        setJMenuBar(mb);
        mb.add(setUpShapesMenu());
        mb.add(setUpColorMenu());
    }

    public JMenu setUpColorMenu(){
        JMenu colors_menu = new JMenu("Kolor");
        colors_menu.setMnemonic('k');

        JMenuItem blue = new JMenuItem("Niebieski");
        blue.setMnemonic('N');
        blue.setAccelerator(KeyStroke.getKeyStroke("control N"));
        blue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                color = Color.BLUE;
            }
        });

        JMenuItem green = new JMenuItem("Zielony");
        green.setMnemonic('z');
        green.setAccelerator(KeyStroke.getKeyStroke("control Z"));
        green.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                color = Color.GREEN;
            }
        });

        JMenuItem pink = new JMenuItem("Różowy");
        pink.setMnemonic('r');
        pink.setAccelerator(KeyStroke.getKeyStroke("control R"));
        pink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                color = Color.PINK;
            }
        });
        JMenuItem black = new JMenuItem("Czarny");
        black.setMnemonic('c');
        black.setAccelerator(KeyStroke.getKeyStroke("control C"));
        black.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                color = Color.BLACK;
            }
        });

        JMenuItem white = new JMenuItem("Biały");
        white.setMnemonic('b');
        white.setAccelerator(KeyStroke.getKeyStroke("control B"));
        white.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                color = Color.WHITE;
            }
        });

        colors_menu.add(blue);
        colors_menu.add(green);
        colors_menu.add(pink);
        colors_menu.add(white);
        colors_menu.add(black);
        return colors_menu;
    }

    public JMenu setUpShapesMenu(){
        JMenu shapes_menu = new JMenu("Kształt");
        shapes_menu.setMnemonic('k');

        JMenuItem pilka = new JMenuItem("Piłka");
        pilka.setMnemonic('p');
        pilka.setAccelerator(KeyStroke.getKeyStroke("control P"));
        pilka.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                shape = new Pilka(poziom.getValue(), pion.getMaximum() - pion.getValue());
            }
        });

        JMenuItem rect = new JMenuItem("Prostokąt");
        rect.setMnemonic('r');
        rect.setAccelerator(KeyStroke.getKeyStroke("control shift P"));
        rect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                shape = new rectangle(poziom.getValue(), pion.getMaximum() - pion.getValue());
            }
        });

        JMenuItem circle = new JMenuItem("Okrąg");
        circle.setMnemonic('o');
        circle.setAccelerator(KeyStroke.getKeyStroke("control O"));
        circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                shape = new circle(poziom.getValue(), pion.getMaximum() - pion.getValue());
            }
        });

        shapes_menu.add(pilka);
        shapes_menu.add(rect);
        shapes_menu.add(circle);
        return shapes_menu;
    }

    public MainWindow(String title) throws HeadlessException {
        super(title);
        Random generator = new Random();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(670, 510);

        JPanel contentPane = new JPanel();

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JButton run = new JButton();
        run.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (e.getX() < run.getWidth() - 20) {
                    run.setLocation(generator.nextInt(600), generator.nextInt(465));
                }
            }
        });
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showCanva();
            }
        });
        run.setBounds(240, 233, 80, 25);
        run.setText("Paint");
        contentPane.add(run);

        JButton restart = new JButton("Restart");
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                run.setLocation(240,233);
            }
        });
        restart.setBounds(330, 233, 80, 25);
        contentPane.add(restart);

    }
}
