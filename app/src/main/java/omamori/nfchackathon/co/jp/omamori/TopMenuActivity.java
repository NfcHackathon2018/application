package omamori.nfchackathon.co.jp.omamori;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class TopMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_menu);

        // ピタッチ画面へ遷移
        findViewById(R.id.pitouch_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), PiTouchActivity.class);
                startActivity(intent);
            }
        });
    }
}
