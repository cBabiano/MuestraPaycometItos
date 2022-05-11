package com.paycomet.sdkitos.model.Basic;

public class ItosConfiguration {

    /**
     * X-SOURCE
     */
    private String xsource;

    /**
     * Init credentials
     */
    private String user;
    private String password;

    /**
     * Populates the PaycometConfiguration instance with the provided values.
     *
     * @param builder
     */
    private ItosConfiguration(PaycometConfigurationBuilder builder) {
        this.xsource = builder.xsource;
        this.user = builder.user;
        this.password = builder.password;
    }

    public String getXsource() {
        return xsource;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Builder class for the PaycometConfiguration instance
     */
    public static class PaycometConfigurationBuilder {
        private String xsource;
        private String user;
        private String password;

        public PaycometConfigurationBuilder(String xsource, String user, String password) {
            this.xsource = xsource;
            this.user = user;
            this.password = password;
        }

        public ItosConfiguration build() {
            return new ItosConfiguration(this);
        }
    }

}
