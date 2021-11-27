package exhaustive;

import com.sun.javaws.IconUtil;

public class Digital {
    private int[] sizes;
    private int[] ans;

    // ��ʼ��һ��Digital�ࡣ
    // sizes�ǲ�ͬλ���Ͽ���״̬��������
    public Digital(int[] sizes) {
        this.sizes = sizes;
        this.ans = new int[this.sizes.length];
        for(int i=0;i<this.ans.length-1;i++){
            this.ans[i] = 0;
        }
        this.ans[this.ans.length-1] = -1;
    }

    // �ɵ�ǰ��״̬����ת������һ��״̬���С�
    // �����������Ч�����У��򷵻� true�����򷵻�false��
    public boolean next() {
        for(int i=this.sizes.length-1;i>=0;i--){
            if(this.ans[i]<this.sizes[i]-1){
                // ������һ״̬
                return true;
            }
        }
        // ÿ��λ�ö���������һ״̬
        return false;
    }
    // ��ȡ��ǰ��״̬���С�
    // ����next()�������ص�ֵ��true����false����Ҫ���ص�ǰ��״̬���С�
    public int[] getStates() {
        // ��ôӺ���ǰ��һ��������״̬��λ��
        int p = this.sizes.length-1;
        while(this.ans[p]>=this.sizes[p]-1){
            p--;
            if(p<0) break;
        }
        // �Ӻ���ǰ�����ڷ�����״̬��λ��
        if(p<0) return null;
        this.ans[p]+=1;  // ��λ
        for(int i=p+1;i<this.ans.length;i++){   // λ�ý�λ����Ҫ����
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