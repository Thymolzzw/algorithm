public class Combination {
    private int total;
    private int select;
    private int[] array;

    public Combination(int total_num, int select_num) {
        total = total_num;
        select = select_num;
        array = new int[select];
        array[0] = 121;


    }

    public void myPrint(){
        System.out.println(total + " " + select);
        System.out.println(array[0]);
    }

    public static void main(String[] args){
//        System.out.println("121");
        Combination com = new Combination(1, 3);
        com.myPrint();

    }
}
