package com.kuyu.kuxianghui.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/12.
 */
public class UserBean implements Serializable{
         private String clientId;
         private String createTime;
         private String headpic;
         private String id;
         private String nickname;
         private String phone;
         private String  platform;
         private String sex;
         private String source;
         private String status;
         private String type;
         private String updateTime;
         private String username;
        public UserBean(){}
        public UserBean(String type, String clientId, String createTime, String headpic, String id, String nickname, String phone, String platform, String sex, String source, String status, String updateTime, String username) {
            this.type = type;
            this.clientId = clientId;
            this.createTime = createTime;
            this.headpic = headpic;
            this.id = id;
            this.nickname = nickname;
            this.phone = phone;
            this.platform = platform;
            this.sex = sex;
            String s = this.source = source;
            this.status = status;
            this.updateTime = updateTime;
            this.username = username;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            return "UserBean{" +
                    "clientId='" + clientId + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", headpic='" + headpic + '\'' +
                    ", id='" + id + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", phone='" + phone + '\'' +
                    ", platform='" + platform + '\'' +
                    ", sex='" + sex + '\'' +
                    ", source='" + source + '\'' +
                    ", status='" + status + '\'' +
                    ", type='" + type + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", username='" + username + '\'' +
                    '}';
        }


}
