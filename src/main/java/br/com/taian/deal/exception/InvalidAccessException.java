package br.com.taian.deal.exception;

import br.com.taian.deal.enumeration.BusinessExceptionCode;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.com.taian.deal.enumeration.BusinessExceptionCode.UNKNOWN;

@Data
public class InvalidAccessException extends RuntimeException {

    public InvalidAccessException(String message){
        super(message);
    }

}
