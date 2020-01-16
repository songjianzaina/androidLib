package com.insworks.lib_datas.bean.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @ProjectName: cloudpay_new
 * @Package: com.insworks.lib_datas.news.bean
 * @ClassName: CopyBean
 * @Author: Song Jian
 * @CreateDate: 2019/5/9 14:23
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/5/9 14:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: 广告实体类
 *
 */
public class SplashAdBean extends ResponseBean implements Parcelable {

    /**
     * status : true
     * list : [{"cid":"4","cat_name":"APP启动广告","cat_idname":"adv","cat_remark":"","cat_status":"1","slide_id":"32","slide_cid":"4","slide_name":"APP启动广告图通用尺寸640x960","slide_pic":"admin/20181215/5c13eaee9617b.png","slide_url":"","slide_des":"640x960","slide_content":"","slide_status":"1","listorder":"0"},{"cid":"4","cat_name":"APP启动广告","cat_idname":"adv","cat_remark":"","cat_status":"1","slide_id":"33","slide_cid":"4","slide_name":"APP启动广告图特别尺寸1125x2436","slide_pic":"admin/20181215/5c13eb2fab2cd.png","slide_url":"","slide_des":"1125x2436","slide_content":"","slide_status":"1","listorder":"0"},{"cid":"4","cat_name":"APP启动广告","cat_idname":"adv","cat_remark":"","cat_status":"1","slide_id":"34","slide_cid":"4","slide_name":"APP启动广告图通用尺寸750x1334","slide_pic":"admin/20181217/5c174c846e73f.png","slide_url":"","slide_des":"750x1334","slide_content":"","slide_status":"1","listorder":"0"},{"cid":"4","cat_name":"APP启动广告","cat_idname":"adv","cat_remark":"","cat_status":"1","slide_id":"35","slide_cid":"4","slide_name":"APP启动广告图特别尺寸1242x2208","slide_pic":"admin/20181217/5c174e6f591e8.png","slide_url":"","slide_des":"1242x2208","slide_content":"","slide_status":"1","listorder":"0"}]
     */

    private boolean status;
    private List<ListBean> list;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }



    public static class ListBean extends ResponseBean implements Parcelable{
        /**
         * cid : 4
         * cat_name : APP启动广告
         * cat_idname : adv
         * cat_remark :
         * cat_status : 1
         * slide_id : 32
         * slide_cid : 4
         * slide_name : APP启动广告图通用尺寸640x960
         * slide_pic : admin/20181215/5c13eaee9617b.png
         * slide_url :
         * slide_des : 640x960
         * slide_content :
         * slide_status : 1
         * listorder : 0
         */

        private String cid;
        private String cat_name;
        private String cat_idname;
        private String cat_remark;
        private String cat_status;
        private String slide_id;
        private String slide_cid;
        private String slide_name;
        private String slide_pic;
        private String slide_url;
        private String slide_des;
        private String slide_content;
        private String slide_status;
        private String listorder;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public String getCat_idname() {
            return cat_idname;
        }

        public void setCat_idname(String cat_idname) {
            this.cat_idname = cat_idname;
        }

        public String getCat_remark() {
            return cat_remark;
        }

        public void setCat_remark(String cat_remark) {
            this.cat_remark = cat_remark;
        }

        public String getCat_status() {
            return cat_status;
        }

        public void setCat_status(String cat_status) {
            this.cat_status = cat_status;
        }

        public String getSlide_id() {
            return slide_id;
        }

        public void setSlide_id(String slide_id) {
            this.slide_id = slide_id;
        }

        public String getSlide_cid() {
            return slide_cid;
        }

        public void setSlide_cid(String slide_cid) {
            this.slide_cid = slide_cid;
        }

        public String getSlide_name() {
            return slide_name;
        }

        public void setSlide_name(String slide_name) {
            this.slide_name = slide_name;
        }

        public String getSlide_pic() {
            return slide_pic;
        }

        public void setSlide_pic(String slide_pic) {
            this.slide_pic = slide_pic;
        }

        public String getSlide_url() {
            return slide_url;
        }

        public void setSlide_url(String slide_url) {
            this.slide_url = slide_url;
        }

        public String getSlide_des() {
            return slide_des;
        }

        public void setSlide_des(String slide_des) {
            this.slide_des = slide_des;
        }

        public String getSlide_content() {
            return slide_content;
        }

        public void setSlide_content(String slide_content) {
            this.slide_content = slide_content;
        }

        public String getSlide_status() {
            return slide_status;
        }

        public void setSlide_status(String slide_status) {
            this.slide_status = slide_status;
        }

        public String getListorder() {
            return listorder;
        }

        public void setListorder(String listorder) {
            this.listorder = listorder;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.cid);
            dest.writeString(this.cat_name);
            dest.writeString(this.cat_idname);
            dest.writeString(this.cat_remark);
            dest.writeString(this.cat_status);
            dest.writeString(this.slide_id);
            dest.writeString(this.slide_cid);
            dest.writeString(this.slide_name);
            dest.writeString(this.slide_pic);
            dest.writeString(this.slide_url);
            dest.writeString(this.slide_des);
            dest.writeString(this.slide_content);
            dest.writeString(this.slide_status);
            dest.writeString(this.listorder);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.cid = in.readString();
            this.cat_name = in.readString();
            this.cat_idname = in.readString();
            this.cat_remark = in.readString();
            this.cat_status = in.readString();
            this.slide_id = in.readString();
            this.slide_cid = in.readString();
            this.slide_name = in.readString();
            this.slide_pic = in.readString();
            this.slide_url = in.readString();
            this.slide_des = in.readString();
            this.slide_content = in.readString();
            this.slide_status = in.readString();
            this.listorder = in.readString();
        }

        public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.list);
    }

    public SplashAdBean() {
    }

    protected SplashAdBean(Parcel in) {
        this.status = in.readByte() != 0;
        this.list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Creator<SplashAdBean> CREATOR = new Creator<SplashAdBean>() {
        @Override
        public SplashAdBean createFromParcel(Parcel source) {
            return new SplashAdBean(source);
        }

        @Override
        public SplashAdBean[] newArray(int size) {
            return new SplashAdBean[size];
        }
    };
}
