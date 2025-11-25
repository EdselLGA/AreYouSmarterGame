package ui;

import Utils.Sound;
import core.GameLogic;
import core.Question;

import javax.swing.*;
import java.awt.*;

/**
 * Main Question Screen Panel (Dynamic: 2 or 4 choices)
 * Designed for 1920x1080
 */
public class GameQuestionPanel extends JPanel {

    private final GameWindow parent;

    private static final String BG = "assets/QuestionsBG.png";
    private static final String BUBBLE = "assets/QuestionsbuttonContainer.png";
    private static final String BTN_SAVE = "assets/Save.png";
    private static final String BTN_DROPOUT = "assets/Dropout.png";
    private static final String BTN_LOCK = "assets/Lock_Answer.png";
    private static final String CHARLIE = "assets/charlie.png";

    private Image bgImage;
    private Image bubbleImage;

    private JLabel questionLabel;
    private JButton[] optionButtons; // A, B, C, D
    private JLabel worthLabel;
    private JLabel currentWinningsLabel;

    private JButton saveBtn, lockBtn, dropoutBtn;

    // Set screen resolution for layout reference (1920x1080)
    private final Dimension screen = new Dimension(1920, 1080);

    public GameQuestionPanel(GameWindow parent) {
        this.parent = parent;

        setLayout(null); // absolute layout for pixel control
        setOpaque(false);

        loadImages();

        // ===== BUBBLE DIMENSIONS (Big white box) =====
        int bubbleWidth = (int) (screen.width * 0.78);
        int bubbleX = (screen.width - bubbleWidth) / 2 - 80;  // slightly left
        int bubbleY = 100;
        int bubbleHeight = scaleHeight(bubbleImage, bubbleWidth);

        // ===== WORTH LABEL =====
        worthLabel = new JLabel("WORTH: $1,000");
        worthLabel.setFont(new Font("Arial", Font.BOLD, 28));
        worthLabel.setForeground(Color.BLACK);
        worthLabel.setBounds(bubbleX + 40, bubbleY + 30, 350, 40);
        add(worthLabel);

        // ===== CURRENT WINNINGS TOP RIGHT =====
        currentWinningsLabel = new JLabel("CURRENT WINNINGS: $0");
        currentWinningsLabel.setFont(new Font("Arial", Font.BOLD, 26));
        currentWinningsLabel.setForeground(Color.WHITE);

        int cwX = screen.width - 470;
        currentWinningsLabel.setBounds(cwX, 30, 460, 40);
        add(currentWinningsLabel);

        // ===== QUESTION TEXT INSIDE BUBBLE =====
        questionLabel = new JLabel("<html><center>PLACEHOLDER QUESTION</center></html>", JLabel.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 38));
        questionLabel.setForeground(Color.BLACK);

        int qX = bubbleX + 60;
        int qW = bubbleWidth - 120;
        questionLabel.setBounds(qX, bubbleY + 90, qW, 120);
        add(questionLabel);

        // ===== OPTIONS (A, B, C, D) =====
        optionButtons = new JButton[4];

        int optW = (qW - 60) / 2;
        int optH = 90;

        int col0X = qX;
        int col1X = qX + optW + 60;

