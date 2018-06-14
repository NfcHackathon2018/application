package omamori.nfchackathon.co.jp.omamori;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Arrays;

import omamori.nfchackathon.co.jp.omamori.model.OmamoriUser;

/**
 * ピタッチ画面クラス
 */
public class PiTouchActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pi_touch);

        // 仮：情報入力画面へ遷移
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), InputInfoActivity.class);
                startActivity(intent);
            }
        });

        // Foreground Dispatch Systemの設定
        nfcAdapter = android.nfc.NfcAdapter.getDefaultAdapter(this);

    }
    @Override
    protected void onResume() {
        super.onResume();

        // NFCがかざされたときの設定
        Intent intent = new Intent(this, this.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // ほかのアプリを開かないようにする
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();

        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String omamoriUserId = intent.getData().toString();
        OmamoriUser user = OmamoriUser.getInstance(omamoriUserId);

        Intent piTouchIntent = new Intent(getApplication(), InputInfoActivity.class);
        piTouchIntent.putExtra("user", user);
        startActivity(piTouchIntent);

    }

}

