package com.dongfg.project.api.aop;

import com.ecwid.consul.v1.ConsulClient;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.stereotype.Component;

/**
 * 原本方法没有传入token导致 deregister 403
 *
 * @author dongfg
 * @date 18-1-25
 */
@Aspect
@Component
@Slf4j
public class ConsulServiceRegistryAop {

    @Autowired
    private ConsulClient client;

    @Autowired
    private ConsulDiscoveryProperties properties;

    @Around("execution(* org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry.deregister(..))")
    public Object deregister(ProceedingJoinPoint joinPoint) {
        ConsulRegistration reg = (ConsulRegistration) joinPoint.getArgs()[0];

        if (log.isInfoEnabled()) {
            log.info("Deregistering service with consul: " + reg.getInstanceId());
        }

        client.agentServiceDeregister(reg.getInstanceId(), properties.getAclToken());
        return null;
    }
}
