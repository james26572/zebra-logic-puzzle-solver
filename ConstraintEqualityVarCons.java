package main.java;
import java.util.*;
public class ConstraintEqualityVarCons extends Constraint{
    Variable v1;
    int c;
    public ConstraintEqualityVarCons(Variable v1, int c) {
        this.v1 = v1;
        this.c = c;
    }
    public String toString() {
        String result = this.v1 + " = " + c;
        return result;
    }

    public boolean isFullyReduced(){
        String cons = this.toString();
        this.reduce();
        String reducedCons = this.toString();
        return cons.equals(reducedCons);
    }
    public void fullyReduce(){
        while(!isFullyReduced()){
            this.reduce();
        }
    }
    protected boolean isSatisfied() {
        for(int val : this.v1.d.vals){
            if(c == val){
                return true;
            }
        }
        return false;
    }
    protected void reduce() {
        this.v1.setDomain(new Domain(new int[]{this.c}));
    }

   
}