package com.zhifeng.wineculture.utils.json;

import android.content.Context;
import android.content.res.AssetManager;

import com.lgh.huanglib.util.L;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetJsonDataUtil {

    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            L.e("lgh",e.getMessage());
        }
        return stringBuilder.toString();
    }
}
