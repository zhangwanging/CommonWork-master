package com.hzu.jpg.commonwork.utils;

import com.hzu.jpg.commonwork.app.Config;
import com.hzu.jpg.commonwork.enity.FilterEntity;
import com.hzu.jpg.commonwork.enity.FilterTwoEntity;
import com.hzu.jpg.commonwork.enity.moudle.AllCityRegionModel;
import com.hzu.jpg.commonwork.enity.moudle.RegionBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */

public class DataUtil {

    public static List<FilterEntity> regions = new ArrayList<>();
    ;

    public static List<FilterTwoEntity> getJobs() {
        List<FilterTwoEntity> locateDate = new ArrayList<>();
        List<FilterEntity> entities = new ArrayList<>();
        entities.add(new FilterEntity("销售", "1"));
        entities.add(new FilterEntity("司机", "2"));
        entities.add(new FilterEntity("行政/后勤", "3"));
        entities.add(new FilterEntity("人力资源", "4"));
        entities.add(new FilterEntity("客服", "5"));
        entities.add(new FilterEntity("财务/审计/税务", "6"));
        entities.add(new FilterEntity("房地产中介/经纪/开发", "7"));
        entities.add(new FilterEntity("淘宝职位", "8"));
        entities.add(new FilterEntity("保险", "9"));
        entities.add(new FilterEntity("翻译", "10"));
        entities.add(new FilterEntity("物业管理", "11"));
        entities.add(new FilterEntity("金融/投资/证券", "12"));
        entities.add(new FilterEntity("市场/公关/媒介", "13"));
        entities.add(new FilterEntity("编辑/出版/印刷", "14"));
        entities.add(new FilterEntity("广告/会展", "15"));
        entities.add(new FilterEntity("法律", "16"));
        entities.add(new FilterEntity("高级管理", "17"));
        entities.add(new FilterEntity("信托/担保/拍卖/典当", "18"));
        entities.add(new FilterEntity("超市/百货/零售", "19"));
        entities.add(new FilterEntity("餐饮/酒店", "20"));
        entities.add(new FilterEntity("家政/安保", "21"));
        entities.add(new FilterEntity("美容/美发", "22"));
        entities.add(new FilterEntity("医院/医疗/护理", "23"));
        entities.add(new FilterEntity("按摩保健", "24"));
        entities.add(new FilterEntity("旅游", "25"));
        entities.add(new FilterEntity("运动健身", "26"));
        entities.add(new FilterEntity("技工/工人", "27"));
        entities.add(new FilterEntity("贸易/运输/物流", "28"));
        entities.add(new FilterEntity("汽车制造与服务", "29"));
        entities.add(new FilterEntity("接卸色剂/制造/维修", "30"));
        entities.add(new FilterEntity("生产/制造", "31"));
        entities.add(new FilterEntity("通信/网络/计算机", "32"));
        entities.add(new FilterEntity("建筑/装修", "33"));
        entities.add(new FilterEntity("电气/能源/动力", "34"));
        entities.add(new FilterEntity("农/林/牧/渔", "35"));
        entities.add(new FilterEntity("医药/生物工程", "36"));
        entities.add(new FilterEntity("环保", "37"));
        entities.add(new FilterEntity("化工", "38"));
        entities.add(new FilterEntity("互联网产品/运营管理", "39"));
        entities.add(new FilterEntity("软件/互联网开发", "40"));
        entities.add(new FilterEntity("IT运维与测试", "41"));
        entities.add(new FilterEntity("服装/纺织/皮革设计/生产", "42"));
        entities.add(new FilterEntity("电子/电器/半导体", "43"));
        entities.add(new FilterEntity("咨询/顾问", "44"));
        entities.add(new FilterEntity("美术/设计/创意", "45"));
        entities.add(new FilterEntity("教育/培训", "46"));
        entities.add(new FilterEntity("媒体/影视/表演", "47"));
        entities.add(new FilterEntity("其他", "48"));
        FilterTwoEntity limit = new FilterTwoEntity("不限", entities);
        FilterTwoEntity SWJob = new FilterTwoEntity("寒暑工", entities);
        FilterTwoEntity internshipJob = new FilterTwoEntity("实习工", entities);
        FilterTwoEntity PartTimeJob = new FilterTwoEntity("兼职", entities);
        FilterTwoEntity normalJob = new FilterTwoEntity("普工2", entities);
        FilterTwoEntity norma2lJob = new FilterTwoEntity("普工3", entities);
        FilterTwoEntity norma3lJob = new FilterTwoEntity("普工4", entities);
        FilterTwoEntity normal4Job = new FilterTwoEntity("普工5", entities);
        FilterTwoEntity normal5Job = new FilterTwoEntity("普工6", entities);
        FilterTwoEntity normal6Job = new FilterTwoEntity("普工a", entities);
        FilterTwoEntity normal8Job = new FilterTwoEntity("普工c", entities);
        FilterTwoEntity normal9Job = new FilterTwoEntity("普工s", entities);
        FilterTwoEntity normal10Job = new FilterTwoEntity("普工z", entities);
        FilterTwoEntity normal11Job = new FilterTwoEntity("普工 ", entities);
        FilterTwoEntity normal12Job = new FilterTwoEntity("普工b", entities);
        locateDate.add(SWJob);
        locateDate.add(internshipJob);
        locateDate.add(PartTimeJob);
        locateDate.add(normalJob);
        locateDate.add(norma2lJob);
        locateDate.add(norma3lJob);
        locateDate.add(normal4Job);
        locateDate.add(normal5Job);
        locateDate.add(normal6Job);
        locateDate.add(normal9Job);
        locateDate.add(normal10Job);
        locateDate.add(normal11Job);
        locateDate.add(normal8Job);
        locateDate.add(limit);
        return locateDate;
    }


