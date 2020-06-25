package com.layui.util;

/**
 * 接口规范，返回信息状态对应枚举
 * @author lgf add 2017-09-21
 *
 */
public enum ResultStatus {
	SUCCESS(0, "Success"),//成功
	UNKNOW(1, "Unknown error"),//未知错误
	NO_RESULT_DATA(4,"No relevant data"),//没有相关数据
	UPDATE_FAILED(1001, "Update Failed"),//修改失败
	DELETE_FAILED(1002, "Delete Failed"),//删除失败
	ADD_FAILED(1003, "Add Failed"),//添加失败
	OPERATE_FAILED(1004, "Operate Failed"),//操作失败;
	EXIST_FAILED(1006,"name is exist"),//名字已存在
	PANO_FAILED_1(201,"线程启动错误"),
	PANO_FAILED_2(202,"图片格式或其他问题无法切图"),
	PANO_FAILED_3(203,"读取输入流错误"),
	PANO_FAILED_4(204,"关闭输入流错误"),
	PANO_FAILED_5(205,"未知错误"),
	PANO_SUCCESS(200, "全景图片切图成功"),
	USED_FAILED(1007,"used"),
	FILE_UPLOAD_FAILED(1005, "Upload　File Failed");//文件上传失败;
	private  int code;
	private  String msg;
	
	private ResultStatus(int code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public static String getName(int code) {
        for (ResultStatus r : ResultStatus.values()) {
            if (r.getCode() == code) {
                return r.msg;
            }
        }
        return null;
    }


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
