package exhaustive;

import javax.sound.sampled.SourceDataLine;

public class Combination {
    private int total;
    private int[] array;

    public Combination(int total_num, int select_num) {
        total = total_num;
        array = new int[select_num];     // 初始化数组
        for(int i=0;i<select_num;i++){
            array[i] = i;
            if(i==select_num-1){       //  末尾值为倒数第二个值
                array[i] = array[i-1];
            }
        }
    }


    // 判断当前是否还有下一个状态
    public boolean next(){
        int p=array.length-1;
        for(;p>=0;p--){
            if(array[p] < total-1-(array.length-1-p)){   // 找到从后向前第一个非最终状态的位置
                break;
            }
        }
        if(p<0)
            return false;
        else
            return true;
    }


    public int[] getStates(){
        int p=array.length-1;
        for(;p>=0;p--){
            if(array[p] < total-1-(array.length-1-p)){   // 找到从后向前第一个非最终状态的位置
                break;
            }
        }
        if(p<0) return null;
        array[p] += 1;   // 进位
        for(++p;p<array.length;p++){
            array[p] = 0;
        }
        return array.clone();
    }



    public static void main(String[] args){
       
        Combination com = new Combination(5, 3);
        while(com.next()){
            int[] states = com.getStates();
            for(int i=0;i<states.length;i++){
                System.out.print(states[i]);
            }
            System.out.println();
        }
       

    }
}
