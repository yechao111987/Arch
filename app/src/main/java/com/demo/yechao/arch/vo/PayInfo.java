package com.demo.yechao.arch.vo;

/**
 * @Author yechao111987@126.com
 * @date 2018/6/28 16:48
 */
public class PayInfo {
    private Double principal;
    private Double interest;
    private Double total;
    private Double rate;
    private Integer month;
    private Double payPerMonth;

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getPayPerMonth() {
        return payPerMonth;
    }

    public void setPayPerMonth(Double payPerMonth) {
        this.payPerMonth = payPerMonth;
    }
}
