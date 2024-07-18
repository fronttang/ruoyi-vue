package com.ruoyi.common.constant;

/**
 * 缓存的key 常量
 * 
 * @author ruoyi
 */
public class CacheConstants {
	/**
	 * 登录用户 redis key
	 */
	public static final String LOGIN_TOKEN_KEY = "login_tokens:";

	/**
	 * 验证码 redis key
	 */
	public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

	/**
	 * 参数管理 cache key
	 */
	public static final String SYS_CONFIG_KEY = "sys_config:";

	/**
	 * 字典管理 cache key
	 */
	public static final String SYS_DICT_KEY = "sys_dict:";

	/**
	 * 防重提交 redis key
	 */
	public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

	/**
	 * 限流 redis key
	 */
	public static final String RATE_LIMIT_KEY = "rate_limit:";

	/**
	 * 登录账户密码错误次数 redis key
	 */
	public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

	/**
	 * 用户选择的项目
	 */
	public static final String USER_PROJECT_KEY = "user:project:";

	/**
	 * 用户选择的角色
	 */
	public static final String USER_PROJECT_ROLE_KEY = "user:project:role:";

	/**
	 * 业主单元轮次推进步骤
	 */
	public static final String UNIT_ROUND_STEP = "unit:round:step:";

	/**
	 * 推进状态
	 */
	public static final String UNIT_ROUND_STEP_STATUS = "unit:round:step:status:";
}
