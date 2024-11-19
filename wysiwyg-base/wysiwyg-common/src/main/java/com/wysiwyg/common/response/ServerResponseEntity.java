package com.wysiwyg.common.response;


import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;

@Slf4j
public class ServerResponseEntity<T> implements Serializable {


	/**
	 * 状态码
	 */
	private Integer code;

	/**
	 * 信息
	 */
	private String msg;

	/**
	 * 数据
	 */
	private T data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return Objects.equals(ResponseEnum.OK.getCode(), this.code);
	}

	@Override
	public String toString() {
		return "ServerResponseEntity{" + "code=" + code + ", msg='" + msg + '\'' + ", data=" + data + '}';
	}

	public static <T> ServerResponseEntity<T> success(T data) {
		ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
		serverResponseEntity.setData(data);
		serverResponseEntity.setCode(ResponseEnum.OK.getCode());
		return serverResponseEntity;
	}

	public static <T> ServerResponseEntity<T> success() {
		ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
		serverResponseEntity.setCode(ResponseEnum.OK.getCode());
		serverResponseEntity.setMsg(ResponseEnum.OK.getMessage());
		return serverResponseEntity;
	}



	public static <T> ServerResponseEntity<T> fail(ResponseEnum responseEnum) {
		ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
		serverResponseEntity.setMsg(responseEnum.getMessage());
		serverResponseEntity.setCode(responseEnum.getCode());
		return serverResponseEntity;
	}

	public static <T> ServerResponseEntity<T> fail(ResponseEnum responseEnum, T data) {
		ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
		serverResponseEntity.setMsg(responseEnum.getMessage());
		serverResponseEntity.setCode(responseEnum.getCode());
		serverResponseEntity.setData(data);
		return serverResponseEntity;
	}

	public static <T> ServerResponseEntity<T> transform(ServerResponseEntity<?> oldServerResponseEntity) {
		ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
		serverResponseEntity.setMsg(oldServerResponseEntity.getMsg());
		serverResponseEntity.setCode(oldServerResponseEntity.getCode());
		return serverResponseEntity;
	}

}
