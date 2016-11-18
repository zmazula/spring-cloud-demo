package com.epam.demo.model;


import com.datastax.driver.core.utils.UUIDs;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.util.UUID;

/**
 * Created by Zoltan_Mazula on 11/3/2016.
 */
@Table(value = "cardinfo")
public class MidBean {

    @PrimaryKey
    private UUID midId;
    private String cardCompany;
    private String cardNetwork;
    private String customerName;
    private String customerCode;
    private double payInterest;

    public MidBean(UUID midId, String cardCompany) {
        this.midId = midId;
        this.cardCompany = cardCompany;
    }

    public MidBean() {
        midId = UUIDs.timeBased();
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
