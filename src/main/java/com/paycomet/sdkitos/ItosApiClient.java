package com.paycomet.sdkitos;

import static com.paycomet.sdkitos.utils.AddRequestParam.addPaymentParams;
import static com.paycomet.sdkitos.utils.AddRequestParam.addRefundParams;
import static com.paycomet.sdkitos.utils.Constants.API_URL;
import static com.paycomet.sdkitos.utils.Constants.RESULT_CODE;
import static com.paycomet.sdkitos.utils.Constants.RESULT_MESSAGE;
import static com.paycomet.sdkitos.utils.Constants.SHARED_PREFERENCES_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.paycomet.sdkitos.interfaces.ItosCallbacks;
import com.paycomet.sdkitos.model.Basic.ItosConfiguration;
import com.paycomet.sdkitos.model.Requests.ItosResponse;
import com.paycomet.sdkitos.model.Requests.ItosTransaction;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItosApiClient {

    /**
     * A shared singleton API client
     */
    private static ItosApiClient mInstance = null;

    /**
     * The client's configuration
     */
    private ItosConfiguration mConfiguration;

    /**
     * A client for making requests to the Itos API
     */
    private ItosApi mItosApi;

    /**
     * Context for caching the IP in the SharedPreferences
     */
    private static Context mContext;

    /**
     * Get SharedPreferences for storing and reading the IP
     */
    private static SharedPreferences mPreferences;

    private ItosApiClient() {
        // -----------------------------------------------------------------------------------------
        // Create a new Retrofit instance and build the API client based on it
        // -----------------------------------------------------------------------------------------
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(getUnsafeOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.mItosApi = mRetrofit.create(ItosApi.class);
    }

    public static synchronized ItosApiClient getInstance(Context context) {
        ItosApiClient.mContext = context;
        ItosApiClient.mPreferences = ItosApiClient.mContext
                .getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        if (mInstance == null) {
            mInstance = new ItosApiClient();

            return mInstance;
        }
        return mInstance;
    }

    public void setConfiguration(ItosConfiguration mConfiguration) {
        this.mConfiguration = mConfiguration;
    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This request initializes the ITOS Payment Services components. This call is necessary to be done at least once before any Financial Transactions are carried out in the terminal, otherwise during the Financial Transaction request the servicies will return the error PINPAD NOT INITIALIZED.
     * @param callback
     */
    public void init(
            ItosCallbacks.OnItosResponse callback
    ) {

        JsonObject request = createRequest();

        this.mItosApi.postInit(mConfiguration.getXsource(), request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itosPostInit -> {
                            if (itosPostInit.getResultCode() != 1000) {
                                callback.onError(new ItosResponse(itosPostInit));
                            } else {
                                callback.onResponse(new ItosResponse(itosPostInit));
                            }
                        },
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                HttpException error = (HttpException) throwable;
                                String errorBody = error.response().errorBody().string();
                                callback.onError(new ItosResponse(JsonParser.parseString(errorBody).getAsJsonObject().get(RESULT_CODE).getAsInt(), JsonParser.parseString(errorBody).getAsJsonObject().get(RESULT_MESSAGE).toString()));
                            }
                        });
    }

    /**
     * This request initiates a Payment Transaction.
     * @param amount
     * @param orderId
     * @param description
     * @param callback
     */
    public void payment(
            double amount,
            String orderId,
            String description,
            ItosCallbacks.OnItosTransactionsResponse callback
    ) {

        JsonObject request = createRequest();
        addPaymentParams(request, amount, orderId, description);

        this.mItosApi.postPayment(mConfiguration.getXsource(), request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itosPostPayment -> {
                            if (itosPostPayment.getResultCode() != 0) {
                                callback.onError(new ItosResponse(itosPostPayment));
                            } else {
                                callback.onResponse(new ItosTransaction(itosPostPayment));
                            }
                        },
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                HttpException error = (HttpException) throwable;
                                String errorBody = error.response().errorBody().string();
                                callback.onError(new ItosResponse(JsonParser.parseString(errorBody).getAsJsonObject().get(RESULT_CODE).getAsInt(), JsonParser.parseString(errorBody).getAsJsonObject().get(RESULT_MESSAGE).toString()));
                            }
                        });
    }

    /**
     * This request initiates a Refund transaction.
     * @param transactionId
     * @param amount
     * @param orderId
     * @param description
     * @param callback
     */
    public void refund(
            String transactionId,
            double amount,
            String orderId,
            String description,
            ItosCallbacks.OnItosTransactionsResponse callback
    ) {

        JsonObject request = createRequest();
        addRefundParams(request, transactionId, amount, orderId, description);

        this.mItosApi.postRefund(mConfiguration.getXsource(), request)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itosPostRefund -> {
                            if (itosPostRefund.getResultCode() != 0) {
                                callback.onError(new ItosResponse(itosPostRefund));
                            } else {
                                callback.onResponse(new ItosTransaction(itosPostRefund));
                            }
                        },
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                HttpException error = (HttpException) throwable;
                                String errorBody = error.response().errorBody().string();
                                callback.onError(new ItosResponse(JsonParser.parseString(errorBody).getAsJsonObject().get(RESULT_CODE).getAsInt(), JsonParser.parseString(errorBody).getAsJsonObject().get(RESULT_MESSAGE).toString()));
                            }
                        });
    }

    /**
     * This request returns a copy of the responce data of the last transaction carried out in the system.
     * @param callback
     */
    public void getLastTransaction(
            ItosCallbacks.OnItosTransactionsResponse callback
    ) {

        this.mItosApi.getLastTransaction(mConfiguration.getXsource())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itosGetLastTransaction -> {
                            if (itosGetLastTransaction.getResultCode() != 0) {
                                callback.onError(new ItosResponse(itosGetLastTransaction));
                            } else {
                                callback.onResponse(new ItosTransaction(itosGetLastTransaction));
                            }
                        },
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                HttpException error = (HttpException) throwable;
                                String errorBody = error.response().errorBody().string();
                                callback.onError(new ItosResponse(JsonParser.parseString(errorBody).getAsJsonObject().get(RESULT_CODE).getAsInt(), JsonParser.parseString(errorBody).getAsJsonObject().get(RESULT_MESSAGE).toString()));
                            }
                        });
    }

    /**
     * This request cancels the ongoing transaction.
     * @param callback
     */
    public void cancel(
            ItosCallbacks.OnItosResponse callback
    ) {

        this.mItosApi.getCancelTransactions(mConfiguration.getXsource())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itosCancel -> {
                            if (itosCancel.getResultCode() != 0) {
                                callback.onError(new ItosResponse(itosCancel));
                            } else {
                                callback.onResponse(new ItosTransaction(itosCancel));
                            }
                        },
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                HttpException error = (HttpException) throwable;
                                String errorBody = error.response().errorBody().string();
                                callback.onError(new ItosResponse(JsonParser.parseString(errorBody).getAsJsonObject().get(RESULT_CODE).getAsInt(), JsonParser.parseString(errorBody).getAsJsonObject().get(RESULT_MESSAGE).toString()));
                            }
                        });
    }

    /**
     * This request prints a copy of the last completed transaction carried out in the system.
     * @param callback
     */
    public void printLastTransaction(
            ItosCallbacks.OnItosResponse callback
    ) {

        this.mItosApi.getPrintLastTransaction(mConfiguration.getXsource())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(itosPrintLastTransaction -> {
                            if (itosPrintLastTransaction.getResultCode() != 0) {
                                callback.onError(new ItosResponse(itosPrintLastTransaction));
                            } else {
                                callback.onResponse(new ItosTransaction(itosPrintLastTransaction));
                            }
                        },
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                HttpException error = (HttpException) throwable;
                                String errorBody = error.response().errorBody().string();
                                callback.onError(new ItosResponse(JsonParser.parseString(errorBody).getAsJsonObject().get(RESULT_CODE).getAsInt(), JsonParser.parseString(errorBody).getAsJsonObject().get(RESULT_MESSAGE).toString()));
                            }
                        });
    }

    private JsonObject createRequest() {
        // -----------------------------------------------------------------------------------------
        // Create a new request JsonObject
        // -----------------------------------------------------------------------------------------
        JsonObject request = new JsonObject();
        return request;
    }
}
