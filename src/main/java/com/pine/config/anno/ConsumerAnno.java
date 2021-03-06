package com.pine.config.anno;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.pine.service.consumer.ConsumerUserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author pine
 * @date 20200119
 */
public class ConsumerAnno {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        System.out.println("---------dubbo启动成功--------");
        ConsumerUserServiceImpl consumerUserService = context.getBean(ConsumerUserServiceImpl.class);
        String username = consumerUserService.getUsername();
        System.out.println("username: " + username);
    }

    @Configuration
    @EnableDubbo(scanBasePackages = "com.pine.service.consumer")
    @ComponentScan(value = {"com.pine.service.consumer"})
    static class ConsumerConfiguration {
        @Bean
        public ApplicationConfig applicationConfig() {
            ApplicationConfig applicationConfig = new ApplicationConfig();
            // 设置服务名
            applicationConfig.setName("dubbo-config");
            return applicationConfig;
        }

        @Bean
        public ConsumerConfig consumerConfig() {
            ConsumerConfig consumerConfig = new ConsumerConfig();
            consumerConfig.setTimeout(3000);
            return consumerConfig;
        }

        @Bean
        public RegistryConfig registryConfig() {
            // 注册信息
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setProtocol("zookeeper");
            registryConfig.setAddress("127.0.0.1");
            registryConfig.setPort(2181);
            return registryConfig;
        }
    }
}
