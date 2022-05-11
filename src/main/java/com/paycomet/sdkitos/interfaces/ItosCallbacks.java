package com.paycomet.sdkitos.interfaces;

import com.paycomet.sdkitos.model.Requests.ItosResponse;
import com.paycomet.sdkitos.model.Requests.ItosTransaction;

public class ItosCallbacks {

    public interface OnItosResponse {
        void onResponse(ItosResponse response);
        void onError(ItosResponse response);
    }

    public interface OnItosTransactionsResponse {
        void onResponse(ItosTransaction response);
        void onError(ItosResponse response);
    }
}
