package com.hzu.jpg.commonwork.enity.moudle;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Administrator on 2017/3/25.
 */
//@JsonIgnoreProperties
public class User {

    private String id;

    private String telephone;

    private String username;

    private String password;

    private String realname;

    private String sex;

    private String birthday;

    private String icno;

    private String school;

    private String major;

    private String province;

    private String city;

    private String region;

    private String requires;

    private String eager;

    private String job_type;

    private String entry_time;

    private String balance;

    private String bank_card;

    private String point;

    private String sign;

    private int is_supplement;

    private String IDcard;

    private String agent_apply;

    private String agent_id;

    private int ownAgentId;

    private int ownAgentStatus;


    //企业的
    private String name;

    private String label;

    private String describes;

    private String license;

    private String tax;

    private String link_man;

    private String link_phone;

    private int isCheck;

    private String failurepassinfo;

    private String email;

    private int role;

    private String details;

    public User() {
    }

    public User(String id, String telephone, String username, String password, String realname,
                String sex, String birthday, String icno, String school, String major, String province,
                String city, String region, String requires, String eager, String job_type, String entry_time,
                String balance, String bank_card, String point, String sign, int is_supplement, String IDcard,
                String agent_apply, String agent_id, int ownAgentId, int ownAgentStatus, String name,
                String label, String license, String describes, String tax, String link_man, String link_phone,
                int isCheck, String failurepassinfo, String email, int role, String details) {
        this.id = id;
        this.telephone = telephone;
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.sex = sex;
        this.birthday = birthday;
        this.icno = icno;
        this.school = school;
        this.major = major;
        this.province = province;
        this.city = city;
        this.region = region;
        this.requires = requires;
        this.eager = eager;
        this.job_type = job_type;
        this.entry_time = entry_time;
        this.balance = balance;
        this.bank_card = bank_card;
        this.point = point;
        this.sign = sign;
        this.is_supplement = is_supplement;
        this.IDcard = IDcard;
        this.agent_apply = agent_apply;
        this.agent_id = agent_id;
        this.ownAgentId = ownAgentId;
        this.ownAgentStatus = ownAgentStatus;
        this.name = name;
        this.label = label;
        this.license = license;
        this.describes = describes;
        this.tax = tax;
        this.link_man = link_man;
        this.link_phone = link_phone;
        this.isCheck = isCheck;
        this.failurepassinfo = failurepassinfo;
        this.email = email;
        this.role = role;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIcno() {
        return icno;
    }

    public void setIcno(String icno) {
        this.icno = icno;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRequires() {
        return requires;
    }

    public void setRequires(String requires) {
        this.requires = requires;
    }

    public String getEager() {
        return eager;
    }

    public void setEager(String eager) {
        this.eager = eager;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(String entry_time) {
        this.entry_time = entry_time;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getIs_supplement() {
        return is_supplement;
    }

    public void setIs_supplement(int is_supplement) {
        this.is_supplement = is_supplement;
    }

    public String getIDcard() {
        return IDcard;
    }

    public void setIDcard(String IDcard) {
        this.IDcard = IDcard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getLink_man() {
        return link_man;
    }

    public void setLink_man(String link_man) {
        this.link_man = link_man;
    }

    public String getLink_phone() {
        return link_phone;
    }

    public void setLink_phone(String link_phone) {
        this.link_phone = link_phone;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }

    public String getFailurepassinfo() {
        return failurepassinfo;
    }

    public void setFailurepassinfo(String failurepassinfo) {
        this.failurepassinfo = failurepassinfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAgent_apply() {
        return agent_apply;
    }

    public void setAgent_apply(String agent_apply) {
        this.agent_apply = agent_apply;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public int getOwnAgentId() {
        return ownAgentId;
    }

    public void setOwnAgentId(int ownAgentId) {
        this.ownAgentId = ownAgentId;
    }

    public int getOwnAgentStatus() {
        return ownAgentStatus;
    }

    public void setOwnAgentStatus(int ownAgentStatus) {
        this.ownAgentStatus = ownAgentStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", telephone='" + telephone + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realname='" + realname + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", icno='" + icno + '\'' +
                ", school='" + school + '\'' +
                ", major='" + major + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", requires='" + requires + '\'' +
                ", eager='" + eager + '\'' +
                ", job_type='" + job_type + '\'' +
                ", enty_time='" + entry_time + '\'' +
                ", balance='" + balance + '\'' +
                ", bank_card='" + bank_card + '\'' +
                ", point='" + point + '\'' +
                ", sign='" + sign + '\'' +
                ", is_supplement=" + is_supplement +
                ", idcard='" + IDcard + '\'' +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", describes='" + describes + '\'' +
                ", license='" + license + '\'' +
                ", tax='" + tax + '\'' +
                ", link_man='" + link_man + '\'' +
                ", link_phone='" + link_phone + '\'' +
                ", isCheck=" + isCheck +
                ", failurepassinfo='" + failurepassinfo + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
