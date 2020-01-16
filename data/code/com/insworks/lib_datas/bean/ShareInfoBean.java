package com.insworks.lib_datas.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.insworks.lib_datas.bean.common.ResponseBean;

/**
 * @ProjectName: tftpay
 * @Package: com.insworks.lib_datas.bean
 * @ClassName: ShareInfoBean
 * @Author: Song Jian
 * @CreateDate: 2019/7/10 19:05
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/7/10 19:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class ShareInfoBean extends ResponseBean implements Parcelable {


    /**
     * share : {"title":"我是 秦学迁 推荐你免费办信用卡，秒批，额度高","message":"您的好友推荐你来办信用卡，包邮到家","icon":"https://yunpay-images.oss-cn-shenzhen.aliyuncs.com/banner/share.jpg","url":"https://interacts.hq.vidata.com.cn/h5/card-platform/index.html?source=18106"}
     */

    private String nickname;
    private String user_head;
    private ShareBean share;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUser_head() {
        if (TextUtils.isEmpty(user_head)) {
            return "";
        }
        return "https://test.qtopays.com"+user_head;
    }

    public void setUser_head(String user_head) {
        this.user_head = user_head;
    }

    public ShareBean getShare() {
        return share;
    }

    public void setShare(ShareBean share) {
        this.share = share;
    }

    public static class ShareBean implements Parcelable {
        /**
         * title : 我是 秦学迁 推荐你免费办信用卡，秒批，额度高
         * message : 您的好友推荐你来办信用卡，包邮到家
         * icon : https://yunpay-images.oss-cn-shenzhen.aliyuncs.com/banner/share.jpg
         * url : https://interacts.hq.vidata.com.cn/h5/card-platform/index.html?source=18106
         */

        private String title;
        private String message;
        private String icon;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.message);
            dest.writeString(this.icon);
            dest.writeString(this.url);
        }

        public ShareBean() {
        }

        protected ShareBean(Parcel in) {
            this.title = in.readString();
            this.message = in.readString();
            this.icon = in.readString();
            this.url = in.readString();
        }

        public static final Creator<ShareBean> CREATOR = new Creator<ShareBean>() {
            @Override
            public ShareBean createFromParcel(Parcel source) {
                return new ShareBean(source);
            }

            @Override
            public ShareBean[] newArray(int size) {
                return new ShareBean[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nickname);
        dest.writeString(this.user_head);
        dest.writeParcelable(this.share, flags);
    }

    public ShareInfoBean() {
    }

    protected ShareInfoBean(Parcel in) {
        this.nickname = in.readString();
        this.user_head = in.readString();
        this.share = in.readParcelable(ShareBean.class.getClassLoader());
    }

    public static final Creator<ShareInfoBean> CREATOR = new Creator<ShareInfoBean>() {
        @Override
        public ShareInfoBean createFromParcel(Parcel source) {
            return new ShareInfoBean(source);
        }

        @Override
        public ShareInfoBean[] newArray(int size) {
            return new ShareInfoBean[size];
        }
    };
}
