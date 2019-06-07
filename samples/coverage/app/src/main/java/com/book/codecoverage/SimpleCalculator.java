package com.book.codecoverage;

public class SimpleCalculator {

    public enum Operation {
        SUM,
        MULTIPLY
    };

    public static int sum(int a, int b) {
        return a+b;
    }

    public static int mathOperation(Operation operation, int a, int b){
        if (operation.equals(Operation.SUM)){
            return sum(a,b);
        }else if(operation.equals(Operation.MULTIPLY)){
            return multiply(a,b);
        }else{
            return 0;
        }
    }

    public static int multiply(int a, int b) {
        return a*b;
    }
}
