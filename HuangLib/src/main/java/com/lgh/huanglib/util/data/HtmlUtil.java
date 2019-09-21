package com.lgh.huanglib.util.data;

/**
 * <pre>
 *     author : lgh
 *     e-mail : 1045105946@qq.com
 *     time   : 2017/10/16
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HtmlUtil {

    public static String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;} p {color: #999999;font-size: 12px;} table{max-width: 100%; width:auto; height:auto;}" +
                "tr{ width:auto; height:auto;color: #999999;font-size: 12px;} </style>" +
                "</head>";
        return "<html>" + head + "<body style='margin-left:15px;margin-right:15px;'>" + bodyHTML + "</body></html>";
    }
}
