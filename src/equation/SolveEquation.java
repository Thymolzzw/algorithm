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

        this.printEquation(this.coefficient);
        this.doDerivation();
        this.printEquation(this.coefficientDerivation);
    }

    public void doDerivation(){
        this.coefficientDerivation = new ArrayList<Double>();
        for(int i=0;i<this.coefficient.size();i++){
            // 初始化this.coefficientDerivation的i位置参数
            this.coefficientDerivation.add(0.0);
            // 新系数x, 与更新位置j
            double x=i*this.coefficient.get(i);
            int j=Math.max(i-1, 0);
            // 求导 更新
            this.coefficientDerivation.set(j, x);
        }
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
            System.out.print(coefficient.get(i) + "x^" + (i) + "+");
        }
        // System.out.println(this.coefficient.get(this.coefficient.size()-1));
        System.out.println();
    }

    // 迭代求解
    public double doStep(double x){

        int i = 0;
        while(Math.abs(doCalculation(this.coefficient, x))>1e-6){

            // System.out.println(i++);
            x=x-doCalculation(this.coefficient, x)/doCalculation(this.coefficientDerivation, x);
        }
        return x;
    }


    public static void main(String[] args) {
        List<Double> coefficient = new ArrayList<Double>();
        coefficient.add(-0.2);
        coefficient.add(-1.0);
        coefficient.add(1.0);
        coefficient.add(0.0);
        coefficient.add(0.0);
        coefficient.add(1.0);
        SolveEquation solveEquation = new SolveEquation(coefficient);
        double a = solveEquation.doStep(3.0);
        System.out.println(a);

        System.out.println(solveEquation.doCalculation(solveEquation.getCoefficient(), a));
    }
    
   
}