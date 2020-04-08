package com.deku.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.ObjectUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDomain {

    private Integer code;

    private String message;

    private String responseCode;

    private Object responseDomain;

    public boolean success() {
        return ObjectUtils.equals(code, 200);
    }
}
