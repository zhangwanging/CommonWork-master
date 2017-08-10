package com.hzu.jpg.commonwork.enity.Bean;

/**
 * Created by Administrator on 2017/3/9.
 */

public class HelpPaymentBean {

    String date_ym;
    double social_security;
    double accumulation_fund;
    double income_tax;
    double help_payment_total;

    public String getDate_ym() {
        return date_ym;
    }

    public void setDate_ym(String date_ym) {
        this.date_ym = date_ym;
    }

    public double getAccumulation_fund() {
        return accumulation_fund;
    }

    public void setAccumulation_fund(double accumulation_fund) {
        this.accumulation_fund = accumulation_fund;
    }

    public double getIncome_tax() {
        return income_tax;
    }

    public void setIncome_tax(double income_tax) {
        this.income_tax = income_tax;
    }

    public double getSocial_security() {
        return social_security;
    }

    public void setSocial_security(double social_security) {
        this.social_security = social_security;
    }

    public double getHelp_payment_total() {
        return help_payment_total;
    }

    public void setHelp_payment_total(double help_payment_total) {
        this.help_payment_total = help_payment_total;
    }
}
