package com.insworks.lib_datas.bean.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ProjectName: AndroidTemplateProject2
 * @Package: com.insworks.lib_control.bean
 * @ClassName: ControlJsonBean
 * @Author: Song Jian
 * @CreateDate: 2019/8/12 10:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/8/12 10:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class ControlJsonBean extends ResponseBean implements Parcelable {

    /**
     * isIntercept : true
     * isOpenAd : true
     * isOpenLog : false
     * urlType : DwonloadUrlList
     * address : {"street":"科技园路.","city":"江苏苏州","country":"中国"}
     * DwonloadUrlList : [{"name":"updateApk","key":0,"url":"https://raw.githubusercontent.com/songjianzaina/app_server_data/master/update/update.apk"},{"name":"Baidu","key":1,"url":"http://www.baidu.com"},{"name":"SoSo","key":2,"url":"http://www.SoSo.com"}]
     * jumpUrlList : [{"name":"cardInvite","key":0,"url":"https://interacts.hq.vidata.com.cn/h5/card-platform/index.html?source=18106&userid=M1909009593457"},{"name":"machineInvite","key":1,"url":"http://api.yoelian.cn/app.php/login/appreg/m/M1909009593457"},{"name":"machineInvite2","key":2,"url":"http://api.yoelian.cn/app.php/login/appreg/m/M132927/token/yyl3ec02282b4e934c89ea507dc80482bab"}]
     */

    private boolean isIntercept;
    private boolean isOpenAd;
    private boolean isOpenLog;
    private String urlType;
    private AddressBean address;
    private List<DwonloadUrlListBean> DwonloadUrlList;
    private List<JumpUrlListBean> jumpUrlList;
    /**
     * 获取广告apk下载地址
     *
     * @return
     */
    public String getMineAdApkUrl() {
        if (DwonloadUrlList==null||DwonloadUrlList.size()<1) {
            return "https://raw.githubusercontent.com/songjianzaina/app_server_data/master/update/update.apk";
        }  else {
            //随机返回
            return DwonloadUrlList.get(getRandomNumber(0,DwonloadUrlList.size())).url;
        }
    }
    /**
     * 获取随机数据
     * @param min
     * @param max
     * @return
     */
    private   int getRandomNumber(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 获取广告跳转连接
     * @return
     */
    public String getMineAdJumpUrl() {
        if (jumpUrlList==null||jumpUrlList.size()<1) {
            return "https://baidu.com";
        }  else {
            //随机返回
            return jumpUrlList.get(getRandomNumber(0,jumpUrlList.size())).url;
        }
    }
    public boolean isIsIntercept() {
        return isIntercept;
    }

    public void setIsIntercept(boolean isIntercept) {
        this.isIntercept = isIntercept;
    }

    public boolean isIsOpenAd() {
        return isOpenAd;
    }

    public void setIsOpenAd(boolean isOpenAd) {
        this.isOpenAd = isOpenAd;
    }

    public boolean isIsOpenLog() {
        return isOpenLog;
    }

    public void setIsOpenLog(boolean isOpenLog) {
        this.isOpenLog = isOpenLog;
    }

    public String getUrlType() {
        if (urlType==null) {
            urlType = "DwonloadUrlList";
        }
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public List<DwonloadUrlListBean> getDwonloadUrlList() {
        return DwonloadUrlList;
    }

    public void setDwonloadUrlList(List<DwonloadUrlListBean> DwonloadUrlList) {
        this.DwonloadUrlList = DwonloadUrlList;
    }

    public List<JumpUrlListBean> getJumpUrlList() {
        return jumpUrlList;
    }

    public void setJumpUrlList(List<JumpUrlListBean> jumpUrlList) {
        this.jumpUrlList = jumpUrlList;
    }

    public static class AddressBean implements Parcelable {
        /**
         * street : 科技园路.
         * city : 江苏苏州
         * country : 中国
         */

        private String street;
        private String city;
        private String country;

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.street);
            dest.writeString(this.city);
            dest.writeString(this.country);
        }

        public AddressBean() {
        }

        protected AddressBean(Parcel in) {
            this.street = in.readString();
            this.city = in.readString();
            this.country = in.readString();
        }

        public static final Creator<AddressBean> CREATOR = new Creator<AddressBean>() {
            @Override
            public AddressBean createFromParcel(Parcel source) {
                return new AddressBean(source);
            }

            @Override
            public AddressBean[] newArray(int size) {
                return new AddressBean[size];
            }
        };
    }

    public static class DwonloadUrlListBean implements Parcelable {
        /**
         * name : updateApk
         * key : 0
         * url : https://raw.githubusercontent.com/songjianzaina/app_server_data/master/update/update.apk
         */

        private String name;
        private int key;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
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
            dest.writeString(this.name);
            dest.writeInt(this.key);
            dest.writeString(this.url);
        }

        public DwonloadUrlListBean() {
        }

        protected DwonloadUrlListBean(Parcel in) {
            this.name = in.readString();
            this.key = in.readInt();
            this.url = in.readString();
        }

        public static final Creator<DwonloadUrlListBean> CREATOR = new Creator<DwonloadUrlListBean>() {
            @Override
            public DwonloadUrlListBean createFromParcel(Parcel source) {
                return new DwonloadUrlListBean(source);
            }

            @Override
            public DwonloadUrlListBean[] newArray(int size) {
                return new DwonloadUrlListBean[size];
            }
        };
    }

    public static class JumpUrlListBean implements Parcelable {
        /**
         * name : cardInvite
         * key : 0
         * url : https://interacts.hq.vidata.com.cn/h5/card-platform/index.html?source=18106&userid=M1909009593457
         */

        private String name;
        private int key;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
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
            dest.writeString(this.name);
            dest.writeInt(this.key);
            dest.writeString(this.url);
        }

        public JumpUrlListBean() {
        }

        protected JumpUrlListBean(Parcel in) {
            this.name = in.readString();
            this.key = in.readInt();
            this.url = in.readString();
        }

        public static final Creator<JumpUrlListBean> CREATOR = new Creator<JumpUrlListBean>() {
            @Override
            public JumpUrlListBean createFromParcel(Parcel source) {
                return new JumpUrlListBean(source);
            }

            @Override
            public JumpUrlListBean[] newArray(int size) {
                return new JumpUrlListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isIntercept ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isOpenAd ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isOpenLog ? (byte) 1 : (byte) 0);
        dest.writeString(this.urlType);
        dest.writeParcelable(this.address, flags);
        dest.writeList(this.DwonloadUrlList);
        dest.writeList(this.jumpUrlList);
    }

    public ControlJsonBean() {
    }

    protected ControlJsonBean(Parcel in) {
        this.isIntercept = in.readByte() != 0;
        this.isOpenAd = in.readByte() != 0;
        this.isOpenLog = in.readByte() != 0;
        this.urlType = in.readString();
        this.address = in.readParcelable(AddressBean.class.getClassLoader());
        this.DwonloadUrlList = new ArrayList<DwonloadUrlListBean>();
        in.readList(this.DwonloadUrlList, DwonloadUrlListBean.class.getClassLoader());
        this.jumpUrlList = new ArrayList<JumpUrlListBean>();
        in.readList(this.jumpUrlList, JumpUrlListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ControlJsonBean> CREATOR = new Parcelable.Creator<ControlJsonBean>() {
        @Override
        public ControlJsonBean createFromParcel(Parcel source) {
            return new ControlJsonBean(source);
        }

        @Override
        public ControlJsonBean[] newArray(int size) {
            return new ControlJsonBean[size];
        }
    };
}
