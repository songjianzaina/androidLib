package com.insworks.lib_bank_number_identify.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.insworks.lib_bank_number_identify.domain.BankCardInfo;
import com.insworks.lib_bank_number_identify.domain.BankCardInfoPatten;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by niutingting on 2017/5/20.
 * <p>
 * 返回银行卡信息
 * 1.自动识别（根据前几位bin号识别）
 * 2.输入完全识别
 * 两个的json文件的正则不一样
 *
 */

public class CheckBankNameUtil {

    private List<BankCardInfoPatten> bankInfos;

    /**
     * 只识别前几位的
     * @param context
     * @param cardNum
     * @return
     * @throws
     */
    public BankCardInfo getBankCardInfo(Context context, String cardNum) throws Exception {


        //获取Json文件数据
        String json = getJson(context, "bank_card_rules.json");
        //解析Json
        Type listType = new TypeToken<ArrayList<BankCardInfoPatten>>() {
        }.getType();
        bankInfos = new Gson().fromJson(json, listType);
        //查找
        for (BankCardInfoPatten bankInfoPatten : bankInfos) {
            for (BankCardInfoPatten.PatternsBean patternBean : bankInfoPatten.getPatterns()) {
                Pattern p = Pattern.compile(patternBean.getReg());
                Matcher m = p.matcher(cardNum);
                if (m.find()) {
                    BankCardInfo bankCarInfo = new BankCardInfo();
                    bankCarInfo.setBankCode(bankInfoPatten.getBankCode());
                    bankCarInfo.setBankName(bankInfoPatten.getBankName());
                    bankCarInfo.setCardType(patternBean.getCardType());
                    switch (patternBean.getCardType()) {
                        case "DC":
                            bankCarInfo.setCardTypeName("储蓄卡");
                            break;
                        case "CC":
                            bankCarInfo.setCardTypeName("信用卡");
                            break;
                        case "SCC":
                            bankCarInfo.setCardTypeName("准贷记卡");
                            break;
                        case "PC":
                            bankCarInfo.setCardTypeName("预付费卡");
                            break;
                    }
                    return bankCarInfo;
                }
            }
        }
        return null;
    }


    /**
     * 输入完成全部识别
     * @param context
     * @param cardNum
     * @return
     * @throws Exception
     */
    public BankCardInfo getBankCardInfoAll(Context context, String cardNum) throws Exception {


        //获取Json文件数据
        String json = getJson(context, "bank_card_rules_all.json");
        //解析Json
        Type listType = new TypeToken<ArrayList<BankCardInfoPatten>>() {
        }.getType();
        bankInfos = new Gson().fromJson(json, listType);
        //查找
        for (BankCardInfoPatten bankInfoPatten : bankInfos) {
            for (BankCardInfoPatten.PatternsBean patternBean : bankInfoPatten.getPatterns()) {
                Pattern p = Pattern.compile(patternBean.getReg());
                Matcher m = p.matcher(cardNum);
                if (m.find()) {
                    BankCardInfo bankCarInfo = new BankCardInfo();
                    bankCarInfo.setBankCode(bankInfoPatten.getBankCode());
                    bankCarInfo.setBankName(bankInfoPatten.getBankName());
                    bankCarInfo.setCardType(patternBean.getCardType());
                    switch (patternBean.getCardType()) {
                        case "DC":
                            bankCarInfo.setCardTypeName("储蓄卡");
                            break;
                        case "CC":
                            bankCarInfo.setCardTypeName("信用卡");
                            break;
                        case "SCC":
                            bankCarInfo.setCardTypeName("准贷记卡");
                            break;
                        case "PC":
                            bankCarInfo.setCardTypeName("预付费卡");
                            break;
                    }
                    return bankCarInfo;
                }
            }
        }
        return null;
    }


    /**
     * 读取本地文件中JSON字符串
     *
     * @param fileName
     * @return
     */
    private String getJson(Context mContext, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    mContext.getAssets().open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
