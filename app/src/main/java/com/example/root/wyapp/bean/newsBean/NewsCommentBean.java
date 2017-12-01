package com.example.root.wyapp.bean.newsBean;

import java.util.List;

/**
 * Created by root on 2017/7/26.
 */

public class NewsCommentBean {

    /**
     * against : 0
     * anonymous : false
     * buildLevel : 1
     * commentId : 2647459792
     * content : 站台窄车厢短小，转线设置不合理，导致太多人挤在小小的空间里面，哎
     * createTime : 2016-12-13 08:34:25
     * deviceInfo : {"deviceName":"iPhone 6 Plus"}
     * favCount : 0
     * ip : 119.32.*.*
     * isDel : false
     * postId : C85DDFSQ00964J4O_2647459792
     * productKey : a2869674571f77b5a0867c3d71db5856
     * shareCount : 0
     * siteName : 网易
     * source : ph
     * unionState : false
     * user : {"avatar":"http://cms-bucket.nosdn.127.net/285fa472b81e4cf19c6225ae4baab1ed20161217004715.jpg","id":"bTE1NTIxMTgxNzA1QDE2My5jb20=","location":"广东省广州市","nickname":"donnytwo","redNameInfo":[],"userId":92845732,"vipInfo":"viphy"}
     * vote : 102
     */

    private int against;
    private boolean anonymous;
    private int buildLevel;
    private long commentId;
    private String content;
    private String createTime;
    private DeviceInfoBean deviceInfo;
    private int favCount;
    private String ip;
    private boolean isDel;
    private String postId;
    private String productKey;
    private int shareCount;
    private String siteName;
    private String source;
    private boolean unionState;
    private UserBean user;
    private int vote;

    @Override
    public String toString() {
        return "NewsCommentBean{" +
                "against=" + against +
                ", anonymous=" + anonymous +
                ", buildLevel=" + buildLevel +
                ", commentId=" + commentId +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", deviceInfo=" + deviceInfo +
                ", favCount=" + favCount +
                ", ip='" + ip + '\'' +
                ", isDel=" + isDel +
                ", postId='" + postId + '\'' +
                ", productKey='" + productKey + '\'' +
                ", shareCount=" + shareCount +
                ", siteName='" + siteName + '\'' +
                ", source='" + source + '\'' +
                ", unionState=" + unionState +
                ", user=" + user +
                ", vote=" + vote +
                '}';
    }

    public int getAgainst() {
        return against;
    }

    public void setAgainst(int against) {
        this.against = against;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public int getBuildLevel() {
        return buildLevel;
    }

    public void setBuildLevel(int buildLevel) {
        this.buildLevel = buildLevel;
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public DeviceInfoBean getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoBean deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public int getFavCount() {
        return favCount;
    }

    public void setFavCount(int favCount) {
        this.favCount = favCount;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isIsDel() {
        return isDel;
    }

    public void setIsDel(boolean isDel) {
        this.isDel = isDel;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isUnionState() {
        return unionState;
    }

    public void setUnionState(boolean unionState) {
        this.unionState = unionState;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public static class DeviceInfoBean {
        /**
         * deviceName : iPhone 6 Plus
         */

        private String deviceName;

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }
    }

    public static class UserBean {
        /**
         * avatar : http://cms-bucket.nosdn.127.net/285fa472b81e4cf19c6225ae4baab1ed20161217004715.jpg
         * id : bTE1NTIxMTgxNzA1QDE2My5jb20=
         * location : 广东省广州市
         * nickname : donnytwo
         * redNameInfo : []
         * userId : 92845732
         * vipInfo : viphy
         */

        private String avatar;
        private String id;
        private String location;
        private String nickname;
        private int userId;
        private String vipInfo;
        private List<?> redNameInfo;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getVipInfo() {
            return vipInfo;
        }

        public void setVipInfo(String vipInfo) {
            this.vipInfo = vipInfo;
        }

        public List<?> getRedNameInfo() {
            return redNameInfo;
        }

        public void setRedNameInfo(List<?> redNameInfo) {
            this.redNameInfo = redNameInfo;
        }
    }
}
