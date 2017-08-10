package com.hzu.jpg.commonwork.enity.service;

/**
 * Created by cimcitech on 2017/5/31.
 */


import java.io.Serializable;
import java.util.ArrayList;

/**
 * 帖子
 */
public class PostsVo implements Serializable {
    private ArrayList<Posts> comment;

    public ArrayList<Posts> getComment() {
        return comment;
    }

    public void setComment(ArrayList<Posts> comment) {
        this.comment = comment;
    }

    public class Posts implements Serializable {
        private String addtime;
        private int check_status;
        private int classfy;
        private String content;
        private String failureInfo;
        private int id;
        private int is_del;
        private String nickname;
        private String pictures;
        private int pid;
        private int user_id;
        private Userinfo userinfo;

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getCheck_status() {
            return check_status;
        }

        public void setCheck_status(int check_status) {
            this.check_status = check_status;
        }

        public int getClassfy() {
            return classfy;
        }

        public void setClassfy(int classfy) {
            this.classfy = classfy;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFailureInfo() {
            return failureInfo;
        }

        public void setFailureInfo(String failureInfo) {
            this.failureInfo = failureInfo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_del() {
            return is_del;
        }

        public void setIs_del(int is_del) {
            this.is_del = is_del;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPictures() {
            return pictures;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public Userinfo getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(Userinfo userinfo) {
            this.userinfo = userinfo;
        }

        public class Userinfo implements Serializable {
            private String IDcard;
            private int agent_apply;
            private int agent_id;
            private int balance;
            private String bank_card;
            private String birthday;
            private String city;
            private int eager;
            private String entry_time;
            private String icno;
            private int id;
            private int is_supplement;
            private int job_type;
            private String link_phone;
            private String logindate;
            private String major;
            private String password;
            private int point;
            private String province;
            private String realname;
            private String region;
            private String registerdate;
            private String requires;
            private String school;
            private String sex;
            private int sign;
            private String telephone;
            private String username;

            public String getIDcard() {
                return IDcard;
            }

            public void setIDcard(String IDcard) {
                this.IDcard = IDcard;
            }

            public int getAgent_apply() {
                return agent_apply;
            }

            public void setAgent_apply(int agent_apply) {
                this.agent_apply = agent_apply;
            }

            public int getAgent_id() {
                return agent_id;
            }

            public void setAgent_id(int agent_id) {
                this.agent_id = agent_id;
            }

            public int getBalance() {
                return balance;
            }

            public void setBalance(int balance) {
                this.balance = balance;
            }

            public String getBank_card() {
                return bank_card;
            }

            public void setBank_card(String bank_card) {
                this.bank_card = bank_card;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public int getEager() {
                return eager;
            }

            public void setEager(int eager) {
                this.eager = eager;
            }

            public String getEntry_time() {
                return entry_time;
            }

            public void setEntry_time(String entry_time) {
                this.entry_time = entry_time;
            }

            public String getIcno() {
                return icno;
            }

            public void setIcno(String icno) {
                this.icno = icno;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIs_supplement() {
                return is_supplement;
            }

            public void setIs_supplement(int is_supplement) {
                this.is_supplement = is_supplement;
            }

            public int getJob_type() {
                return job_type;
            }

            public void setJob_type(int job_type) {
                this.job_type = job_type;
            }

            public String getLink_phone() {
                return link_phone;
            }

            public void setLink_phone(String link_phone) {
                this.link_phone = link_phone;
            }

            public String getLogindate() {
                return logindate;
            }

            public void setLogindate(String logindate) {
                this.logindate = logindate;
            }

            public String getMajor() {
                return major;
            }

            public void setMajor(String major) {
                this.major = major;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public String getRegisterdate() {
                return registerdate;
            }

            public void setRegisterdate(String registerdate) {
                this.registerdate = registerdate;
            }

            public String getRequires() {
                return requires;
            }

            public void setRequires(String requires) {
                this.requires = requires;
            }

            public String getSchool() {
                return school;
            }

            public void setSchool(String school) {
                this.school = school;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public int getSign() {
                return sign;
            }

            public void setSign(int sign) {
                this.sign = sign;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
