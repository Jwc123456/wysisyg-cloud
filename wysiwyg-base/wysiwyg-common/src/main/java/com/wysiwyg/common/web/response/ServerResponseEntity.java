package com.wysiwyg.common.web.response;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;

@Getter
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

    public void setCode(Integer code) {
		this.code = code;
	}

    public void setMsg(String msg) {
		this.msg = msg;
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
		serverResponseEntity.setMsg(ResponseEnum.OK.getMsg());
		return serverResponseEntity;
	}



	public static <T> ServerResponseEntity<T> fail(ResponseEnum responseEnum) {
		ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
		serverResponseEntity.setMsg(responseEnum.getMsg());
		serverResponseEntity.setCode(responseEnum.getCode());
		return serverResponseEntity;
	}

	public static <T> ServerResponseEntity<T> fail(ResponseEnum responseEnum, T data) {
		ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
		serverResponseEntity.setMsg(responseEnum.getMsg());
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
