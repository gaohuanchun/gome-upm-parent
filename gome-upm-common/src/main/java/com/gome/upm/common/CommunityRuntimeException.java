package com.gome.upm.common;

import com.gome.upm.common.util.ResponsesDTO;


/**
 * 远程调用运行时异常
 *
 * @author niulu
 * @date 2013-11-26
 */
public class CommunityRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -3243492968283757237L;

    private ResponsesDTO responsesDTO;

    public CommunityRuntimeException(String message) {
        super(message);
    }

    public CommunityRuntimeException(String message, ResponsesDTO responsesDTO) {
        super(message);
        this.responsesDTO = responsesDTO;
    }

    public CommunityRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ResponsesDTO getResponsesDTO() {
        return responsesDTO;
    }
}
