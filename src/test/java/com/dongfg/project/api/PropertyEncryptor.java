package com.dongfg.project.api;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

import java.util.Scanner;

/**
 * @author dongfg
 * @date 2017/12/21
 */
public class PropertyEncryptor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input Encryptor Password:");
        String password = scanner.nextLine();
        System.setProperty("jasypt.encryptor.password", password);

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        while (true) {
            System.out.print("Property Value:");
            String value = scanner.next();
            System.out.println(encryptor.encrypt(value));
            System.out.println();
        }
    }
}