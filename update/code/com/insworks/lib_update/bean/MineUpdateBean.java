package com.insworks.lib_update.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.insworks.lib_datas.bean.common.ResponseBean;

/**
 * @ProjectName: AndroidTemplateProject2
 * @Package: com.insworks.lib_update.bean
 * @ClassName: MineUpdateBean
 * @Author: Song Jian
 * @CreateDate: 2019/8/8 17:13
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/8/8 17:13
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class MineUpdateBean extends ResponseBean implements Parcelable {

    /**
     * isForce : false
     * updateIgnoreVersion : false
     * newVersion : 101
     * title : 重大更新
     * updateInfo : test updateApk
     * type : download
     * jumpThird : {"payURL":"https://raw.githubusercontent.com/itlwy/AppSmartUpdate/master/resources/app/smart-update.apk","picUrl":"https://raw.githubusercontent.com/itlwy/AppSmartUpdate/master/resources/app/smart-update.apk"}
     * download : {"apkURL":"https://raw.githubusercontent.com/itlwy/AppSmartUpdate/master/resources/app/smart-update.apk"}
     */

    private boolean isForce;//是否强制更新
    private boolean updateIgnoreVersion;//不管version多少直接更新 优先级最高
    private int newVersion;
    private String title;
    private String updateInfo;
    private String type;
    private JumpThirdBean jumpThird;
    private DownloadBean download;

    public boolean isIsForce() {
        return isForce;
    }

    public void setIsForce(boolean isForce) {
        this.isForce = isForce;
    }

    public boolean isUpdateIgnoreVersion() {
        return updateIgnoreVersion;
    }

    public void setUpdateIgnoreVersion(boolean updateIgnoreVersion) {
        this.updateIgnoreVersion = updateIgnoreVersion;
    }

    public int getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(int newVersion) {
        this.newVersion = newVersion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JumpThirdBean getJumpThird() {
        return jumpThird;
    }

    public void setJumpThird(JumpThirdBean jumpThird) {
        this.jumpThird = jumpThird;
    }

    public DownloadBean getDownload() {
        return download;
    }

    public void setDownload(DownloadBean download) {
        this.download = download;
    }

    public static class JumpThirdBean implements Parcelable {
        /**
         * payURL : https://raw.githubusercontent.com/itlwy/AppSmartUpdate/master/resources/app/smart-update.apk
         * picUrl : https://raw.githubusercontent.com/itlwy/AppSmartUpdate/master/resources/app/smart-update.apk
         */

        private String payURL;
        private String picUrl;

        public String getPayURL() {
            return payURL;
        }

        public void setPayURL(String payURL) {
            this.payURL = payURL;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.payURL);
            dest.writeString(this.picUrl);
        }

        public JumpThirdBean() {
        }

        protected JumpThirdBean(Parcel in) {
            this.payURL = in.readString();
            this.picUrl = in.readString();
        }

        public static final Creator<JumpThirdBean> CREATOR = new Creator<JumpThirdBean>() {
            @Override
            public JumpThirdBean createFromParcel(Parcel source) {
                return new JumpThirdBean(source);
            }

            @Override
            public JumpThirdBean[] newArray(int size) {
                return new JumpThirdBean[size];
            }
        };
    }

    public static class DownloadBean implements Parcelable {
        /**
         * apkURL : https://raw.githubusercontent.com/itlwy/AppSmartUpdate/master/resources/app/smart-update.apk
         */

        private String apkURL;

        public String getApkURL() {
            return apkURL;
        }

        public void setApkURL(String apkURL) {
            this.apkURL = apkURL;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.apkURL);
        }

        public DownloadBean() {
        }

        protected DownloadBean(Parcel in) {
            this.apkURL = in.readString();
        }

        public static final Creator<DownloadBean> CREATOR = new Creator<DownloadBean>() {
            @Override
            public DownloadBean createFromParcel(Parcel source) {
                return new DownloadBean(source);
            }

            @Override
            public DownloadBean[] newArray(int size) {
                return new DownloadBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isForce ? (byte) 1 : (byte) 0);
        dest.writeByte(this.updateIgnoreVersion ? (byte) 1 : (byte) 0);
        dest.writeInt(this.newVersion);
        dest.writeString(this.title);
        dest.writeString(this.updateInfo);
        dest.writeString(this.type);
        dest.writeParcelable(this.jumpThird, flags);
        dest.writeParcelable(this.download, flags);
    }

    public MineUpdateBean() {
    }

    protected MineUpdateBean(Parcel in) {
        this.isForce = in.readByte() != 0;
        this.updateIgnoreVersion = in.readByte() != 0;
        this.newVersion = in.readInt();
        this.title = in.readString();
        this.updateInfo = in.readString();
        this.type = in.readString();
        this.jumpThird = in.readParcelable(JumpThirdBean.class.getClassLoader());
        this.download = in.readParcelable(DownloadBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MineUpdateBean> CREATOR = new Parcelable.Creator<MineUpdateBean>() {
        @Override
        public MineUpdateBean createFromParcel(Parcel source) {
            return new MineUpdateBean(source);
        }

        @Override
        public MineUpdateBean[] newArray(int size) {
            return new MineUpdateBean[size];
        }
    };
}
