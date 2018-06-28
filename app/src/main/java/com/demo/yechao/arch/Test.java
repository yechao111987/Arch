package com.demo.yechao.arch;

import android.support.v4.widget.TextViewCompat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Bidi;
import java.util.ArrayList;

/**
 * @Author yechao111987@126.com
 * @date 2018/6/12 16:31
 */
public class Test {

    /**
     * 贷款总金额为A，月利率为β，贷款期数为k，每期需还款总金额（本金+利息）为x
     * 等额本息每期还款本息总额 x = A * β * (1 + β) ^ k / [(1 + β) ^ k - 1]
     * <p>
     */

    public static double calTotal(double sum, double rate, int period) {
        BigDecimal sumB = new BigDecimal(sum);
        BigDecimal rateB = new BigDecimal(rate);
        BigDecimal all = (sumB.multiply(rateB)).multiply((rateB.add(new BigDecimal(1))).pow(period));
        System.out.println(all.toString());
        BigDecimal a1 = ((rateB.add(new BigDecimal(1))).pow(period)).subtract(new BigDecimal(1));
        BigDecimal a2 = all.divide(a1, 2, RoundingMode.DOWN);
        System.out.println("a2:" + a2.toString());
        return a1.doubleValue();

    }


    /**
     * 计算等额本息还款
     *
     * @param principal 贷款总额
     * @param months    贷款期限
     * @param rate      贷款利率
     * @return
     */
    public static ArrayList<Double> calculateEqualPrincipalAndInterest(double principal, int months, double rate) {
        ArrayList<Double> data = new ArrayList<Double>();
        double monthRate = rate / 1.2;//月利率
        double preLoan = (principal * monthRate * Math.pow((1 + monthRate), months)) / (Math.pow((1 + monthRate), months) - 1);//每月还款金额
        double totalMoney = preLoan * months;//还款总额
        double interest = totalMoney - principal;//还款总利息
        data.add(new Double(totalMoney));//还款总额
        data.add(new Double(principal));//贷款总额
        data.add(new Double(interest));//还款总利息
        data.add(new Double(preLoan));//每月还款金额
//        data.add(new Double(months));//还款期限
        return data;
    }

    public static void main(String[] ars) {
        double sum = 1500000;
        double rate = 0.0049;
        int period = 360;
        double total = Test.calTotal(sum, rate, period);
        ArrayList<Double> lists = Test.calculateEqualPrincipalAndInterest(sum, period, rate);
        for (Double list : lists) {
            System.out.println("dd:" + list);

        }
        System.out.println("Total is " + total);


    }


}
