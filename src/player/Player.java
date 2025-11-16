// Player data(name,winnings,lifelines)
package player;

import core.Copy;
import core.Peek;
import core.Save;

public class Player {
    private String name;
    private int winnings;
    private Peek peekLifeline;
    private Copy copyLifeline;
    private Save saveLifeline;

    public Player(String name) {
        this.name = name;
        this.winnings = 0;
        this.peekLifeline = new Peek();
        this.copyLifeline = new Copy();
        this.saveLifeline = new Save();
    }

    public String getName() {
        return name;
    }
    public int getWinnings() {
        return winnings;
    }
    public void addWinnings(int amount) {
        this.winnings += amount;
    }
    public Peek getPeekLifeline() {
        return peekLifeline;
    }
    public Copy getCopyLifeline() {
        return copyLifeline;
    }
    public Save getSaveLifeline() {
        return saveLifeline;
    }
    public void resetLifelines() {
        this.peekLifeline.reset();
        this.copyLifeline.reset();
        this.saveLifeline.reset();
    }

}