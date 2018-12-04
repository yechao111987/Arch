package com.demo.yechao.arch;

import com.alibaba.fastjson.JSON;
import com.demo.yechao.arch.vo.PayInfo;
import org.junit.Test;


/**
 * @Author yechao111987@126.com
 * @date 2018/11/29 19:59
 */
public class Calculate {

    /**
     * 等额本金
     *
     * @param principal
     * @param months
     * @param rate
     * @param current_month
     * @return
     */
    public static PayInfo calculateEqualPrincipal(double principal, int months, double rate, int current_month) {
        double capital_per_month = principal / months;
        double interest = (principal - (current_month - 1) * capital_per_month) * rate / 12;
        double payPerMonth = capital_per_month + interest;
        PayInfo payInfo = new PayInfo();
        payInfo.setPayPerMonth(payPerMonth);
        payInfo.setRate(rate);
        double totalInterest = calTotalInterest(principal, rate, months);
        payInfo.setInterest(totalInterest);
        payInfo.setMonth(current_month);
        payInfo.setPrincipal(principal);
        payInfo.setTotal(principal + totalInterest);
        return payInfo;
    }


    /**
     * 贷款总金额为A，月利率为β，贷款期数为k，每期需还款总金额（本金+利息）为x
     * 等额本息每期还款本息总额 x = A * β * (1 + β) ^ k / [(1 + β) ^ k - 1]
     * <p>
     */
    public static double calTotalInterest(double principal, double rate, int months) {
        double capital_per_month = principal / months;
        double totalInterest = 0;
        for (int current_month = 1; current_month <= months; current_month++) {
            double interest = (principal - (current_month - 1) * capital_per_month) * rate / 12;
            totalInterest += interest;
        }
        System.out.println(totalInterest);
        return totalInterest;
    }


    /**
     * 计算等额本息还款
     *
     * @param principal 贷款总额
     * @param months    贷款期限
     * @param rate      贷款利率
     * @return
     */
    public static PayInfo calculateEqualPrincipalAndInterest(double principal, int months, double rate, int current_month) {
        PayInfo payInfo = new PayInfo();
        double monthRate = rate / 12;//月利率
        //每月还款金额
        double preLoan = (principal * monthRate * Math.pow((1 + monthRate), months)) / (Math.pow((1 + monthRate), months) - 1);
        double totalMoney = preLoan * months;//还款总额
        double interest = totalMoney - principal;//还款总利息
        payInfo.setPrincipal(principal);
        payInfo.setTotal(interest + principal);
        payInfo.setInterest(interest);
        payInfo.setMonth(current_month);
        payInfo.setRate(rate);
        payInfo.setPayPerMonth(preLoan);
        return payInfo;
    }

    @Test
    public void testCalculateEqualPrincipal() {
        PayInfo payInfo = calculateEqualPrincipal(1000000, 360, 0.049, 1);
        double total = calTotalInterest(1000000, 0.049, 360);
        System.out.println(total);
        System.out.println(JSON.toJSONString(payInfo));
    }

    @Test
    public void testCalculateEqualPrincipalAndInterest() {
        PayInfo payInfo = calculateEqualPrincipalAndInterest(1000000, 360, 0.049, 1);
        System.out.println(JSON.toJSONString(payInfo));
    }
}
