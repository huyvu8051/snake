import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 500;
    static final int UNIT_SIZE = 25;
    static final int DELAY = 60;

    final List<Node> bodyParts2 = new ArrayList<>();


    Node apple;
    char  direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;


    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());




        startGame();
    }

    public void startGame() {

        for (int i = 0; i < 3; i++) {
            bodyParts2.add(new Node(UNIT_SIZE, UNIT_SIZE));
        }

        newApple();
        running = true;
        timer = new Timer(DELAY , this);
        timer.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // print grid line
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }

        for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
        }


        // print apple
        g.setColor(apple.getColor());
        g.fillOval(apple.getX(), apple.getY(), UNIT_SIZE, UNIT_SIZE);


        // print snake body
        for (int i = 0; i < bodyParts2.size(); i++) {
            if (i == -1) {
                Node head = bodyParts2.get(0);
                g.setColor(Color.BLUE);
                g.fillRect(head.getX(), head.getY(), UNIT_SIZE, UNIT_SIZE);
            } else {
                Node body = bodyParts2.get(i);
                g.setColor(body.getColor());
                g.fillRect(body.getX(), body.getY(), UNIT_SIZE, UNIT_SIZE);
            }
        }


        // print total score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + bodyParts2.size(), (SCREEN_WIDTH - metrics.stringWidth("Score: " + bodyParts2.size())) / 2, g.getFont().getSize());

        if (!running) {
            gameOver(g);
        }
    }

    public void newApple() {
        int appleX1 = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;

        int appleY1 = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;


        apple = new Node(appleX1, appleY1, Color.red);


    }

    public void move() {

        for (int i = bodyParts2.size() - 1; i > 0; i--) {
            Node node = bodyParts2.get(i);
            Node node2 = bodyParts2.get(i - 1);

            node.setX(node2.getX());
            node.setY(node2.getY());
        }

        Node head = bodyParts2.get(0);

        int x1 = head.getX();
        int y1 = head.getY();



        switch (direction) {
            case 'U':
                head.setY(((y1 + SCREEN_HEIGHT) - UNIT_SIZE) % SCREEN_HEIGHT);
                break;
            case 'D':
                head.setY((y1 + UNIT_SIZE) % SCREEN_HEIGHT);
                break;
            case 'L':
                head.setX(((x1 + SCREEN_WIDTH) - UNIT_SIZE) % SCREEN_WIDTH);
                break;
            case 'R':
                head.setX((x1 + UNIT_SIZE) % SCREEN_WIDTH);
                break;
        }

//        System.out.println("some think new");
    }

    public void checkApple() {

        Node head = bodyParts2.get(0);

        Node tail = bodyParts2.get(bodyParts2.size() - 1);

        if (head.equals(apple)) {
            bodyParts2.add(new Node(tail.getX(), tail.getY()));
            newApple();
        }
    }

    public void checkCollisions() {
        Node head = bodyParts2.get(0);

        for(int i = bodyParts2.size() - 1; i > 0; i --){

            Node body = bodyParts2.get(i);

            if(head.equals(body)) running = false;
        }



        if (!running) timer.stop();
    }

    public void gameOver(Graphics g) {

        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + bodyParts2.size(), (SCREEN_WIDTH - metrics1.stringWidth("Score: " + bodyParts2.size())) / 2, g.getFont().getSize());


        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game over", (SCREEN_WIDTH - metrics2.stringWidth("Game over")) / 2, SCREEN_HEIGHT / 2);
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (direction != 'R') direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (direction != 'L') direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (direction != 'D') direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (direction != 'U') direction = 'D';
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();

        }

        repaint();
    }
}
