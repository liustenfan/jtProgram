package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.FileImage;

@RestController
public class FileController {
	
	/**
	 * 完成文件上传入门案例
	 * 
	 * url:/file
	 * 参数: fileImage
	 * 返回值: String 
	 * 
	 * SpringMVC中如果需要接收文件参数.则一般利用MultipartFile
	 * 来接收参数....
	 * 文件上传步骤:
	 * 		1.定义指定的文件目录   E:\JT_IMAGE
	 * 		2.获取文件名称
	 * 		3.将数据写入磁盘,执行文件上传操作.
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	
	@RequestMapping("/file")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
		
		//1.创建目录
		File file = new File("E:\\JT_IMAGE");
		//2.判断文件目录是否存在
		if(!file.exists()) {
			//如果目录不存在则新建目录
			file.mkdirs();	//创建多级目录.
		}
		
		//3.获取文件名称
		String fileName = fileImage.getOriginalFilename();
		
		//4.封装文件对象
		File realFile = new File("E:/JT_IMAGE/"+fileName);
		
		//5.实现文件上传
		fileImage.transferTo(realFile);//将字节信息,按照指定的文件位置进行输出.
		return "文件上传成功!!!!!";
	}
	
	
	
	@Autowired
	private FileService  fileService;
	
	/**
	 * 实现图片上传
	 * url地址:Request URL: http://localhost:8091/pic/upload
	      请求参数: uploadFile 
             返回值结果: FileImage
	 */
	@RequestMapping("/pic/upload")
	public FileImage upload(MultipartFile uploadFile) {
		
		return fileService.upload(uploadFile);
	}
	
	
	
	
	
	
	
	
	
}
