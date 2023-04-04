package main.java;
import java.util.List;

public abstract class Constraint {
    

    public String toString() {
        
        return "";
    }

    
    protected abstract boolean isFullyReduced();
    protected abstract void fullyReduce();
    protected abstract boolean isSatisfied();

    protected abstract void reduce();

}
