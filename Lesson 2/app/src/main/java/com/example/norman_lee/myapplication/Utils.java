package com.example.norman_lee.myapplication;

public class Utils {
    static void checkInvalidInputs(String value){
        if ((Integer.parseInt(value) == 0) || (Integer.parseInt(value) < 0)){
            throw new IllegalArgumentException();
        }

        else if (isEmpty(value)){
            throw new NumberFormatException();
        }
    }

    static boolean isEmpty(String value){
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) != ' '){
                 return false;
            }
        }

        return true;
    }
}
