/*
 * Software: LotteryAdvisor
 * Module: LotteryVisualPlayBox class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 13.3.2012
 *
 */
package oh3ebf.lottery.components;

import oh3ebf.lottery.interfaces.LotteryVisualPlayBoxSelectionIF;
import java.awt.*;
import org.apache.log4j.Logger;

public class LotteryVisualPlayBox extends javax.swing.JPanel {

    private static final long serialVersionUID = 7526472295622776147L;
    private static Logger logger;
    private final int number;
    private final boolean primaryNumber;
    private boolean selected = false;
    private boolean resultNumber = false;
    private boolean resultSecondaryNumber = false;
    private final LotteryVisualPlayBoxSelectionIF selection;
    private int state;

    /**
     * Creates new form kenoVisualPlayBox
     *
     * @param num number of boxes to draw
     * @param s
     *
     */
    public LotteryVisualPlayBox(int num, boolean primaryNumber, LotteryVisualPlayBoxSelectionIF s) {

        // get logger instance for this class".
        logger = Logger.getLogger(LotteryVisualPlayBox.class);

        number = num;
        selection = s;
        state = 0;
        this.primaryNumber = primaryNumber;
        
        initComponents();
    }

    /**
     * Function draws graphic objects to panel
     *
     * @param g reference panels graphics object
     *
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int w = this.getSize().width;
        int h = this.getSize().height;

        // remove old drawign objects
        clear(g);

        // get metrics from the graphics
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        // get the height of a line of text in this font and render context
        int hgt = metrics.getHeight();
        // get the advance of my text in this font and render context
        int adv = metrics.stringWidth("1");

        // set drawing color
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(1));

        // draw new values
        if (number <= 9) {
            g2d.drawString(String.valueOf(number), adv, hgt);
        } else {
            g2d.drawString(String.valueOf(number), adv - 5, hgt);
        }

        // draw selection line
        if (selected) {
            // set drawing color
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(1, 1, w, h);
        }

        switch (state) {
            case 0:
                // no number
                this.setBackground(new java.awt.Color(238, 238, 238));
                break;
            case 1:
                // primary number
                this.setBackground(new java.awt.Color(204, 255, 204));
                break;
            case 2:
                // secondary number
                this.setBackground(new java.awt.Color(51, 255, 204));
                break;
            case 3:
                // primary and secondary number
                this.setBackground(new java.awt.Color(255,204,204));
                break;                
            default:
                this.setBackground(new java.awt.Color(238, 238, 238));
        }
    }

    /**
     * Function sets component selected state
     *
     * @param select
     */
    public void setSelection(boolean select) {
        // update component state
        selected = select;

        repaint();
    }

    /**
     * Function set component member state in result numbers
     *
     * @param result
     */
    public void setPrimaryResult(boolean result) {
        resultNumber = result;

        // update component in result number
        state = result ? 1 : 0;

        repaint();
    }

    /**
     * Function set component member state in secondary result numbers
     *
     * @param result
     */
    public void setSecondaryResult(boolean result) {
        resultSecondaryNumber = result;

        if (resultNumber) {
            // primary and secondary number are same
            state = 3;
        } else {
            // update component in result number
            state = result ? 2 : 0;
        }

        repaint();
    }

    /**
     * Function clears previous drawing objects from screen
     *
     * @param g reference to graphics
     *
     */
    protected void clear(Graphics g) {
        // super.paintComponent clears offscreen pixmap, since we're using double buffering by default.
        super.paintComponent(g);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setMaximumSize(new java.awt.Dimension(20, 20));
        setMinimumSize(new java.awt.Dimension(20, 20));
        setPreferredSize(new java.awt.Dimension(20, 20));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // set selection when mouse clicked
        selected = (selected == false) && (selection.isSelectionEnabled(primaryNumber));

        // update state
        selection.setSelected(selected, primaryNumber, number);

        // update view
        repaint();
    }//GEN-LAST:event_formMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}