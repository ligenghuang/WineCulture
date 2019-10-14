package com.zhifeng.wineculture.ui.impl;

import com.lgh.huanglib.util.base.BaseView;
import com.zhifeng.wineculture.modules.GeneralDto;
import com.zhifeng.wineculture.modules.post.ForgetPwdPost;
import com.zhifeng.wineculture.modules.post.SetPayPwdPost;

/**
  *
  * @ClassName:     忘记支付密码
  * @Description:
  * @Author:         lgh
  * @CreateDate:     2019/9/17 10:49
  * @Version:        1.0
 */

public interface ForgetPwdView extends BaseView {


    /**
     * 获取验证码
     */
    void getCode();
    void getCodeSuccess(String msg);

    /**
     * 忘记支付密码
     * @param forgetPwdPost
     */
    void forgetPwd(ForgetPwdPost forgetPwdPost);
    void forgetPwdSuccess(GeneralDto generalDto);
    /**
     * 设置支付密码
     * @param setPayPwdPost
     */
    void setPayPwd(SetPayPwdPost setPayPwdPost);
    void setPayPwdSuccess(GeneralDto generalDto);

}
