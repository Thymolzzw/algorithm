package equation;

import java.util.ArrayList;
import java.util.List;

public class SolveEquation {
    private List<Double> coefficient;    // 方程系数
    private List<Double> coefficientDerivation;  // 求导后的方程系数

    public SolveEquation(List<Double> coefficient){
        //  克隆一份,长度为n,后n-1位为系数,第一位为偏差
        //  例：（5,1,2,3,4）==》5+1x+2x^2+3x^3+4x^4=0  求解
        this.coefficient = new ArrayList<Double>();
        this.coefficient.addAll(coefficient);
        this.coefficientDerivation = this.doDerivation(this.coefficient);   // 求导
    }

    public List<Double> doDerivation(List<Double> coefficient){
        List<Double> coefficientDerivation = new ArrayList<Double>();
        for(int i=0;i<coefficient.size();i++){
            // 初始化this.coefficientDerivation的i位置上的参数
            coefficientDerivation.add(0.0);
            // 新系数x, 与更新位置j
            double x=i*coefficient.get(i);
            int j=Math.max(i-1, 0);
            // 求导 更新
            coefficientDerivation.set(j, x);
        }
        return coefficientDerivation;
    }

    public double doCalculation(List<Double> coefficient, double x){
        double sum = 0.0;
        // 遍历系数方程计算
        for(int i=0;i<coefficient.size();i++){
            sum += coefficient.get(i)*Math.pow(x, (double)i);
        }
        return sum;
    }


    // get方法获得系数
    public List<Double> getCoefficient(){
        List<Double> temp = new ArrayList<Double>();
        temp.addAll(this.coefficient);
        return temp;
    }

    // get方法获得求导后的系数
    public List<Double> getCoefficientDerivation(){
        List<Double> temp = new ArrayList<Double>();
        temp.addAll(this.coefficientDerivation);
        return temp;
    }
    
    // 打印方程
    public void printEquation(List<Double> coefficient){
        for(int i=0;i<coefficient.size();i++){
            if(i==0){
                System.out.print(coefficient.get(i) + "x^" + (i));
            }else{
                System.out.print((coefficient.get(i) > -1e-10 ? "+":"") + coefficient.get(i) + "x^" + (i));
            }
        }
        System.out.println("=0");
    }

    // 迭代求解，每次求一个解
    public double doStep1(List<Double> coefficient, List<Double> coefficientDerivation, double x){

        while(Math.abs(doCalculation(coefficient, x))>1e-8){
            double step = doCalculation(coefficient, x)/doCalculation(coefficientDerivation, x);
            // System.out.println(step);
            if(Double.isInfinite(step)) step=Math.random()+0.1;  // 导数为零异常，重新设置step
            if(Math.abs(step)<1e-10)  return x;   // step过小异常
            x=x-step;
        }
        return x;
    }


    // 迭代求解，每次求一个解
    public double doStep(List<Double> coefficient, List<Double> coefficientDerivation, double x){
        double bx = 0,step=1,t=0.618;
        int times = 0; 
        List<Double> dDerivation = this.doDerivation(coefficientDerivation);  // 求二阶导
        while(Math.abs(doCalculation(coefficient, x))>1e-15&&Math.abs(step)>1e-9){   // 当解足够近似，或者step足够小时结束循环
            double f = doCalculation(coefficient, x);
            double fd = doCalculation(coefficientDerivation, x);
            double fdd = doCalculation(dDerivation, x);
            step = (f*fd)/(fd*fd-f*fdd);
            if((f*fd>0&&step<0)||(f*fd<0&&step>0)) step *= -1;
            if(Double.isInfinite(step)) {
                step = Math.random() + 0.1;      // 导数为零异常，设置step
            }
            if (times++%20000==0)
                t /= 10;    // 每两万次迭代t的值变为十分之一
            x=x-step*t;
        }
        return x;
    }


