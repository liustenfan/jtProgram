package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class FileImage {
	// `{"error":0,"url":"图片的保存路径","width":图片的宽度,"height":图片的高度}`
	private Integer error;	//0 表示上传成功    1.表示上传失败
	private String url;		//用户图片的虚拟机地址
	private Integer width;
	private Integer height;
	
	public static FileImage fail() {
		
		return new FileImage(1, null, null, null);
	}
	
	public static FileImage success(String url,Integer width,Integer height) {
		
		return new FileImage(0, url, width, height);
	}
	
	
	
	
	
	
}
