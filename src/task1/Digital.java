package task1;

public class Digital {
    private int[] sizes;
    private int[] ans;

    // 初始化一个Digital类。
    // sizes是不同位置上可以状态的数量。
    public Digital(int[] sizes) {
        this.sizes = sizes;
        this.ans = new int[this.sizes.length];
        for(int i=0;i<this.ans.length-1;i++){
            this.ans[i] = 0;
        }
        this.ans[this.ans.length-1] = -1;
    }

    // 由当前的状态序列转换到下一个状态序列。
    // 如果产生了有效的序列，则返回 true；否则返回false。
    public boolean next() {
        for(int i=this.sizes.length-1;i>=0;i--){
            if(this.ans[i]<this.sizes[i]-1){
                // 存在下一状态
                return true;
            }
        }
        // 每个位置都不存在下一状态
        return false;
    }
    // 获取当前的状态序列。
    // 不管next()方法返回的值是true还是false，都要返回当前的状态序列。
    public int[] getStates() {
        // 获得从后向前第一个非最终状态的位置
        int p = this.sizes.length-1;
        while(this.ans[p]>=this.sizes[p]-1){
            p--;
            if(p<0) break;
        }
        // 从后向前不存在非最终状态的位置
        if(p<0) return null;
        this.ans[p]+=1;  // 进位
        for(int i=p+1;i<this.ans.length;i++){   // 位置进位后需要清零
            this.ans[i]=0;
        }
        return this.ans.clone();
    }

    public static void main(String[] args) {
        int[] s = new int[]{3, 2, 3};
        Digital digital = new Digital(s);
        while(digital.next()){
            int[] a = digital.getStates();
            for(int aa:a){
                System.out.print(aa);
            }
            System.out.println();
        }
    }
}