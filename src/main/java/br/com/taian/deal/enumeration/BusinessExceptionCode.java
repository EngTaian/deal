package br.com.taian.deal.enumeration;

import lombok.Getter;

@Getter
public enum BusinessExceptionCode {
    UNKNOWN(1);

    private Integer code;

    BusinessExceptionCode(Integer code) {
        this.code = code;
    }

}
