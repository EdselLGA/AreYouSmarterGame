// Copy lifeline implementation

package core;  
public class Copy extends Lifeline {

    @Override
    public void useLifeline() {
        if (!isUsed) {
            System.out.println("Copy lifeline used: You can copy an answer from another player.");
            isUsed = true;
        } else {
            System.out.println("Copy lifeline has already been used.");
        }
    }
}