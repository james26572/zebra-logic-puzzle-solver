package main.java;
import java.util.*;
public class ConstraintDifferenceVarVar extends Constraint {
    Variable v1, v2;
    public ConstraintDifferenceVarVar(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
    public String toString() {
        String result = this.v1 + "!=" + this.v2;
        return result;
    }
    protected boolean isSatisfied() {
        if(this.v1.d.vals.length == 1 && this.v2.d.vals.length == 1 && this.v1.d.vals[0] == this.v2.d.vals[0]){
            return false;
        }
        return true;
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
    protected void reduce() {
        
        if(this.v2.d.vals.length == 1){
            int b = this.v2.d.vals[0];
            
            
            for(int i = 0;i<this.v1.d.vals.length;i++){
                int val = this.v1.d.vals[i];
                if(val == b){
                    this.v1.d.delete(i);
                }
            }
        }
        if(this.v1.d.vals.length == 1){
            int b = this.v1.d.vals[0];
            
            
            for(int i = 0;i<this.v2.d.vals.length;i++){
                int val = this.v2.d.vals[i];
                if(val == b){
                    this.v2.d.delete(i);
                }
            }
        }
    }

    
}