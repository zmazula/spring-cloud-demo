package com.epam.demo.DTO;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.UUID;

/**
 * Created by zmazula on 11/11/16.
 */
public class MidBeanDTO {

    private UUID midId;
    private String cardCompany;
    private String cardNetwork;
    private String customerName;
    private String customerCode;
    private double payInterest;

    public MidBeanDTO(UUID midId, String cardCompany) {
        this.midId = midId;
        this.cardCompany = cardCompany;
    }

    public MidBeanDTO() {

    }

    public String getCardCompany() {
        return cardCompany;
    }

    public void setCardCompany(String cardCompany) {
        this.cardCompany = cardCompany;
    }

    public UUID getMidId() {
        return midId;
    }

    public void setMidId(UUID midId) {
        this.midId = midId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getCardNetwork() {
        return cardNetwork;
    }

    public void setCardNetwork(String cardNetwork) {
        this.cardNetwork = cardNetwork;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public double getPayInterest() {
        return payInterest;
    }

    public void setPayInterest(double payInterest) {
        this.payInterest = payInterest;
    }
}
