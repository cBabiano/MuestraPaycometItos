package com.paycomet.sdkitos.model.Requests;

import static com.paycomet.sdkitos.utils.Constants.ORDER_ID;
import static com.paycomet.sdkitos.utils.Constants.TICKET;

import com.google.gson.annotations.SerializedName;
import com.paycomet.sdkitos.model.Basic.ItosTicket;

public class ItosTransaction extends ItosResponse {

    @SerializedName(ORDER_ID)
    private String orderId;

    @SerializedName(TICKET)
    private ItosTicket ticket;

    public ItosTransaction() { }

    public ItosTransaction(double resultCode, String resultMessage, String orderId, ItosTicket ticket) {
        super(resultCode, resultMessage);
        this.orderId = orderId;
        this.ticket = ticket;
    }

    public ItosTransaction(ItosTransaction itosPostPayment) {
        super(itosPostPayment.getResultCode(), itosPostPayment.getResultMessage());
        this.orderId = itosPostPayment.getOrderId();
        this.ticket = itosPostPayment.getTicket();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ItosTicket getTicket() {
        return ticket;
    }

    public void setTicket(ItosTicket ticket) {
        this.ticket = ticket;
    }
}
