package com.jt.manage.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.FileService;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	
	//要求上传完成后再次跳转到上传页面
	@RequestMapping("/file")
	public String file(MultipartFile file) throws IllegalStateException, IOException{
		
		//获取文件名称
		String fileName = file.getOriginalFilename();
		File picFile = new File("E:/jt-upload/"+fileName);
		//直接输出图片
		file.transferTo(picFile);
		//重定向到文件页面
		return "redirect:/file.jsp";
	}
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult upload(MultipartFile uploadFile){
		
		return fileService.upload(uploadFile);
	}
	
	
	
	
	
	
	
	
}
