package com.yiqi.api_backend.controller.common;

import cn.hutool.core.img.Img;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import com.yiqi.api_backend.common.lang.Result;
import com.yiqi.api_backend.security.StpKit;
import com.yiqi.api_backend.utils.PathUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * 图片上传
 */
@RestController
@RequestMapping("/common")
public class UploadController {

	@SneakyThrows
	@PostMapping("/uploadImg")
	public Result uploadImg(MultipartFile image, HttpServletRequest req) {
		Assert.isTrue(StpKit.USER.isLogin() || StpKit.ADMIN.isLogin(), "无权限操作");

		Assert.notNull(image, "请选择上传图片");
		Assert.isTrue(!image.isEmpty(), "请选择上传图片");

		// 图片类型判断
		String type = FileTypeUtil.getType(image.getInputStream());
		Assert.isTrue(Arrays.asList("png", "jpg", "jpeg").contains(type.toLowerCase()), "不支持该图片格式");

		String pre = System.getProperty("user.dir") + "/images/";
		if (!FileUtil.exist(pre)) {
			FileUtil.mkdir(pre);
		}

		String imageName = UUID.randomUUID() + "." + type.toLowerCase();

		Img.from(image.getInputStream())
				.scale(0.5F)
				.setQuality(0.3)
				.write(FileUtil.file(pre + imageName));

		// 访问路径
		String path = PathUtil.getImageDomain(req) + "/images/" + imageName;
		return Result.success(path);
	}

}
