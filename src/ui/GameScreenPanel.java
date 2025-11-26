package ui;

import Utils.Sound;
import core.GameLogic;
import core.GameModel;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

public class GameScreenPanel extends JPanel {

    private final GameWindow parent;

    private static final String BACKGROUND = "assets/ChooseSub_BG.png";
    private static final String BTN_BACK = "assets/BACK.png";

    private CategoryPanel categoryPanel;
    private HelperPanel helperPanel;
    private QuestionPanel questionPanel;

    private Image bgImage;
    private Image charlieImage;
    
    private GameLogic gameLogic;
    private GameModel gameModel;

    private CardLayout layout;

    public GameScreenPanel(GameWindow parent) {
        this.parent = parent;

        this.categoryPanel = new CategoryPanel(this);
        this.helperPanel = new HelperPanel(this);
        this.questionPanel = new QuestionPanel(this);
        this.gameModel = new GameModel();
        this.gameLogic = new GameLogic(gameModel);
        this.layout = new CardLayout();

        setOpaque(false);
        setLayout(layout);

        
        add(helperPanel,"helper");
        add(categoryPanel,"category");
        add(questionPanel,"question");

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        ImageIcon bgIc = new ImageIcon(BACKGROUND);
        if (bgIc.getIconWidth() > 0) {
            bgImage = bgIc.getImage().getScaledInstance(
                    screen.width, screen.height, Image.SCALE_SMOOTH);
        }

        
        addComponentListener(new ComponentAdapter(){
            @Override
            public void componentShown(ComponentEvent e){
                gameLogic.startGame();
            }
        });
    }

    public void backToMenu(){
        parent.switchTo(GameWindow.CARD_MAINMENU);

    }
    public void showCard(String name) {
        layout.show(this, name);
    }
    public void switchTo(String name) {
        refresh();
        showCard(name);
    }

    public GameLogic getGameLogic(){
        return gameLogic;
    }
    public GameModel getModel(){
        return gameModel;
    }
    public void refresh(){
        helperPanel.refresh();
        categoryPanel.refresh();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImage != null)
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }
}
