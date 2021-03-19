package com.brabos.bahia.cursoSpring.domain.enums;

public enum PaymentState {


    PENDING(1, "PENDING"),
    PAID(2, "PAID"),
    CANCELED(3, "CANCELED");

    private int code;
    private String description;

    private PaymentState(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PaymentState toEnum(Integer code){
        if(code == null){
            return null;
        }
        for(PaymentState x : PaymentState.values()){
            if(code.equals(x.getCode())){
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + code);
    }
}
