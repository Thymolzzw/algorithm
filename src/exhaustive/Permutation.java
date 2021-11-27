package exhaustive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Permutation {
    private int total;
    private List<Integer> nums;
 
    public Permutation(int total, int select){
        this.total = total;
        
        this.nums = new ArrayList<Integer>();   // 初始化数组。举例：total为3，select为2时，nums初始化为[0,1]
        for(int i=0;i<select;i++){
            this.nums.add(i);
        }
        this.nums.set(this.nums.size()-1, this.nums.get(this.nums.size()-2));
    }


    // 对列表all进行减法操作，减去列表sub中p位置之前的相同元素。
    // 例子：all[0,1,2], sub[0,1],p=1,结果为[1,2]
    public List<Integer> preDo(List<Integer> all, List<Integer> sub, int p){
        List<Integer> all_clone = new ArrayList<Integer>();  // 拷贝all列表
        all_clone.addAll(all);
        sub = sub.subList(0, p);
        all_clone.removeAll(sub);
        Collections.sort(all_clone);
        return all_clone;
    }


    // 找到sub列表中从前向后第一个大于value的值index，没有则返回-1
    public int findFirstBigger(List<Integer> sub, int value){
        for(int i=0;i<sub.size();i++){
            if(sub.get(i)>value)
                return i;
        }
        return -1;
    }


    // 当前时候还有下一状态，没有返回-1，有返回从后向前第一个非最终状态位置
    public int next(){
        int p=this.nums.size()-1;  // 记录从后向前第一个非最终状态
        
        List<Integer> all = new ArrayList<Integer>();  // 初始化。默认数字集合，total为3时， num初始化为[0,1,2]
        for(int i=0;i<this.total;i++){
            all.add(i);
        }

        for(;p>=0;p--){
            List<Integer> sub = this.preDo(all, this.nums, p);  
           
            // index表示sub中第一个大于this.nums.get(p)的位置
            int index = this.findFirstBigger(sub, this.nums.get(p));
            if(index<0){
                // 当前p位置是最终状态
                continue;
            }else{
                // 当前p位置不是最终状态
                return p;
            }
        }
        return -1;
    }


    // 得到下一状态的序列
    public List<Integer> getStates(){
        int p = this.next();
        if(p<0){
            // 没有下一状态
            return null;
        }else{
            // 存在下一状态
            List<Integer> all = new ArrayList<Integer>();  // 初始化。默认数字集合，total为3时， num初始化为[0,1,2]
            for(int i=0;i<this.total;i++){
                all.add(i);
            }
            List<Integer> sub = this.preDo(all, this.nums, p);  

            int index = this.findFirstBigger(sub, this.nums.get(p));
            this.nums.set(p, sub.get(index));
            
            List<Integer> all_select = new ArrayList<Integer>();  // 拷贝all列表
            all_select.addAll(all);
            all_select.removeAll(this.nums.subList(0, p+1));
            Collections.sort(all_select);
            int pp = p+1;
            for(++p;p<this.nums.size();p++){     // p位置之后重新选择较小值赋值
                this.nums.set(p, all_select.get(p-pp));
            }
            
            List<Integer> ans = new ArrayList<Integer>();
            ans.addAll(this.nums);
            return ans;
        }

    }

    
    public static void main(String[] args) {
        Permutation per = new Permutation(4, 3);
        while(per.next()>=0){
            List<Integer> a = per.getStates();
            System.out.println(a);
        }
        
    }
}