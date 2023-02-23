package com.gofield.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtils {

    public static String makeRandomUuid(){
        return UUID.randomUUID().toString();
    }

    public static String makeRandomIntegratedCode(int size){
        Random random = new Random();
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < size; i++) {
            int choice = random.nextInt(3);
            switch(choice) {
                case 0:
                    buff.append((char)((int)random.nextInt(25)+97));
                    break;
                case 1:
                    buff.append((char)((int)random.nextInt(25)+65));
                    break;
                case 2:
                    buff.append((char)((int)random.nextInt(10)+48));
                    break;
                default:
                    break;
            }
        }
        return buff.toString();
    }

    public static String makeRandomNumberCode(int size){
        Random random = new Random();
        StringBuffer buff = new StringBuffer();
        for(int i=0 ; i<size ; i++){
            if(random.nextBoolean()){
                buff.append((char)((int)(random.nextInt(10)) + 48));
            } else {
                buff.append(random.nextInt(9));
            }
        }
        return buff.toString();
    }

    public static String makeRandomCode(int size){
        Random random = new Random();
        StringBuffer buff = new StringBuffer();
        for(int i=0 ; i<size ; i++){
            if(random.nextBoolean()){
                buff.append((char)((int)(random.nextInt(26)) + 65));
            } else {
                buff.append(random.nextInt(10));
            }
        }
        return buff.toString();
    }

}
