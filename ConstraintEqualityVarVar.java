package main.java;
import java.util.*;
public class ConstraintEqualityVarVar extends Constraint {
    Variable v1, v2;
    public ConstraintEqualityVarVar(Variable v1, Variable v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
    public String toString() {
        String result = this.v1 + " = " + this.v2;
        return result;
    }
    protected boolean isSatisfied() {
        // Optimal complexity
        Set<Integer> uniqueSet = new HashSet<>();
        Domain d1 = this.v1.getDomain();
        Domain d2 = this.v2.getDomain();
        for (int el1 : d1.vals)
        {
            uniqueSet.add(el1);
        }
        for (int el2 : d2.vals)
        {
            if(uniqueSet.contains(el2))
            {
                return true;
            }
        }
        return false;
    }
    protected void reduce() {
        Domain d1 = this.v1.getDomain();
        Domain d2 = this.v2.getDomain();
        Set<Integer> set1 = new HashSet<>();
        for (int i = 0; i < d1.length(); i++)
        {
            int el = this.v1.getDomain().vals[i];
            set1.add(el);
        }
        Set<Integer> set2 = new HashSet<>();
        for (int j = 0; j < d2.length(); j++)
        {
            int el = this.v2.getDomain().vals[j];
            set2.add(el);
        }
        set1.retainAll(set2);
        int[] d1arr = new int[set1.size()];
        Object[] set1ToArr = set1.toArray();
        for (int i = 0; i < set1.size(); i++)
        {
            d1arr[i] = ((Integer) set1ToArr[i]).intValue();
        }
        int[] d2arr = new int[set2.size()];
        Object[] set2ToArr = set2.toArray();
        for (int j = 0; j < set2.size(); j++)
        {
            d2arr[j] = ((Integer) set2ToArr[j]).intValue();
        }
        this.v1.setDomain(new Domain(d1arr));
        this.v2.setDomain(new Domain(d1arr));
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