    private List<Double> updateCoefficient(List<Double> coefficient, double x) {

        List<Double> coefficientUpdated = new ArrayList<Double>();
        for(int i=0;i<coefficient.size();i++){
            if(i==0){
                // 第一个系数
                coefficientUpdated.add(coefficient.get(i)/(-1.0*x));
            }else{
                coefficientUpdated.add((coefficient.get(i)-coefficientUpdated.get(i-1))/(-1.0*x));
            }
        }
		return coefficientUpdated;
	}


    // 二分迭代找到初始近似解
    public double dichotomy(List<Double> coefficient, double x_lower, double x_upper){
        double lower = this.doCalculation(coefficient, x_lower);
        double upper = this.doCalculation(coefficient, x_upper);
        double x_middle = (x_lower+x_upper)/2.0;
        double middle = this.doCalculation(coefficient, x_middle);
        int i = 0;
        while(Math.abs(middle)>1e-5){
            if(i>200) return middle;
            if(lower*middle<-1e-10){
                x_upper = x_middle;
            }else{
                x_lower = x_middle;
            }
            lower = this.doCalculation(coefficient, x_lower);
            upper = this.doCalculation(coefficient, x_upper);
            x_middle = (x_lower+x_upper)/2.0;
            middle = this.doCalculation(coefficient, x_middle);
            i++;
        }
        return middle;
    }

    // 求所有实数解
    public List<Double> solveEquation(double x_lower, double x_upper){
        List<Double> coefficient = new ArrayList<Double>();  // 拷贝一份coefficient
        coefficient.addAll(this.coefficient);
        List<Double> coefficientDerivation = new ArrayList<Double>();  // 拷贝一份coefficientDerivation
        coefficientDerivation.addAll(this.coefficientDerivation);

        List<Double> solutions = new ArrayList<Double>();

        // double x = Math.random()*(x_upper-x_lower)+x_lower;
        // double x = (x_lower+x_upper)/2.0;
        double x = dichotomy(coefficient, x_lower, x_upper);
        System.out.println(x);

        // 计算零解
        while(this.doCalculation(coefficient, 0.0)>-1e-10&&this.doCalculation(coefficient, 0.0)<1e-10){
            coefficient.remove(0);
            coefficientDerivation = this.doDerivation(coefficient);
            solutions.add(0.0);
        }

        
        int len = coefficient.size();    // 计算非零解
        for(int i=0;i<len-1;i++){
            x = this.doStep(coefficient, coefficientDerivation, x);
            solutions.add(x);

            // 更新方程 降幂次
            coefficient = this.updateCoefficient(coefficient, x);
            coefficientDerivation = this.doDerivation(coefficient);
        }
        return solutions;
    }



   
	public static void main(String[] args) {

        List<Double> coefficient = new ArrayList<Double>();

        // 解方程例子： (x-1)(x-2)(x-3)(x-4)=0
        // 展开后为：24-50x^1+35x^2-10x^3+x^4=0
        coefficient.add(24.0);
        coefficient.add(-50.0);
        coefficient.add(35.0);
        coefficient.add(-10.0);
        coefficient.add(1.0);


        // 例子：x*x*(x-1)=0
        // 展开：-x^2+x^3=0
        // coefficient.add(0.0);
        // coefficient.add(0.0);
        // coefficient.add(-1.0);
        // coefficient.add(1.0);

        // x(X-1)(X-1)=0   1x-2X^2+X^3=0 重根
        // coefficient.add(0.0);
        // coefficient.add(-6390602.560402107);
        // coefficient.add(-2007443.1650284305);
        // coefficient.add(279739.5270355313);
        // coefficient.add(132219.78612358586);
        // coefficient.add(1378.517178345849);
        // coefficient.add(-2485.0174374680964);
        // coefficient.add(-129.11619318672834);
        // coefficient.add(14.251298589495017);
        // coefficient.add(1.0);

        
        SolveEquation solveEquation = new SolveEquation(coefficient);
        System.out.print("方程为：");
        solveEquation.printEquation(solveEquation.getCoefficient());
        System.out.print("求导：");
        solveEquation.printEquation(solveEquation.getCoefficientDerivation());

        List<Double> ans = solveEquation.solveEquation(-10.0, 10.0);   // 设置解的上届与下届
        System.out.print("解为："); 
        System.out.println(ans);

    }
    
   
}