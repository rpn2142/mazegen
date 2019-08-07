import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class Lines extends JComponent{

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
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
        }
    }

    public static void main(String[] args) {
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final Lines comp = new Lines();
        comp.setPreferredSize(new Dimension(1800, 1800));
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        JButton newLineButton = new JButton("New Line");
        JButton clearButton = new JButton("Clear");
        buttonsPanel.add(newLineButton);
        buttonsPanel.add(clearButton);
        testFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        newLineButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int[][] maze = mazeGenerator.printMaze(3);
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
        });
        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                comp.clearLines();
            }
        });
        testFrame.pack();
        testFrame.setVisible(true);
    }

    private static void drawLine(Lines comp, int srcX, int srcY, int destX, int destY) {
        int margin = 50;
        int gap = 100;
        int x1 = margin+srcY*gap;
        int x2 = margin+destY*gap;
        int y1 = margin+srcX*gap;
        int y2 = margin+destX*gap;
        comp.addLine(x1, y1, x2, y2, Color.BLACK);
    }

}