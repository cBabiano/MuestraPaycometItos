package com.paycomet.sdkitos.model.Basic;

import static com.paycomet.sdkitos.utils.Constants.TICKET_AUTHORIZATION;
import static com.paycomet.sdkitos.utils.Constants.TICKET_CARD_BANK;
import static com.paycomet.sdkitos.utils.Constants.TICKET_CARD_NUMBER;
import static com.paycomet.sdkitos.utils.Constants.TICKET_CARD_TYPE;
import static com.paycomet.sdkitos.utils.Constants.TICKET_DATE;
import static com.paycomet.sdkitos.utils.Constants.TICKET_ID;
import static com.paycomet.sdkitos.utils.Constants.TICKET_LOCATION;
import static com.paycomet.sdkitos.utils.Constants.TICKET_MERCHANT_ID;
import static com.paycomet.sdkitos.utils.Constants.TICKET_MERCHANT_NAME;
import static com.paycomet.sdkitos.utils.Constants.TICKET_PAYCOMET_ID;
import static com.paycomet.sdkitos.utils.Constants.TICKET_TERMINAL_ID;
import static com.paycomet.sdkitos.utils.Constants.TICKET_TIME;

import com.google.gson.annotations.SerializedName;

public class ItosTicket {

    @SerializedName(TICKET_ID)
    private int id;

    @SerializedName(TICKET_AUTHORIZATION)
    private int authorization;

    @SerializedName(TICKET_CARD_BANK)
    private String cardBank;

    @SerializedName(TICKET_CARD_NUMBER)
    private String cardNumber;

    @SerializedName(TICKET_CARD_TYPE)
    private String cardType;

    @SerializedName(TICKET_DATE)
    private int date;

    @SerializedName(TICKET_TIME)
    private int time;

    @SerializedName(TICKET_TERMINAL_ID)
    private int terminalId;

    @SerializedName(TICKET_MERCHANT_ID)
    private int merchantId;

    @SerializedName(TICKET_PAYCOMET_ID)
    private int paycometId;

    @SerializedName(TICKET_LOCATION)
    private String location;

    @SerializedName(TICKET_MERCHANT_NAME)
    private String merchantName;

    public ItosTicket() { }

    public ItosTicket(int id, int authorization, String cardBank, String cardNumber, String cardType, int date, int time, int terminalId, int merchantId, int paycometId, String location, String merchantName) {
        this.id = id;
        this.authorization = authorization;
        this.cardBank = cardBank;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.date = date;
        this.time = time;
        this.terminalId = terminalId;
        this.merchantId = merchantId;
        this.paycometId = paycometId;
        this.location = location;
        this.merchantName = merchantName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorization() {
        return authorization;
    }

    public void setAuthorization(int authorization) {
        this.authorization = authorization;
    }

    public String getCardBank() {
        return cardBank;
    }

    public void setCardBank(String cardBank) {
        this.cardBank = cardBank;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public int getPaycometId() {
        return paycometId;
    }

    public void setPaycometId(int paycometId) {
        this.paycometId = paycometId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
