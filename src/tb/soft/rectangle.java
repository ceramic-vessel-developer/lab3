package tb.soft;

import java.awt.*;

public class rectangle extends Sprajt{
    private final int w=40, h=20;


    public rectangle(int x, int y) {
        super(x, y);
    }


    @Override
    public void draw(Graphics2D g2d) {
        g2d.fillRect(x-Math.round(w/2), y-Math.round(h/2), w, h);
    }
}
