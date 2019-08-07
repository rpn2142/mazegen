import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class Lines extends JComponent{

    public static final int MARGIN = 50;
    public static final int N = 3;
    public static final int GAP = 150;

    private static class Line{
        final int x1;
        final int y1;
        final int x2;
        final int y2;
        final Color color;

        public Line(int x1, int y1, int x2, int y2, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }
    }

    private final LinkedList<Line> lines = new LinkedList<Line>();
    private static final MazeGenerator mazeGenerator = new MazeGenerator();

    public void addLine(int x1, int x2, int x3, int x4) {
        addLine(x1, x2, x3, x4, Color.black);
    }

    public void addLine(int x1, int x2, int x3, int x4, Color color) {
        lines.add(new Line(x1,x2,x3,x4, color));
        repaint();
    }

    public void clearLines() {
        lines.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Line line : lines) {
            g.setColor(line.color);
            //g.drawLine(line.x1, line.y1, line.x2, line.y2);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g2.draw(new Line2D.Float(line.x1, line.y1, line.x2, line.y2));
        }

        addImages(g, this);

    }

    public static void main(String[] args) {
        final Lines comp = new Lines();
        comp.setPreferredSize(new Dimension(1800, 1800));
        initComponent(comp);



        JFrame testFrame = new JFrame();
        testFrame.setBackground(Color.LIGHT_GRAY);
        //testFrame.setUndecorated(true);
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);
        testFrame.pack();
        BufferedImage bi = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bi.createGraphics();
        comp.print(graphics);
        graphics.dispose();
        testFrame.dispose();

        File outputfile = new File("saved.png");
        try {
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initComponent(Lines comp) {
        int[][] maze = mazeGenerator.printMaze(N);
        for( int i=0; i<maze.length; i++)
            for( int j=0; j<maze.length; j++) {
                int square = maze[i][j];
                if( (square&0b0001) == 1 )
                    drawLine(comp, i, j,i+1 , j);
                if( (square&0b0010) > 0 )
                    drawLine(comp, i+1, j,i+1 , j+1);
                if( (square&0b0100) > 0 )
                    drawLine(comp, i, j+1,i+1 , j+1);
                if( (square&0b1000) > 0 )
                    drawLine(comp, i, j,i , j+1);
            }

    }

    private static void addImages(Graphics comp, ImageObserver io) {

        comp.drawImage(getImage("bee.jpg"), MARGIN+20, MARGIN+20, io);
        comp.drawImage(getImage("flower.png"), MARGIN+(N-1)*GAP +20, MARGIN+(N-1)*GAP +15, io);

    }

    private static BufferedImage getImage(String name) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new FileInputStream(name));
        }catch(FileNotFoundException e) {
            System.out.println("Could not find file!");
            e.printStackTrace();
        }catch(IOException e) {
            System.out.println("Could not read file!");
            e.printStackTrace();
        }
        return image;
    }

    private static void drawLine(Lines comp, int srcX, int srcY, int destX, int destY) {
        int x1 = MARGIN +srcY* GAP;
        int x2 = MARGIN +destY* GAP;
        int y1 = MARGIN +srcX* GAP;
        int y2 = MARGIN +destX* GAP;
        comp.addLine(x1, y1, x2, y2, Color.BLACK);
    }

}