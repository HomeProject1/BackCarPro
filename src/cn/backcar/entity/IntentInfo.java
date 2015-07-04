package cn.backcar.entity;

import java.io.Serializable;

public class IntentInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String key = "";
	public String value = "";
	public int type;
	public int length;//��������
	public String unit = "";// ��λ
	
	public boolean needEdit;// �Ƿ���������

	public IntentInfo() {

	}

	public IntentInfo(String key, String value, int type,int length) {
		this.key = key;
		this.value = value;
		this.type = type;
		this.length=length;
	}

	public IntentInfo(String key, String value, int type, String unit,int length) {
		this.key = key;
		this.value = value;
		this.type = type;
       this.length=length;
		this.unit = unit;
	}

	
}
