package com.fandi.util;

import com.fandi.shrio.AccountProfile;
import org.apache.shiro.SecurityUtils;



public class ShrioUtil {
    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
