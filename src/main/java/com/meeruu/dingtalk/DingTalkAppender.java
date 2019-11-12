package com.meeruu.dingtalk;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.alibaba.fastjson.JSON;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author Gaoqs
 * @description: dingTalk组件化
 * @date 2019-11-11 14:22
 */
public class DingTalkAppender extends UnsynchronizedAppenderBase<LoggingEvent> {
    private static final String dingTaikURL = "https://oapi.dingtalk.com/robot/send?access_token=";
    private String pattern = "[%c%L] -%-5level [%thread] %msg%n";
    private PatternLayout layout;
    private String profilesActive = "unknown";
    private String dingdingToken = "unknown";
    private String appName = "unknown";
    private String env = "unknown";

    @Override
    protected void append(LoggingEvent eventObject) {
      try {
        if(Level.ERROR.equals(eventObject.getLevel())) {
          String toMsg = layout.doLayout(eventObject);
          toDingDing(dingTaikURL + getDingdingToken(), toMsg);
        }
      }catch (Exception e){
        e.printStackTrace();
      }
    }

    @Override
    public void start() {
      PatternLayout patternLayout = new PatternLayout();
      patternLayout.setContext(context);
      patternLayout.setPattern(getPattern());
      patternLayout.start();
      this.layout = patternLayout;
      super.start();
    }

    private void toDingDing(String webHookToken, String contentMsg) throws Exception{

      StringBuilder sb = new StringBuilder();
        String textCache = sb.append("【" + appName + "】")
                             .append("【" + getInetAddress().getHostAddress() + "】")
                             .append("【" + env + "】").append("\n")
                             .append(contentMsg).toString();

      String textMsg="{\"msgtype\": \"text\",\"text\": {\"content\": " + JSON.toJSONString(textCache) + "}}";
      try(CloseableHttpClient httpclient = HttpClients.createDefault()) {
        HttpPost httppost = new HttpPost(webHookToken);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);

        httpclient.execute(httppost);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    private InetAddress getInetAddress(){
      try {
        InetAddress inetAddress = InetAddress.getLocalHost();
        return inetAddress;
      } catch (
          UnknownHostException e) {
        e.printStackTrace();
      }
      return null;
    }


    public String getProfilesActive() {
      return profilesActive;
    }

    public void setProfilesActive(String profilesActive) {
      this.profilesActive = profilesActive;
    }

    public void setPattern(String pattern) {
      this.pattern = pattern;
    }

    public String getPattern() {
      return pattern;
    }

    public PatternLayout getLayout() {
      return layout;
    }

    public void setLayout(PatternLayout layout) {
      this.layout = layout;
    }

    public String getDingdingToken() {
      return dingdingToken;
    }

    public void setDingdingToken(String dingdingToken) {
      this.dingdingToken = dingdingToken;
    }

    public String getAppName() {
      return appName;
    }

    public void setAppName(String appName) {
      this.appName = appName;
    }

  public String getEnv() {
    return env;
  }

  public void setEnv(String env) {
    this.env = env;
  }

//  public static void main(String[] args) {
//      DingTalkAppender logDing = new DingTalkAppender();
//    try {
//      logDing.toDingDing("https://oapi.dingtalk.com/robot/send?access_token=8c184310c00e13a0b24b542e66e0b8580954dbf12523dc0d9d5a92e964c53e91", "测试验证钉钉推送消息");
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
}
