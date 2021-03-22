package com.sugar.route.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;
import java.util.Map;

/**
 * @author bytedance
 */
public class ConsulUtil {
    @Autowired
    private static DiscoveryClient discoveryClient;

    public static ServiceInstance getChatServerInstanceById(String id) {
        final List<ServiceInstance> instances = discoveryClient.getInstances("IMSERVER");
        for (ServiceInstance instance : instances) {
            Map<String, String> metadata = instance.getMetadata();
            String value = metadata.get("id");
            if (id.equals(value)) {
                return instance;
            }
        }
        return null;
    }
}
