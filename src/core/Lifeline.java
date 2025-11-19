// Abstract lifeline class
package core;


public abstract class Lifeline{
    protected boolean isUsed;

    public Lifeline() {
        this.isUsed = false;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public abstract void useLifeline();
    
    public void reset(){
        this.isUsed = false;
    }
}