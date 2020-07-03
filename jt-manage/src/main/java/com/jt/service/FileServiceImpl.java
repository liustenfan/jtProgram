package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.FileImage;

@Service
@PropertySource(value="classpath:/properties/image.properties",encoding="UTF-8")
public class FileServiceImpl implements FileService {
	
	@Value("${image.localDir}")
	private String localDir;		// = "E:/JT_IMAGE";	//磁盘路径根目录
	@Value("${image.urlPath}")
	private String urlPath;			// = "http://image.jt.com";	//虚拟路径的根目录
	
	
	/**
	 * 1.校验数据的有效性  上传的文件是图片
	 * 2.准备文件上传目录  分目录储存   按照时间维护划分  yyyy/MM/dd  hash方式 fastDFS 分布式文件存储系统
	 * 3.防止文件重名操作  UUID.文件后缀   
	 * 
	 * 磁盘真实路径:
	 * 	E:\JT_IMAGE\2020\06\19\abc.png
	 *网络虚拟路径: 
	 *	http://image.jt.com\2020\06\19\abc.png
	 */
	@Override
	public FileImage upload(MultipartFile uploadFile) {
		//abc.jpg ABC.JPG
		String fileName = uploadFile.getOriginalFilename();
 		//1.将所有的文件类型转化为小写
		fileName = fileName.toLowerCase();
		//2.利用正则表达式判断字符串类型是否属于图片
		if(!fileName.matches("^.+\\.(jpg|png|gif)$")) {
			
			return FileImage.fail();
		}
		
		//3.将当前时间转化为目录结构
		String dateDir = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
		//拼接文件存储的本地路径   E:/JT_IMAGE/2020/06/19/
		String realLocalDir = localDir + dateDir;
		File fileDir = new File(realLocalDir);
		if(!fileDir.exists()) {
			//如果目录不存在,则创建目录
			fileDir.mkdirs();
		}
		
		//4.防止文件重名 利用uuid.jpg的形式完成
		String uuid = UUID.randomUUID().toString().replace("-", "");
		//获取文件后缀
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		String realFileName = uuid + fileType;
		
		//5.准备文件存储对象
		String realFilePath = realLocalDir + realFileName;
		File imageFile = new File(realFilePath);
		
		
		try {
			//获取图片宽度和高度.
			BufferedImage bufferedImage = 
						ImageIO.read(uploadFile.getInputStream());
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			//实现文件上传
			uploadFile.transferTo(imageFile);
			
			//定义虚拟路径
			String realURl = urlPath + dateDir + realFileName;
			return FileImage.success(realURl,width,height);
			
		} catch (IllegalStateException | IOException e) {
			
			e.printStackTrace();
			return FileImage.fail();
		}
	}
}
