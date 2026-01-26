package com.bravo.credit.client.application.async.BrokerConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "app.broker")
public class BrokerProperties {

    private Map<String, BindingConfig> bindings = new HashMap<>();

    public Map<String, BindingConfig> getBindings() {
        return bindings;
    }

    public void setBindings(Map<String, BindingConfig> bindings) {
        this.bindings = bindings;
    }

    public BindingConfig mustGet(String alias) {
        BindingConfig cfg = bindings.get(alias);
        if (cfg == null) throw new IllegalArgumentException("Alias no configurado: " + alias);
        return cfg;
    }

    public static class BindingConfig {
        private String exchange;
        private String routingKey;
        private String queue;

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getRoutingKey() {
            return routingKey;
        }

        public void setRoutingKey(String routingKey) {
            this.routingKey = routingKey;
        }

        public String getQueue() {
            return queue;
        }

        public void setQueue(String queue) {
            this.queue = queue;
        }
    }
}