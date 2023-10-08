package com.nimang.pupa.business.controller;


import com.nimang.pupa.common.annotation.IgnoreAuth;
import com.nimang.pupa.common.tool.enumsTool.EnumUtil;
import com.nimang.pupa.common.tool.enumsTool.EnumVO;
import com.nimang.pupa.common.tool.webTool.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 配置/枚举
 * @module pupa
 * @date 2023-04-28
 */

@Log4j2
@RestController
@RequestMapping(value = "/pupa/enum")
@RequiredArgsConstructor
public class EnumController {
	// 如使用apifox插件上传接口，对于已经开发测试完毕并在文档上进行过修改的接口，建议在注释上加@ignore，避免再次上传时覆盖修改记录
	// @status常用值：开发中-developing、已发布-released

	private final EnumUtil enumUtil;

	/**
	 * 根据标签获取枚举信息
	 * @param label 标签
	 * @param sort 排序（V0：按值正序，V1：按值逆序，D0：按注释正序，D1：按注释逆序）
	 * @return R<List<EnumVO>>
	 * @status released
	 */
	@IgnoreAuth
	@GetMapping("/getEnums")
	public R<List<EnumVO>> getEnums(@NotBlank(message = "缺少标签") String label, String sort) {
		return R.ok(enumUtil.getEnums(label, sort));
	}
}
