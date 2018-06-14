package omamori.nfchackathon.co.jp.omamori.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OmamoriUser implements Serializable {

    private static OmamoriUser instance = null;

    public String userId;
    public String userName;

    private Map<String, String> userInfo;

    /**
     * 自クラスのインスタンス生成
     *
     * @param userId OMAMORIユーザID
     * @return 自クラスのインスタンス
     */
    public static OmamoriUser getInstance(final String userId)
    {
        if(instance == null)
        {
            return new OmamoriUser(userId);
        }

        return instance;
    }

    /**
     * コンストラクタ
     *
     * @param userId OMAMORIユーザID
     */
    private OmamoriUser(final String userId)
    {
        this.initOmamoriUserData();
        this.userId = userId;
        this.userName = userInfo.get(userId);
    }

    /**
     * ユーザ情報の初期化
     */
    private void initOmamoriUserData()
    {
        userInfo = new HashMap<>();
        userInfo.put("nfc_testuser1", "AA AAさん");
        userInfo.put("nfc_testuser2", "BB BBさん");
        userInfo.put("nfc_testuser3", "CC CCさん");
        userInfo.put("nfc_testuser4", "DD DDさん");
        userInfo.put("nfc_testuser5", "EE EEさん");
        userInfo.put("nfc_testuser6", "FF FFさん");
        userInfo.put("nfc_testuser7", "GG GGさん");
    }

}
