package cool.tch.websitehealthmonitor.message;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

import static cool.tch.websitehealthmonitor.constant.Constants.DINGTALK_WEBHOOK_URL;

/**
 * 钉钉群聊机器人
 */
public class DingTalkRobotGroupMessage {

    public static void sendMessage(String accessToken, String secret, String title, String text) {
        try {

            // 加签（将时间戳 timestamp 和密钥 secret 当做签名字符串，使用HmacSHA256算法计算签名，然后进行Base64 encode，
            // 最后再把签名参数再进行urlEncode，得到最终的签名（需要使用UTF-8字符集）。）
            Long timestamp = System.currentTimeMillis();
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");

            //sign字段和timestamp字段必须拼接到请求URL上，否则会出现 310000 的错误信息
            DingTalkClient client = new DefaultDingTalkClient(DINGTALK_WEBHOOK_URL + "?sign=" + sign + "&timestamp=" + timestamp);
            OapiRobotSendRequest req = new OapiRobotSendRequest();

            //设置消息类型
            req.setMsgtype("markdown");
            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle(title);
            markdown.setText(text);
            req.setMarkdown(markdown);

            client.execute(req, accessToken);
        } catch (Exception e) {
            System.out.println("钉钉群聊机器人推送消息失败。。。");
            e.printStackTrace();
        }
    }
}
