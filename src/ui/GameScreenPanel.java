package ui;

import Utils.Sound;
import core.GameLogic;
import core.GameModel;
import core.NextStep;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

public class GameScreenPanel extends JPanel {

    private final GameWindow parent;

    private static final String BACKGROUND = "assets/ChooseSub_BG.png";
    private static final String BTN_BACK = "assets/BACK.png";

    private Image bgImage;
    private Image charlieImage;
    
    // GameHandling
    private CategoryPanel categoryPanel;
    private HelperPanel helperPanel;
    private QuestionPanel questionPanel;

    private GameLogic gameLogic;
    private GameModel gameModel;

    private CardLayout layout;
    // Game Handling
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
        showCard(name);
    }

    public GameLogic getGameLogic(){
        return gameLogic;
    }
    public GameModel getModel(){
        return gameModel;
    }

    public void showQuestionPanel(){
        // load question
    }
    public void showHelperPanel(){
        // show helper
        helperPanel.refresh();
    }
    public void showCategoryPanel(){
        // show categories
        categoryPanel.refresh();
    }
    /*
    public void showResultsPanel(){
    
    }
    */

    // Question Answer Selected
    public void onSelectedAnswer(){

    }
    // Question Answer Lock
    public void onDropOut(){
        NextStep step = NextStep.GAME_OVER;
        handleNext(step);
    }
    public void onLockAnswer(){
        NextStep step = gameLogic.lockAnswer();
        handleNext(step);
    }
    public void onPeek(){

    }
    public void onCopy(){

    }
    public void onSave(){

    }
    public void onCategorySelected(){
        NextStep step = NextStep.NEXT_QUESTION;
        handleNext(step);
    }
    public void onHelperSelected(){
        NextStep step = NextStep.PICK_CATEGORY;
        handleNext(step);
    }
    public void handleNext(NextStep step){
        switch (step){
            case NEXT_QUESTION:
                gameLogic.loadQuestion();
                questionPanel.initializeQuestion();
                showCard("question");
                break;
            case PICK_CATEGORY:
                categoryPanel.refresh();
                showCard("category");
                break;
            case PICK_HELPER:
                helperPanel.refresh();
                showCard("helper");
                break;
            case LAST_QUESTION:
                break;
            case GAME_OVER:
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImage != null)
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
    }
}
