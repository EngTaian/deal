package br.com.taian.deal.enumeration;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public enum ArmoredType {

    I ("1"),
    IIA ("2A"),
    II ("2"),
    IIIA ("3A"),
    III ("3"),
    IV ("4"),
    V ("5");

    private String label;

    ArmoredType(String label){
        this.label = label;
    }

    public static ArmoredType valueOf(Integer ordinal){
        if(ObjectUtils.isEmpty(ordinal)){
            for(ArmoredType type : ArmoredType.values()){
                if(type.ordinal() == ordinal)
                    return type;
            }
        }
        return null;
    }


}
