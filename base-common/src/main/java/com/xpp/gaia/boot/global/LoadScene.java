package com.xpp.gaia.boot.global;

import static com.xpp.gaia.toolkit.action.ActionHandler.assertCheck;
import static com.xpp.gaia.toolkit.utils.JwtUtil.LOADER;

import com.xpp.gaia.toolkit.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * LoadScene
 *
 * @author Akira
 * @since 2024/6/21
 */
public enum LoadScene {

    UAC("统一门户"),
    SFA("SFA"),
    SFA_APP("SFA小程序"),
    DMS_APP("经销商小程序"),

    UN_KNOW("未定义"),
    ;

    private String name;

    LoadScene(String name) {
        this.name = name;
    }

    public static LoadScene getThisScene(String key) {
        assertCheck(StringUtils.isNotBlank(key), "无效的场景参数");
        LoadScene[] all = LoadScene.values();
        for (LoadScene each : all) {
            if (each.toString().equals(key)) {
                return each;
            }
        }
        assertCheck(false, "无效的场景参数");
        return null;
    }

    public static LoadScene getSceneFromToken(String token) {
        if (JwtUtil.isUnformed(token)) {
            return SFA;
        }
        JwtUtil.verifyToken(JwtUtil.secretKey, token);
        String loader = JwtUtil.getClaimByName(token, LOADER).asString();
        return getThisScene(loader);
    }

}
