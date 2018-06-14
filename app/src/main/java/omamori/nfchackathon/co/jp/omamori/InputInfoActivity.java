package omamori.nfchackathon.co.jp.omamori;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import omamori.nfchackathon.co.jp.omamori.async.AsyncMailSendRequest;
import omamori.nfchackathon.co.jp.omamori.model.AppUser;
import omamori.nfchackathon.co.jp.omamori.model.Mail;
import omamori.nfchackathon.co.jp.omamori.model.OmamoriUser;

public class InputInfoActivity extends AppCompatActivity  implements LocationListener {

    private LocationManager locationManager;    // ロケーション
    private double latitude;    // 経緯
    private double longitude;   // 緯度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_info);

        // 前画面のIntent取得
        Intent intent = getIntent();
        OmamoriUser user = (OmamoriUser) intent.getSerializableExtra("user");

        // 名前取得
        TextView nameTextView = (TextView) findViewById(R.id.name_textView);
        nameTextView.setText(AppUser.APP_USER_NAME);

        // 位置情報取得
        this.initGps();

        // 現在時刻取得
        SimpleDateFormat date = new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒");
        String now = date.format(new Date());
        TextView timeTextView = (TextView) findViewById(R.id.time_textView);
        timeTextView.setText(now);

        // 登録ボタン押下イベント
        findViewById(R.id.register_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // メール送信
                // Uri.Builder builder = new Uri.Builder();
                // String subject = String.format(Mail.SUBJECT_TEMP, "母さん");
                // String content = String.format(Mail.CONTENT_TEMP, "母さん", AppUser.APP_USER_NAME, ((TextView) findViewById(R.id.time_textView)).getText(), ((TextView) findViewById(R.id.memo_textView)).getText());

                // AsyncMailSendRequest asyncTask = new AsyncMailSendRequest(subject, content);
                // asyncTask.execute(builder);

                // 動画再生画面へ遷移
                Intent intent = new Intent(getApplication(), MovieActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * 位置情報取得の初期化
     */
    private void initGps()
    {
        // GPS使用許可権限確認
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 許可されていない場合
            ActivityCompat.requestPermissions(InputInfoActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1000);
            return;
        }
        // NetWork位置情報使用許可権限確認
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 許可されていない場合
            ActivityCompat.requestPermissions(InputInfoActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1000);
            return;
        }

        // 位置情報取得
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0 , 0, this);
    }


    @Override
    public void onLocationChanged(Location location) {

        try {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            // 現在位置を取得したらインスタンス削除
            locationManager.removeUpdates(this);

            // 緯度、経度から住所取得
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            StringBuilder result = new StringBuilder();
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            for (Address address : addresses) {
                int idx = address.getMaxAddressLineIndex();
                // 1番目のレコードは国名のため省略
                for (int i = 0; i <= idx; i++) {
                    result.append(address.getAddressLine(i));
                }
            }

            // 住所TextViewに住所を表示
            TextView text = (TextView) findViewById(R.id.address_textView);
            text.setText(result.toString().split("、")[1]);
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}

