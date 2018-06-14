package omamori.nfchackathon.co.jp.omamori;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        try {

            // 動画再生
            final VideoView videoView = (VideoView) findViewById(R.id.movie);
            videoView.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/" + R.raw.samplecm));
            videoView.start();

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    // 登録完了画面へ遷移
                    Intent intent = new Intent(getApplication(), CompRegistationActivity.class);
                    startActivity(intent);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
