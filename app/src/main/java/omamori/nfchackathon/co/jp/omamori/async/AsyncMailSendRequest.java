package omamori.nfchackathon.co.jp.omamori.async;

import android.net.Uri;
import android.os.AsyncTask;

import omamori.nfchackathon.co.jp.omamori.model.Mail;


/**
 * 非同期メール送信クラス
 */
public class AsyncMailSendRequest extends AsyncTask<Uri.Builder, Void, String> {

    private String subject; // メール件名
    private String content; // メール本文

    /**
     * コンストラクタ
     *
     * @param subject メール件名
     * @param content メール本文
     */
    public AsyncMailSendRequest(final String subject, final String  content)
    {
        this.subject = subject;
        this.content = content;
    }

    /**
     * バックグラウンド処理
     *
     * @param builder
     * @return
     */
    @Override
    protected String doInBackground(Uri.Builder... builder) {

        try{
            // メール送信
            Mail mail = new Mail(this.subject, this.content);
            mail.send();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

