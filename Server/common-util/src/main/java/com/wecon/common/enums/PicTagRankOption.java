/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wecon.common.enums;

/**
 *
 * @author zengzhipeng_dian91
 */
public enum PicTagRankOption {
    Unknown("未知", 0),
    Sexy("色情露骨", 1);
    
    public int value;
    public String key;

    PicTagRankOption(String _key, int _value) {
        this.key = _key;
        this.value = _value;
    }
    
    public static PicTagRankOption valueOf(int value) {
        switch (value) {
            case 1:
                return Sexy;
            default:
                return Unknown;
        }
    }
}
