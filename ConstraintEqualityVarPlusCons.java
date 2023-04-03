package main.java;
import java.util.*;
public class ConstraintEqualityVarPlusCons extends Constraint {
    Variable v1, v2;
    int cons;
    Boolean abs;
    public ConstraintEqualityVarPlusCons(Variable v1, Variable v2, int cons, Boolean abs){
        this.v1 = v1;
        this.v2 = v2;
        this.cons = cons;
        this.abs = abs;
    }
    public String toString() {
        String result = "";
        if(!abs)
        {
            result += this.v1 + " = " + this.v2 + " + " + this.cons;
        } else
        {
            result += "|" + this.v1 + " - " + this.v2 + "|" + " = " + this.cons;
        }
        return result;
    }
    protected boolean isSatisfied() {
        for(int val1:v1.getDomain().vals){
            for(int val2:v2.getDomain().vals){
                if(val1 == val2 + this.cons){
                    return true;
                }
            }
        }
        return false;
        
    }

   
    
    protected void reduce() {

        //from d1
        for(int i = 0; i < this.v1.d.vals.length; i++) {
            Boolean flag = false;
            if(!abs) {
                for(int j = 0; j < this.v2.d.vals.length; j++) { 
                    if(this.v1.d.vals[i] == this.v2.d.vals[j] + this.cons)
                        flag = true;
                }
                if (!flag) {
                    v1.d.delete(i); 
                }
            } else {
                for(int j = 0; j < this.v2.d.vals.length; j++) { 
                    if(Math.abs(this.v1.d.vals[i] - this.v2.d.vals[j]) == this.cons)
                        flag = true;
                }
                if (!flag) {
                    v1.d.delete(i); 
                }
            }
        }
    
        //from d2
        for(int i = 0; i < this.v2.d.vals.length; i++) {
            Boolean flag = false;
            if(!abs) {
                for(int j = 0; j < this.v1.d.vals.length; j++) { 
                    if(this.v2.d.vals[i] == this.v1.d.vals[j] - this.cons)
                        flag = true;
                }
                if (!flag) {
                    v2.d.delete(i); 
                }
            } else {
                for(int j = 0; j < this.v1.d.vals.length; j++) { 
                    if(Math.abs(this.v1.d.vals[j] - this.v2.d.vals[i]) == this.cons)
                        flag = true;
                }
                if (!flag) {
                    v2.d.delete(i); 
                }
            }
        }
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

    
}