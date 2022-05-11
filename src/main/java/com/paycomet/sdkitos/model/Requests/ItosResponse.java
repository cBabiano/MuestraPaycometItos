package com.paycomet.sdkitos.model.Requests;

import static com.paycomet.sdkitos.utils.Constants.RESULT_CODE;
import static com.paycomet.sdkitos.utils.Constants.RESULT_MESSAGE;

import com.google.gson.annotations.SerializedName;

public class ItosResponse {

    @SerializedName(RESULT_CODE)
    private double resultCode;

    @SerializedName(RESULT_MESSAGE)
    private String resultMessage;

    public ItosResponse() { }

    /**
     *
     * @param resultCode
     * @param resultMessage
     */
    public ItosResponse(double resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    /**
     *
     * @param itosPostInit
     */
    public ItosResponse(ItosResponse itosPostInit) {
        this.resultCode = itosPostInit.getResultCode();
        this.resultMessage = itosPostInit.getResultMessage();
    }

    /**
     *
     * @param itosPostPayment
     */
    public ItosResponse(ItosTransaction itosPostPayment) {
        this.resultCode = itosPostPayment.getResultCode();
        this.resultMessage = itosPostPayment.getResultMessage();
    }

    public double getResultCode() {
        return resultCode;
    }

    public void setResultCode(double resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
