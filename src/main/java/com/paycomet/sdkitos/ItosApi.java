package com.paycomet.sdkitos;

import com.google.gson.JsonObject;
import com.paycomet.sdkitos.model.Requests.ItosResponse;
import com.paycomet.sdkitos.model.Requests.ItosTransaction;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ItosApi {

    // INIT
    @POST("/v1/transactions/init")
    Observable<ItosResponse> postInit(@Header("X-SOURCE") String xsource, @Body JsonObject request);

    // PAYMENT
    @POST("/v1/transactions/payment")
    Observable<ItosTransaction> postPayment(@Header("X-SOURCE") String xsource, @Body JsonObject request);

    // REFUND
    @POST("/v1/transactions/refund")
    Observable<ItosTransaction> postRefund(@Header("X-SOURCE") String xsource, @Body JsonObject request);

    // LAST TRANSACTION
    @GET("/v1/transactions/last")
    Observable<ItosTransaction> getLastTransaction(@Header("X-SOURCE") String xsource);

    // LAST TRANSACTION
    @GET("/v1/transactions/cancel")
    Observable<ItosTransaction> getCancelTransactions(@Header("X-SOURCE") String xsource); // TODO no se que response tiene

    // PRINT LAST TRANSACTION
    @GET("/v1/transactions/last/print")
    Observable<ItosTransaction> getPrintLastTransaction(@Header("X-SOURCE") String xsource); // TODO no se que response tiene


}
