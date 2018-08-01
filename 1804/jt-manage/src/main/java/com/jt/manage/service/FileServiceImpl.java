package com.jt.manage.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;

@Service
public class FileServiceImpl implements FileService{
	
	private String fileDir = "E:/jt-upload/";//文件存储路径
	private String urlDir = "http://image.jt.com/";
	
	/**
	 * 1.判断是否为图片  jpg|png|gif
	 * 木马.exe----木马.exe.png
	 * 2.判断是否为恶意程序.
	 * 3.为了图片检索方便,采用分文件存储
	 * 	3.1 UUID  1swdfsdfasd-asdfasdf--234234-sfsdfs(很少)
	 *  3.2 时间      yyyy/MM/dd/
	 * 4.用户上传的图片可能重名 UUID+随机数3位
	 * 5.实现文件上传
	 */
	@Override
	public PicUploadResult upload(MultipartFile uploadFile) {
		
		PicUploadResult result = new PicUploadResult();
		//1.获取图片名称  abc.jpg
		String fileName = uploadFile.getOriginalFilename();
		
		//2判断是否为图片类型
		if(!fileName.matches("^.*(jpg|png|gif)$")){
			//表示不是图片
			result.setError(1);
			return result;
		}
		try {
			//3.判断程序是否为恶意代码
			BufferedImage bufferedImage = 
					ImageIO.read(uploadFile.getInputStream());
			
			//3.1获取宽度和高度
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			//3.2判断是否有宽高
			if(0 == width || 0 == height){
				result.setError(1);
				return result;
			}
			
			//4.准备分文件存储路径
			String dateDir = 
			new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			
			//5.准备文件保存的目录    E:/jt-upload/2018/11/11
			String filePath = fileDir + dateDir;
			
			File imageFile = new File(filePath);
			
			//判断文件夹是否存在
			if(!imageFile.exists()){
				//imageFile.mkdir();  //创建一级目录
				imageFile.mkdirs(); //创建多级目录
			}
			
			//6.生成全新文件名称    .jpg
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			
			String uuid = UUID.randomUUID().toString().replace("-","");
			
			int random = new Random().nextInt(999); 
			
			//13123123.jpg
			String realFileName = uuid + random + fileType;
			
			//E:/jt-upload/2018/11/11/13123123.jpg
			String localFilePath = filePath + "/" +realFileName;
			
			uploadFile.transferTo(new File(localFilePath));
			
			//为返回值封装数据
			result.setHeight(height+"");
			result.setWidth(width+"");
			//result.setUrl("http://img14.360buyimg.com/n0/jfs/t6022/354/2910675640/195383/b714f20c/59494c80Na4c82fb7.jpg");
			
			//http://image.jt.com/2018/11/11/abc.jpg
			String urlPath = urlDir + dateDir + "/" + realFileName;
			result.setUrl(urlPath);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(1);
			return result;
		}
		return result;
	}
}
