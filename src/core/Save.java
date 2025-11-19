// Save lifeline implementation

package core;

public class Save extends Lifeline {

    @Override
    public void useLifeline() {
        if (!isUsed) {
            System.out.println("Save lifeline used: You can save yourself from a wrong answer.");
            isUsed = true;
        } else {
            System.out.println("Save lifeline has already been used.");
        }
    }
}