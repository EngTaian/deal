package br.com.taian.deal.enumeration;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public enum DealStatus {

    ACTIVE("Ativo"),
    INACTIVE("Inativo"),
    WAITING_CONFIRMATION_PAYMENT("Aguardando confirmação"),
    SOLD("Vendido")
    ;

    private String label;

    DealStatus(String label){
        this.label = label;
    }

    public static DealStatus valueOf(Integer ordinal){
        if(!ObjectUtils.isEmpty(ordinal)){
            for(DealStatus status : DealStatus.values()){
                if(status.ordinal() == ordinal)
                    return status;
            }
        }
        return null;
    }


}
