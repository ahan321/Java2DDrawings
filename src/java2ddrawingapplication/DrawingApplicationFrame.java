/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2ddrawingapplication;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author acv
 */
public class DrawingApplicationFrame extends JFrame
{

    // Create the panels for the top of the application. One panel for each
    // line and one to contain both of those panels.

    // create the widgets for the firstLine Panel.

    //create the widgets for the secondLine Panel.

    // Variables for drawPanel.

    // add status label
  
    // Constructor for DrawingApplicationFrame
    
    JPanel topPanel = new JPanel();
    JPanel firstLine = new JPanel();
    JPanel secondLine = new JPanel(); 
    JCheckBox dashedCheck = new JCheckBox("Dashed");
    JCheckBox filledCheck = new JCheckBox("Filled");
    JCheckBox gradientCheck = new JCheckBox("Use Gradient");
    JLabel shapeText = new JLabel("Shape:");
    String comboBoxOptions[] = {"Line", "Oval", "Rectangle"};
    JComboBox comboBox = new JComboBox(comboBoxOptions);
    JButton color1Button = new JButton("1st Color...");
    JButton color2Button = new JButton("2nd Color...");
    JButton undoButton = new JButton("Undo");
    JButton clearButton = new JButton("Clear");
    JLabel optionsText = new JLabel("Options:");
    JLabel lineWidthText = new JLabel("Line Width:");
    JSpinner lineWidthSpinner = new JSpinner();
    JLabel dashLengthText = new JLabel("Dash Length:");
    JSpinner dashLengthSpinner = new JSpinner();
    int lineWidth;
    Color color1 = Color.BLUE;
    Color color2 = Color.RED;
    Paint currentColor;
    MyShapes currentShape;
    ArrayList<MyShapes> shapes = new ArrayList<MyShapes>();
    JLabel mousePosLabel = new JLabel("(0,0)");
    JPanel bottomPanel = new JPanel();
    Color setColor;
    
    public DrawingApplicationFrame()
    {
        topPanel.setBackground(Color.cyan);
        
        firstLine.setBackground(Color.cyan);
        firstLine.add(shapeText);
        firstLine.add(comboBox);
        firstLine.add(color1Button);
        firstLine.add(color2Button);
        firstLine.add(undoButton);
        firstLine.add(clearButton);
        
        secondLine.setBackground(Color.cyan);
        secondLine.add(optionsText);
        secondLine.add(filledCheck);
        secondLine.add(gradientCheck);
        secondLine.add(dashedCheck);
        secondLine.add(lineWidthText);
        secondLine.add(lineWidthSpinner);
        secondLine.add(dashLengthText);
        secondLine.add(dashLengthSpinner);
        GridLayout layout = new GridLayout(2,1);
        topPanel.setLayout(layout);
        topPanel.add(firstLine);
        topPanel.add(secondLine);
        Color setColor;
        
        //bottomPanel.add(mousePosLabel);
        
        super.add(topPanel, BorderLayout.NORTH);
        DrawPanel drawPanel = new DrawPanel();
        drawPanel.setBackground(Color.WHITE);
        super.add(drawPanel, BorderLayout.CENTER);
        super.add(mousePosLabel, BorderLayout.SOUTH);
        super.setVisible(true);
        
        color1Button.addActionListener(new color1Selection());
        color2Button.addActionListener(new color2Selection());
        undoButton.addActionListener(new undoButton());
        clearButton.addActionListener(new clearButton());


        
        // add widgets to panels
        
        // firstLine widgets

        // secondLine widgets

        // add top panel of two panels

        // add topPanel to North, drawPanel to Center, and statusLabel to South
        
        //add listeners and event handlers
    }

    public class color1Selection implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            color1 = JColorChooser.showDialog(DrawingApplicationFrame.this, "Choose a color", color1);
        }
    }
    
    public class color2Selection implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                color2 = JColorChooser.showDialog(DrawingApplicationFrame.this, "Choose a color", color2);
            }
        }
    
    public class undoButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (shapes.size() >= 1) {
                shapes.remove(shapes.size() - 1);
                repaint();
            }
        }
    }
        
    public class clearButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            shapes.clear();
            repaint();
        }
    }
    // Create event handlers, if needed

    // Create a private inner class for the DrawPanel.
    private class DrawPanel extends JPanel
    {
        
        public DrawPanel()
        {
            addMouseListener(new MouseHandler());
            addMouseMotionListener(new MouseHandler());
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            //loop through and draw each shape in the shapes arraylist
            
            for (int i = 0; i < shapes.size(); i++) {
                shapes.get(i).draw(g2d);
            }

        }


        private class MouseHandler extends MouseAdapter implements MouseMotionListener
        {
            Paint color;
            Stroke stroke;
            boolean fill;
            
            
            public void mousePressed(MouseEvent event)
            {
                if (dashedCheck.isSelected() & (Integer) dashLengthSpinner.getValue() != 0) {
                    float[] dashLength = {(Integer) dashLengthSpinner.getValue()};
                    stroke = new BasicStroke((Integer) lineWidthSpinner.getValue(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashLength, 0);
                }
                else {
                    stroke = new BasicStroke((Integer) lineWidthSpinner.getValue(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                }
                if (filledCheck.isSelected()) {
                    fill = true;
                }
                else {
                    fill = false;
                }
                if (gradientCheck.isSelected()) {
                    currentColor = new GradientPaint(0,0,color1,50,50,color2,true);
                }
                else{
                    currentColor = color1;
                }
                if (comboBox.getSelectedItem() == "Rectangle") {
                    currentShape = new MyRectangle(event.getPoint(),event.getPoint(),currentColor, stroke, filledCheck.isSelected());
                }
                if (comboBox.getSelectedItem() == "Line") {
                    currentShape = new MyLine(event.getPoint(),event.getPoint(),currentColor, stroke);
                }
                if (comboBox.getSelectedItem() == "Oval") {
                    currentShape = new MyOval(event.getPoint(),event.getPoint(),currentColor, stroke, filledCheck.isSelected());
                }                
                mousePosLabel.setText("(" + event.getX() + "," + event.getY() + ")");
                System.out.println(mousePosLabel.getText());
                shapes.add(currentShape);
                repaint();
            }

            public void mouseReleased(MouseEvent event)
            {
                mousePosLabel.setText("(" + event.getX() + "," + event.getY() + ")");
                shapes.get(shapes.size()-1).setEndPoint(event.getPoint());
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent event)
            {
                mousePosLabel.setText("(" + event.getX() + "," + event.getY() + ")");
                shapes.get(shapes.size()-1).setEndPoint(event.getPoint());
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent event)
            {
                mousePosLabel.setText("(" + event.getX() + "," + event.getY() + ")");
                repaint();
            }
        }

    }
}
