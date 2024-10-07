package com.mark.lee.springbootdemo.demos.drools.entity;

public class Order {

    private Integer amount;
    private Integer score;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Order{" +
                "amount=" + amount +
                ", score=" + score +
                '}';
    }
}
