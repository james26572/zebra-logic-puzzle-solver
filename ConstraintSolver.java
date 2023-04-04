package main.java;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
public class ConstraintSolver {
    private Domain dom;
    private ArrayList<Variable> variableSet;
    private ArrayList<Constraint> constraintSet;
    
    public ConstraintSolver() {
        this.variableSet = new ArrayList<Variable>();
        this.constraintSet = new ArrayList<Constraint>();
        
    }
   
    
    
    
    
    public String toString() {
        //print variable
        for(int i = 0; i < variableSet.size(); i++){
            System.out.println(variableSet.get(i));
        }
        //print constraints
        for(int i = 0; i < constraintSet.size(); i++){
            System.out.println(constraintSet.get(i));
        }
        return "";
        
    }
    private void parse(String fileName) {
        try {
            File inputFile = new File(fileName);
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                if(currentLine.startsWith("Domain-")) {
                    //this is our domain - i.e. a datastructure that contains values and can be updated, played with etc.
                    String s = currentLine.replace("Domain-","");
                    String[] array = s.split(",");
                    int[] vals = new int[array.length];
                    for(int i = 0; i < array.length; i++) {
                        vals[i] = Integer.parseInt(array[i]);
                    }
                    dom = new Domain(vals);
                    // Add to domain list here
                    
                } else if (currentLine.startsWith("Var-")) {
                    //this is the code for every variable (a name and a domain)
                    String s = currentLine.replace("Var-","");
                    Variable var = new Variable(s, dom);
                    variableSet.add(var);
                } else if (currentLine.startsWith("Cons-")) {
                    //TODO
                    //this is the code for the constraints
                    //ConstraintEqualityVarPlusCons:
                    if (currentLine.startsWith("Cons-eqVPC")) {
                        String regexPattern = "\\(|\\)|\\ ";
                        String s = currentLine.replace("Cons-eqVPC(","").replaceAll(regexPattern, "");
                        String[] values = s.split("=");
                        String[] values2 = values[1].split("\\+");
                        String val1Name = values[0];
                        String val2Name = values2[0];
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if(element.hasThisName(val2Name)) {
                                v2 = element;
                            }
                        }
                        ConstraintEqualityVarPlusCons eq = new ConstraintEqualityVarPlusCons(v1, v2, Integer.parseInt(values2[1]), false);
                        constraintSet.add(eq);
                    } else if (currentLine.startsWith("Cons-eqVC"))
                    {
                        String regexPattern = "\\(|\\)";
                        String s = currentLine.replace("Cons-eqVC","").replaceAll(regexPattern, "");
                        String[] values = s.split(" = ");
                        String val1Name = values[0];
                        int c1 = Integer.parseInt(values[1]);
                        //System.out.println("Cons-eqVC: " + val1Name + c1);
                        Variable v1 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            }
                        }
                        ConstraintEqualityVarCons eq = new ConstraintEqualityVarCons(v1, c1);
                        constraintSet.add(eq);
                    }
                    else if (currentLine.startsWith("Cons-diff"))
                    {
                        String regexPattern = "\\(|\\)";
                        String s = currentLine.replace("Cons-diff","").replaceAll(regexPattern, "");
                        String[] values = s.split(",");
                        String val1Name = values[0];
                        String val2Name = values[1];
                        //System.out.println("Cons-diff: " + val1Name + val2Name);
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if(element.hasThisName(val2Name)) {
                                v2 = element;
                            }
                        }
                        ConstraintDifferenceVarVar eq = new ConstraintDifferenceVarVar(v1, v2);
                        constraintSet.add(eq);
                    }   // ConstraintEqualityVarVar
                    else if (currentLine.startsWith("Cons-eqVV"))
                    {
                        String regexPattern = "\\(|\\)";
                        String s = currentLine.replace("Cons-eqVV","").replaceAll(regexPattern, "");
                        String[] values = s.split(" = ");
                        String val1Name = values[0];
                        String val2Name = values[1];
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if(element.hasThisName(val2Name)) {
                                v2 = element;
                            }
                        }
                        ConstraintEqualityVarVar eq = new ConstraintEqualityVarVar(v1, v2);
                        constraintSet.add(eq);
                    } else if (currentLine.startsWith("Cons-abs")) {
                        String regexPattern = "\\(|\\)|\\ ";
                        String s = currentLine.replace("Cons-abs","").replaceAll(regexPattern, "");
                        String[] values = s.split("=");
                        String[] values2 = values[0].split("-");
                        String val1Name = values2[0];
                        String val2Name = values2[1];
                        Variable v1 = null;
                        Variable v2 = null;
                        for (Variable element : variableSet) {
                            if (element.hasThisName(val1Name)) {
                                v1 = element;
                            } else if(element.hasThisName(val2Name)) {
                                v2 = element;
                            }
                        }
                        ConstraintEqualityVarPlusCons eq = new ConstraintEqualityVarPlusCons(v1, v2, Integer.parseInt(values[1]), true);
                        constraintSet.add(eq);
                    }
                }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("Error.");
                e.printStackTrace();
            }
            }
   
    public void reduce() {
        for(int i = 0; i < this.constraintSet.size(); i++)
            this.constraintSet.get(i).reduce();
    }
    
   
    public static void main(String[] args) {

        ConstraintSolver solver = new ConstraintSolver();
        
        ArrayList<String> list = solver.printAnswer("data.txt");
        System.out.println(list);
        



        


        
        
        
        }


        public ArrayList<String> printAnswer(String filename) {

            ConstraintSolver problem = new ConstraintSolver();
            problem.parse(filename);
            ArrayList<String> answer = new ArrayList<String>();
            problem.solve(answer);
            
            return answer;

        }

    

    public boolean solve(ArrayList<String> answer){
        

        this.reduce();
        this.reduce();
        
        

        if(this.emptyDomain()){
            //System.out.println("empty");
            return false;
        }

        if(this.isSolved()){
            //System.out.println("solved");
            //System.out.println(this);
            fillAnswerArray(this,answer);
            return true;

        }

        
       ConstraintSolver sub1 = this.deepCopy();
       sub1.splitDomain(0);
       ConstraintSolver sub2 = this.deepCopy();
       sub2.splitDomain(1);

       if(sub1.solve(answer)){
        return true;
       }
       if(sub2.solve(answer)){
        return true;
       }


       return false;

    }


    public void fillAnswerArray(ConstraintSolver solvedProb, ArrayList<String> answers){
        for(Variable var:solvedProb.variableSet){
            String ans = "Sol-"+var.name + "-"+var.d.vals[0];
            answers.add(ans);
        }
    }

    

    public void splitDomain(int idx){
        Domain small = this.getDomainWithSmallestValidValue();

        //System.out.println(small.vals.length);
        
        Domain[] splits = small.split();
        //System.out.println("____"+ splits[idx].toString()+"__________");

        small.setDomain(splits[idx].vals);


    }

    public Variable findVarByName(String name){
        Variable variable = null;
        for(Variable var : this.variableSet){
            if(name.equals(var.name)){
                variable = var;
            }
        }
        return variable;
    }


    public ConstraintSolver deepCopy(){
        ConstraintSolver clone = new ConstraintSolver();

        for(Variable var : this.variableSet){
            Domain domain = new Domain(var.d.vals.clone());
            Variable variable = new Variable(var.name,domain);
            clone.variableSet.add(variable);
        }

        clone.dom = new Domain( new int[]{1,2,3,4,5});

        


        for (Constraint c : this.constraintSet) {
            Constraint newConstraint = null;
            if (c instanceof ConstraintEqualityVarPlusCons) {
                ConstraintEqualityVarPlusCons eq = (ConstraintEqualityVarPlusCons) c;
                Domain dom1 = new Domain(eq.v1.d.vals.clone());
                Variable v1 = clone.findVarByName(eq.v1.name);
                Domain dom2 = new Domain(eq.v2.d.vals.clone());
                Variable v2 = clone.findVarByName(eq.v2.name);

                newConstraint = new ConstraintEqualityVarPlusCons(v1, v2, eq.cons, eq.abs);
            } else if (c instanceof ConstraintEqualityVarCons) {
                ConstraintEqualityVarCons eq = (ConstraintEqualityVarCons) c;
                Domain dom1 = new Domain(eq.v1.d.vals.clone());
                Variable v1 = clone.findVarByName(eq.v1.name);
                newConstraint = new ConstraintEqualityVarCons(v1, eq.c);
            } else if (c instanceof ConstraintDifferenceVarVar) {
                ConstraintDifferenceVarVar diff = (ConstraintDifferenceVarVar) c;
                Domain dom1 = new Domain(diff.v1.d.vals.clone());
                Variable v1 = clone.findVarByName(diff.v1.name);
                Domain dom2 = new Domain(diff.v2.d.vals.clone());
                Variable v2 = clone.findVarByName(diff.v2.name);
                newConstraint = new ConstraintDifferenceVarVar(v1, v2);
            } else if (c instanceof ConstraintEqualityVarVar) {
                ConstraintEqualityVarVar eq = (ConstraintEqualityVarVar) c;
                Domain dom1 = new Domain(eq.v1.d.vals.clone());
                Variable v1 = clone.findVarByName(eq.v1.name);
                Domain dom2 = new Domain(eq.v2.d.vals.clone());
                Variable v2 = clone.findVarByName(eq.v2.name);
                newConstraint = new ConstraintEqualityVarVar(v1, v2);
            }
            clone.constraintSet.add(newConstraint);
        }

        return clone;
    }

   

   

        
        
        
        


       


    

    

        
        
        
        
        
        
        
       
    

    

    
    public boolean isSolved(){
        for(Variable var : this.variableSet){
            if(var.d.vals.length != 1){
                return false;
            }
        }
        
        return true;
    }
    public Domain getDomainWithLargestValue(){
        int max = -10;
        Domain domain = null;
        for(Variable var :this.variableSet){
            if(var.d.length()>max){
                max = var.d.length();
                domain = var.d;
            }
        }
        return domain;
    }

    public boolean isSatisfied(){
        for(Constraint cons : this.constraintSet){
            if(!cons.isSatisfied()){
                return false;
            }
        }
        return true;
    }

    


        

    
    

    public boolean emptyDomain(){
        for(Variable var : this.variableSet){
            if(var.d.vals.length == 0){
                return true;
            }
        }
        return false;
    }

    

    public Domain getDomainWithSmallestValidValue(){
        int min = 10;
        Domain smalldom = null;
        for(Variable var: this.variableSet){
            if(var.d.vals.length<min && var.d.vals.length>1){
                min = var.d.vals.length;
                smalldom = var.d;
            }
        }
        return smalldom;
    }



    
   

    
}

























