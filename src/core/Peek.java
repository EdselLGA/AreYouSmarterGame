// Peek lifeline implementation
package core;

public class Peek extends Lifeline {

    @Override
    public void useLifeline() {
        if (!isUsed) {
            System.out.println("Peek lifeline used: You can peek at the answer choices.");
            isUsed = true;
        } else {
            System.out.println("Peek lifeline has already been used.");
        }
    }
}