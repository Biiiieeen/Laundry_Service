
package com.group4.laundry.model;

import java.math.BigDecimal;

public class Service {

    private int serviceId;
    private String serviceName;
    private BigDecimal pricePerKg;
    private BigDecimal maxKgPerLoad;

    public Service(int serviceId, String serviceName,
                   BigDecimal pricePerKg, BigDecimal maxKgPerLoad) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.pricePerKg = pricePerKg;
        this.maxKgPerLoad = maxKgPerLoad;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public BigDecimal getPricePerKg() {
        return pricePerKg;
    }

    public void setPricePerKg(BigDecimal pricePerKg) {
        this.pricePerKg = pricePerKg;
    }

    public BigDecimal getMaxKgPerLoad() {
        return maxKgPerLoad;
    }

    public void setMaxKgPerLoad(BigDecimal maxKgPerLoad) {
        this.maxKgPerLoad = maxKgPerLoad;
    }
}
