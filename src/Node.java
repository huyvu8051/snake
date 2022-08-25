import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Node {

    private static Random random = new Random();
    private int x;
    private int y;
    private Color color;

    public Node() {
    }

    public Node(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.color =  new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }


}
