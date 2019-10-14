package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.GeneralDto;

/**
  *
  * @ClassName:     修改手机号码 验证码
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/16 18:11
  * @Version:        1.0
 */

public interface ModifyMobileCodeView extends BaseView {

    /**
     * 获取验证码
     */
    void getCode();
    void getCodeSuccess(String msg);

    /**
     * 换绑手机
     * @param verify_code
     * @param phone
     */
    void changePhone(String verify_code, String phone);
    void changePhoneSuccess(GeneralDto generalDto);
}
