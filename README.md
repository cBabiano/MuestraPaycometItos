# sdkItos
Ejemplo de pago:
```Java
ItosApiClient client = ItosApiClient.getInstance(CONTEXT);
                    ItosConfiguration configuration = new ItosConfiguration.PaycometConfigurationBuilder(
                            "X-SOURCE",
                            "USER",
                            "PASSWORD"
                    ).build();
                    client.setConfiguration(configuration);
                    client.init(
                            new ItosCallbacks.OnItosResponse() {
                                @Override
                                public void onResponse(ItosResponse response) {

                                    client.payment(
                                            IMPORTE,
                                            ORDER,
                                            CONCEPTO,
                                            new ItosCallbacks.OnItosTransactionsResponse() {

                                                @Override
                                                public void onResponse(ItosTransaction response) {
                                                    // OK
                                                }

                                                @Override
                                                public void onError(ItosResponse response) {
                                                    // KO
                                                }
                                            }
                                    );
                                }

                                @Override
                                public void onError(ItosResponse response) {
                                    // FALLO INIT
                                }
                            }
                    );
```