    public static List<FilterEntity> getOneFirst() {
        List<FilterEntity> data = new ArrayList<>();
        data.add(new FilterEntity("不限", "1"));
        data.add(new FilterEntity("假期工", "2"));
        data.add(new FilterEntity("临时工", "3"));
        data.add(new FilterEntity("兼职", "4"));
        data.add(new FilterEntity("长期工", "5"));
        return data;
    }

    public static List<FilterEntity> getLocation() {
        List<FilterEntity> data = new ArrayList<>();
        data.add(new FilterEntity("惠城区", "1"));
        data.add(new FilterEntity("惠阳区", "2"));
        data.add(new FilterEntity("博罗县", "3"));
        data.add(new FilterEntity("淡水", "4"));
        data.add(new FilterEntity("惠东县", "5"));
        data.add(new FilterEntity("马安", "6"));
        return data;
    }

    public static List<FilterEntity> getSalary() {
        List<FilterEntity> data = new ArrayList<>();
        data.add(new FilterEntity("1000元以下", "1"));
        data.add(new FilterEntity("1000-1500元", "2"));
        data.add(new FilterEntity("1500-2000元", "3"));
        data.add(new FilterEntity("2000-3000元", "4"));
        data.add(new FilterEntity("3000-4000元", "5"));
        data.add(new FilterEntity("4000-5000元", "6"));
        data.add(new FilterEntity("5000-6000元", "7"));
        data.add(new FilterEntity("6000元以上", "8"));
        return data;
    }

    public static List<FilterEntity> getWelfare() {
        List<FilterEntity> data = new ArrayList<>();
        data.add(new FilterEntity("不限", "1"));
        data.add(new FilterEntity("夜班补贴", "2"));
        data.add(new FilterEntity("伙食补贴", "3"));
        data.add(new FilterEntity("岗位补贴", "4"));
        data.add(new FilterEntity("全勤奖", "5"));
        data.add(new FilterEntity("绩效考核奖", "6"));
        data.add(new FilterEntity("其他补贴", "7"));
        return data;
    }

    public static void setRegionData(List<RegionBean> regionData) {
        regions.clear();
        int i = 2;
        regions.add(new FilterEntity("不限", "1"));
        if(regionData != null){
            for (RegionBean regionBean : regionData) {
                regions.add(new FilterEntity(regionBean.getName(), i + ""));
            }
        }

    }

    public static List<FilterTwoEntity> getAllLocationData(List<AllCityRegionModel> allLocation) {
        List<FilterEntity> entities = null;
        List<FilterTwoEntity> twoEntities = new ArrayList<>();
        HashMap<String,String> cities = new HashMap<>();

        for (AllCityRegionModel allCityRegionModel : allLocation) {
            if (allCityRegionModel.getCity().endsWith("自治州")
                    || allCityRegionModel.getCity().endsWith("自治区")
                    || cities.containsKey(allCityRegionModel.getCity()))
                continue;
            cities.put(allCityRegionModel.getCity(),"");

                entities = new ArrayList<>();
            for (int i = 0; i < allCityRegionModel.getRegion().size(); i++) {
                entities.add(new FilterEntity(allCityRegionModel.getRegion().get(i),i+""));
            }

            twoEntities.add(new FilterTwoEntity(allCityRegionModel.getCity(),entities));
        }
        return twoEntities;

    }
}
