package task2;

public class Hannuota {

    public static void han(int n, String from, String temp, String to){
        if(n==1){
            System.out.println(from + "-->" + to);
            return;
        }
        han(n-1, from, to, temp);
        System.out.println(from + "-->" + to);
        han(n-1, temp, from, to);
    }

    public static void main(String[] args) {
        han(3, "a", "b", "c");
    }


    
}