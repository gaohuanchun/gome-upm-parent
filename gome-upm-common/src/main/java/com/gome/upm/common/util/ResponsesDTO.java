/**
 * 
 */
package com.gome.upm.common.util;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gome.upm.constants.ReturnCode;

/**
 * @author niulu
 * 
 *         2014年7月2日 下午12:04:32
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponsesDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code = ReturnCode.ACTIVE_FAILURE.code();
	private Object attach;
	
	public ResponsesDTO() {
		
	}

	public ResponsesDTO(ReturnCode returnCode) {
		this.code = returnCode.code();
		this.attach = returnCode.msg();
	}

	public void setReturnCode(ReturnCode returnCode) {
		this.code = returnCode.code();
		this.attach = returnCode.msg();
	}

	public ReturnCode nowReturnCode() {// 此处不能写getxx,会被spring 识别然后返回出去
		return ReturnCode.codeToEnum(code);
	}

	public ResponsesDTO(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getAttach() {
		return attach;
	}

	public void setAttach(Object attach) {
		this.attach = attach;
	}

}
