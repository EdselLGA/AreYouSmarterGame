package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import core.Question;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JTextArea;
import javax.swing.border.Border;

public class QuestionPanel extends JPanel{
    private static final String BACKGROUND = "assets/splash.png";
    private static final String YELLOW_BUTTON = "assets/questionButton.png";

    private GameScreenPanel parent;

    private Image bgImage;
    private JButton[] choiceButtons;
    private JButton[] selectionButtons;
    private int selectedIndex =-1;
    private JTextArea questionTextArea;
    private Border originalBorder;
    // peek copy save drop lock

    public QuestionPanel(GameScreenPanel parent){
        this.parent = parent;

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon bgIc = new ImageIcon(BACKGROUND);

        if (bgIc.getIconWidth() > 0)
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH);

        setLayout(new BorderLayout());
        
        choiceButtons = new JButton[4];
        selectionButtons = new JButton[5];
        choiceButtons[0] = new JButton();
        choiceButtons[1] = new JButton();
        choiceButtons[2] = new JButton();
        choiceButtons[3] = new JButton();

        originalBorder = choiceButtons[0].getBorder();

        //initializeQuestion();

        selectionButtons[0] = makeImageButton(YELLOW_BUTTON, "Peek");
        selectionButtons[1] = makeImageButton(YELLOW_BUTTON, "Copy");
        selectionButtons[2] = makeImageButton(YELLOW_BUTTON, "Save");
        selectionButtons[3] = makeImageButton(YELLOW_BUTTON, "Lock Answer");
        selectionButtons[4] = makeImageButton(YELLOW_BUTTON, "Drop Out");

        JPanel bottom = new JPanel();
        JPanel center = new JPanel();
        JPanel right = new JPanel();
        JPanel choiceButtonGrid = new JPanel();

        
        choiceButtonGrid.setLayout(new GridLayout(2, 2, 10, 10));

        questionTextArea = new JTextArea();
        
        choiceButtonGrid.add(choiceButtons[0]);
        choiceButtonGrid.add(choiceButtons[1]);
        choiceButtonGrid.add(choiceButtons[2]);
        choiceButtonGrid.add(choiceButtons[3]);

        center.add(questionTextArea);
        center.add(choiceButtonGrid);

        bottom.add(selectionButtons[0]);
        bottom.add(selectionButtons[1]);
        bottom.add(selectionButtons[2]);
        bottom.add(selectionButtons[3]);
        bottom.add(selectionButtons[4]);

        add(center,BorderLayout.CENTER);
        add(right,BorderLayout.EAST);
        add(bottom,BorderLayout.SOUTH);

        initializeButtonListeners();
        
        addComponentListener(new ComponentAdapter(){
            @Override
            public void componentShown(ComponentEvent e){
                initializeQuestion();
                selectedIndex = -1;
            }
        });
    }
    public void initializeQuestion(){
        choiceButtons[0].setVisible(true);
        choiceButtons[1].setVisible(true);
        choiceButtons[2].setVisible(true);
        choiceButtons[3].setVisible(true);
        Question questionDetails = parent.getGameLogic().showQuestion();
        String questionText = questionDetails.getQuestionText();
        String[] questionOptions = questionDetails.getOptions();
        questionTextArea.setText(questionText);
        if(questionOptions.length == 2){
            //true or false
            choiceButtons[0].setText("True");
            choiceButtons[1].setText("False");
            choiceButtons[2].setVisible(false);
            choiceButtons[3].setVisible(false);
        }
        else if(questionOptions.length == 4){
            choiceButtons[0].setText(questionOptions[0]);
            choiceButtons[1].setText(questionOptions[1]);
            choiceButtons[2].setText(questionOptions[2]);
            choiceButtons[3].setText(questionOptions[3]);

        }
    }

    // to enable or disable if used.
    private void initializeSelectionButtons(){
        if(parent.getModel().isPeekUsed()){
            selectionButtons[0].setEnabled(false);
        }
        if(parent.getModel().isCopyUsed()){
            selectionButtons[0].setEnabled(false);
        }
        if(parent.getModel().isSaveUsed()){
            selectionButtons[0].setEnabled(false);
        }
    }

    private void initializeButtonListeners(){
        choiceButtons[0].addActionListener(e -> {
            if (selectedIndex != -1){
                choiceButtons[selectedIndex].setBorder(originalBorder);
                choiceButtons[0].setBorder(BorderFactory.createLoweredBevelBorder());
                choiceButtons[0].repaint();
                selectedIndex = 0;
            }
            else {
                choiceButtons[0].setBorder(BorderFactory.createLoweredBevelBorder());
                choiceButtons[0].repaint();
                selectedIndex = 0;
            }
        });
        choiceButtons[1].addActionListener(e -> {
            if (selectedIndex != -1){
                choiceButtons[selectedIndex].setBorder(originalBorder);
                choiceButtons[1].setBorder(BorderFactory.createLoweredBevelBorder());
                choiceButtons[1].repaint();
                selectedIndex = 1;
            }
            else {
                choiceButtons[1].setBorder(BorderFactory.createLoweredBevelBorder());
                choiceButtons[1].repaint();
                selectedIndex = 1;
            }
        });
        choiceButtons[2].addActionListener(e -> {
            if (selectedIndex != -1){
                choiceButtons[selectedIndex].setBorder(originalBorder);
                choiceButtons[2].setBorder(BorderFactory.createLoweredBevelBorder());
                choiceButtons[2].repaint();
                selectedIndex = 2;
            }
            else {
                choiceButtons[2].setBorder(BorderFactory.createLoweredBevelBorder());
                choiceButtons[2].repaint();
                selectedIndex = 2;
            }
        });
        choiceButtons[3].addActionListener(e -> {
            if (selectedIndex != -1){
                choiceButtons[selectedIndex].setBorder(originalBorder);
                choiceButtons[3].setBorder(BorderFactory.createLoweredBevelBorder());
                choiceButtons[3].repaint();
                selectedIndex = 3;
                System.out.println("3rd button pressed");
            }
            else {
                choiceButtons[3].setBorder(BorderFactory.createLoweredBevelBorder());
                choiceButtons[3].repaint();
                selectedIndex = 3;
            }
        });

        selectionButtons[0].addActionListener(e ->{

        });
        selectionButtons[1].addActionListener(e ->{
            
        });
        selectionButtons[2].addActionListener(e ->{
            
        });
        selectionButtons[3].addActionListener(e ->{
            if (selectedIndex!=-1){
                parent.getModel().setQuestionChoiceIndex(selectedIndex);
                parent.onLockAnswer();
                selectedIndex = -1;
            }
        });
        selectionButtons[4].addActionListener(e ->{
            
        });
    }

    private JButton makeImageButton(String imagePath, String name) {

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon rawIcon = new ImageIcon(imagePath);

        int newWidth = (int) (screen.width * 0.10);
        int newHeight = (int) ((double) rawIcon.getIconHeight() /
                rawIcon.getIconWidth() * newWidth);

        Image scaledImg = rawIcon.getImage().getScaledInstance(
                newWidth, newHeight, Image.SCALE_SMOOTH
        );

        JButton btn = new JButton(name,new ImageIcon(scaledImg));

        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setFont((GameWindow.customFont).deriveFont(15f));
        btn.setHorizontalTextPosition(JButton.CENTER);
        btn.setVerticalTextPosition(JButton.CENTER);

        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setPreferredSize(new Dimension(newWidth, newHeight));

        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null)
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }
    
}
