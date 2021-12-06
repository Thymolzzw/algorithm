package equation;

import java.security.Principal;
import java.util.*;

import exhaustive.Digital;
import sun.plugin2.message.PrintAppletMessage;
import equation.SolveEquation;

public class jt {
    //生成随机根
    public static double[] generateZeros(double min, double max, int num){
        double [] zeros = new double[num];
        for (int i=0;i<num;++i){
            zeros[i] = Math.random()*(max-min)+min;
        }
        return zeros;
    }
    //生成系数数组
    public static double[] generateCoefficients(double[] zeros){
        double [] coefficients = new double[zeros.length+1];
        int[] sizes = new int[zeros.length];
        Arrays.fill(sizes,2);
        Digital dt = new Digital(sizes);
        while (dt.next()){
            int coef_id = 0;
            int [] states = dt.getStates();
            double coef = 1;
            for (int i=0;i<states.length;++i){
                if(states[i]==1) {
                    coef *= -1 * zeros[i];
                    coef_id++;
                }
            }
            coefficients[coefficients.length-1-coef_id] += coef;
        }
        return coefficients;
    }
    public static void sortprint(double[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    double temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
  

    
    public static void test1(double min, double max, boolean homogeneous, boolean sameZero, int testtimes){
        for (int i=0;i<testtimes;++i) {
            List<Double> coefficient = new ArrayList<Double>();

            int num = (int) (Math.random() * (10 - 1) + 1);
            double[] zeros = generateZeros(min, max, num);
            if (sameZero&&zeros.length<2) zeros = generateZeros(2, max, num);
            if (homogeneous) zeros[0] = 0;
            if (sameZero) zeros[zeros.length-1] = zeros[(zeros.length-1)/2];
            sortprint(zeros);
            double[] coefficients = generateCoefficients(zeros);
            for (double coef:coefficients){
                coefficient.add(coef);
            }

            SolveEquation solveEquation = new SolveEquation(coefficient);
            System.out.print("方程为：");
            solveEquation.printEquation(solveEquation.getCoefficient());
            System.out.print("求导：");
            solveEquation.printEquation(solveEquation.getCoefficientDerivation());

            List<Double> ans = solveEquation.solveEquation(min, max);
            System.out.print("解为：");
            System.out.println(ans);
        }
    }
    public static void main(String[] args){
        double min = -10.0, max =10.0;
//        test(min,max,true,false,100);
//        test(min,max,true,true,3);
//        test(min,max,false,false,100);
//        test(min,max,false,true,3);
        test1(min,max,true,true,10000);
    }
}