        int row0Y = bubbleY + 240;
        int row1Y = row0Y + optH + 40;

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton("Choice " + (i + 1));
            optionButtons[i].setFont(new Font("Arial", Font.BOLD, 30));
            optionButtons[i].setBackground(Color.WHITE);
            optionButtons[i].setForeground(Color.BLACK);
            optionButtons[i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4));
            optionButtons[i].setFocusPainted(false);

            int x = (i % 2 == 0) ? col0X : col1X;
            int y = (i < 2) ? row0Y : row1Y;

            optionButtons[i].setBounds(x, y, optW, optH);
            add(optionButtons[i]);

            final int index = i;
            optionButtons[i].addActionListener(e -> {
                Sound.playSFX("assets/Clicked.wav");
                handleChoice(index);
            });
        }

        // ===== BOTTOM BUTTONS (Save, Lock, Dropout) =====
        int bottomY = bubbleY + bubbleHeight + 30;

        int btnW = 230;
        int btnH = 90;
        int gap = 35;

        int total = (btnW * 3) + (gap * 2);
        int startX = (screen.width - total) / 2 - 60;

        saveBtn = imageButton(BTN_SAVE, btnW, btnH);
        lockBtn = imageButton(BTN_LOCK, btnW, btnH);
        dropoutBtn = imageButton(BTN_DROPOUT, btnW, btnH);

        saveBtn.setBounds(startX, bottomY, btnW, btnH);
        lockBtn.setBounds(startX + btnW + gap, bottomY, btnW, btnH);
        dropoutBtn.setBounds(startX + 2 * (btnW + gap), bottomY, btnW, btnH);

        HelpersUI.addLightenOnHover(saveBtn, 1.25f);
        HelpersUI.addLightenOnHover(lockBtn, 1.25f);
        HelpersUI.addLightenOnHover(dropoutBtn, 1.25f);

        HelpersUI.addHoverSFX(saveBtn, "assets/Hover.wav");
        HelpersUI.addHoverSFX(lockBtn, "assets/Hover.wav");
        HelpersUI.addHoverSFX(dropoutBtn, "assets/Hover.wav");

        add(saveBtn);
        add(lockBtn);
        add(dropoutBtn);

        // ===== CHARLIE (bottom right) =====
        ImageIcon charIcon = new ImageIcon(CHARLIE);

        int charH = (int) (screen.height * 0.35);
        int charW = charIcon.getIconWidth() * charH / charIcon.getIconHeight();

        Image scaled = charIcon.getImage().getScaledInstance(charW, charH, Image.SCALE_SMOOTH);

        JLabel charLabel = new JLabel(new ImageIcon(scaled));
        charLabel.setBounds(screen.width - charW - 25, screen.height - charH - 25, charW, charH);
        add(charLabel);
    }

    // ===== LOAD QUESTION =====
    public void loadQuestion(Question q, int worth, int currentWinnings) {

        worthLabel.setText("WORTH: $" + worth);
        currentWinningsLabel.setText("CURRENT WINNINGS: $" + currentWinnings);

        questionLabel.setText("<html><center>" + q.getQuestionText() + "</center></html>");

        String[] opts = q.getOptions();

        // MULTIPLE CHOICE (4 options)
        if (q.isMultipleChoice()) {
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setVisible(true);
                optionButtons[i].setText(opts[i]);
            }
        }
        // TRUE OR FALSE (only 2 buttons)
        else {
            optionButtons[0].setVisible(true);
            optionButtons[1].setVisible(true);
            optionButtons[0].setText("TRUE");
            optionButtons[1].setText("FALSE");

            optionButtons[2].setVisible(false);
            optionButtons[3].setVisible(false);
        }
    }

    // ===== HANDLE ANSWER CLICK =====
    private void handleChoice(int index) {
        System.out.println("Player chose choice index: " + index);

        // integrate with GameLogic here
        // GameLogic logic = parent.getGameLogic();
        // Question q = logic.getCurrentQuestion();
        // boolean correct = q.isCorrect(index);
    }

    // ===== HELPER: Image button =====
    private JButton imageButton(String path, int w, int h) {
        ImageIcon raw = new ImageIcon(path);
        Image im = raw.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);

        JButton btn = new JButton(new ImageIcon(im));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        return btn;
    }

    // ===== LOAD BACKGROUND IMAGES =====
    private void loadImages() {
        bgImage = new ImageIcon(BG).getImage();
        bubbleImage = new ImageIcon(BUBBLE).getImage();
    }

    // ===== SCALE IMAGE HEIGHT =====
    private int scaleHeight(Image img, int newWidth) {
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        return (h * newWidth) / w;
    }

    // ===== PAINT BACKGROUND + BUBBLE =====
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }

        if (bubbleImage != null) {
            int bubbleWidth = (int) (screen.width * 0.78);
            int bubbleX = (screen.width - bubbleWidth) / 2 - 80;
            int bubbleY = 100;
            int bubbleHeight = scaleHeight(bubbleImage, bubbleWidth);

            g.drawImage(bubbleImage, bubbleX, bubbleY, bubbleWidth, bubbleHeight, this);
        }
    }
}
