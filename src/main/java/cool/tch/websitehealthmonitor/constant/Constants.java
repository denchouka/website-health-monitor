package cool.tch.websitehealthmonitor.constant;

public class Constants {

    /**
     * 钉钉群机器人webhook地址
     */
    public static final String DINGTALK_WEBHOOK_URL = "https://oapi.dingtalk.com/robot/send";

    /**
     * http状态码200
     */
    public static final String HTTP_CODE_OK = "200";

    /**
     * 时间格式
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 每日存活检查：标题
     */
    public static final String DAILY_HEALTH_CHECK_TITLE = "⚙️网站健康监测服务 · 每日存活检查";

    /**
     * 每日存活检查：内容
     */
    public static final String DAILY_HEALTH_CHECK_TEXT =
            """
            #### %s
            ---
            🕗 早上好！
            网站健康监测服务 正常运行中 ☀️
            """;

    /**
     * 周期性健康检查：标题
     */
    public static final String PERIODIC_HEALTH_CHECK_TITLE = "⚙️网站健康监测 · 异常告警";

    /**
     * 周期性健康检查：内容
     */
    public static final String PERIODIC_HEALTH_CHECK_TEXT =
            """
            #### %s
            ---
            🌐监测网站：  *[%s](%s)*
            ⏰监测时间：   *%s*
            🛠️监测结果：  🚨*%s*
            📢响应状态：  🔴*%s*
            
            ---
            ⚠️ 请立即关注，尽快排查网站服务状态
            """;

    /**
     * 周期性健康检查：结果
     */
    public static final String PERIODIC_HEALTH_CHECK_RESULT = "网站不可用";

    /**
     * HttpClient超时时间（s）
     */
    public static final int HTTP_CLIENT_TIMEOUT = 5;

    /**
     * HttpRequest超时时间（s）
     */
    public static final int HTTP_REQUEST_TIMEOUT = 10;

    /**
     * 请求最大次数
     */
    public static final int REQUEST_MAX_COUNT = 3;

    /**
     * 线程睡眠时间（ms）
     */
    public static final int THREAD_SLEEP_TIME = 5 * 1000;
}
