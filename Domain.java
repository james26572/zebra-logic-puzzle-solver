package main.java;

public class Domain {

    int[] vals;


    public Domain(int[] vals) {
        this.vals = vals;
    }


    public int length(){
        return this.vals.length;
    }

    public void setDomain(int[] arr){
        this.vals = arr;
    }

    public Domain(Domain d2) {
        //TODO make a copy of the domain from what d2 contains
        vals = new int[d2.vals.length];
        for(int i = 0; i < vals.length; i++)
            this.vals[i] = d2.vals[i];
    }
    public void delete(int i){
        int [] arr = new int[this.length()-1];
        int idx = 0;
        for(int j =0;j<vals.length;j++){
            if(j == i){
                continue;
            }
            arr[idx] = vals[j];
            idx++;
        }
        this.vals = arr;
        
    }

    /**
     * @return
     */
    public String toString() {
        String result  = "{";
        for (int i = 0; i < vals.length; i++)
            result += vals[i];
        result += "}";
        return result;
    }

    /**
     * @return
     */
    public Domain[] split() {
        int length = this.length();

        int mid = length / 2;
        int[] left = new int[mid];
        int[] right = new int[this.vals.length - mid];
        System.arraycopy(this.vals, 0, left, 0, mid);
        System.arraycopy(this.vals, mid, right, 0, length - mid);

        return new Domain[]{new Domain(left),new Domain(right)};


    }

    /**
     * @return
     */
    private boolean isEmpty() {
        return this.vals.length == 0;
    }

    /**
     * @return
     */
    private boolean equals(Domain d2) {
        int idx = 0;
        if(d2.vals.length!=this.vals.length){
            return false;
        }
        while(idx<this.vals.length){
            if(this.vals[idx]!=d2.vals[idx]){
                return false;
            }
            idx++;
        }
        return true;
    }

    /**
     * @return
     */
    private boolean  isReducedToOnlyOneValue() {
        return this.vals.length==1;
    }


    



}
