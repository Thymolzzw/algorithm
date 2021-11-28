package task2;

public class Fabanacci {

    public static int fab(int n){
        if(n==1) return 1;
        if(n==2) return 2;
        return fab(n-1) + fab(n-2);
    }

    public static void main(String[] args) {
        int a = fab(4);
        System.out.println(a);
    }
    
}