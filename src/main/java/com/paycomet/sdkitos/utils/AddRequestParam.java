package com.paycomet.sdkitos.utils;

import com.google.gson.JsonObject;

public class AddRequestParam {

    /**
     *
     * @param mRequest
     * @param amount
     * @param orderId
     * @param description
     */
    public static void addPaymentParams(
            JsonObject mRequest,
            double amount,
            String orderId,
            String description

    ) {
        mRequest.addProperty("amount", amount);
        mRequest.addProperty("orderId", orderId);
        mRequest.addProperty("description", description);
    }

    /**
     *
     * @param mRequest
     * @param transactionId
     * @param amount
     * @param orderId
     * @param description
     */
    public static void addRefundParams(
            JsonObject mRequest,
            String transactionId,
            double amount,
            String orderId,
            String description

    ) {
        mRequest.addProperty("transactionId", transactionId);
        mRequest.addProperty("amount", amount);
        mRequest.addProperty("orderId", orderId);
        mRequest.addProperty("description", description);
    }
}
