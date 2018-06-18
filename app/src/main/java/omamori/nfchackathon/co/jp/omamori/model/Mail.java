package omamori.nfchackathon.co.jp.omamori.model;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * メール管理クラス
 */
public class Mail {

    // メール件名テンプレート
    public static final String SUBJECT_TEMP = "【%s】OMAMORI通知";
    // メール本文テンプレート
    public static final String CONTENT_TEMP =
            "＝＝＝＝＝＝＝＝＝＝＝＝＝＝\n"
                    +"OMAMORIで %s と %s が会ったようです。\n"
                    +"【時間】%s\n"
                    +"【場所】%s\n"
                    +"【内容】%s\n"
                    +"＝＝＝＝＝＝＝＝＝＝＝＝＝＝\n"
                    +"\n"
                    +"\n"
                    +"このメールに対するお問い合わせは以下のURLよりお願いいたします。\n"
                    +"http://www.dnp.co.jp\n"
                    +"\n"
                    +"--------------------------\n"
                    +"大日本印刷株式会社\n"
                    +"Dai Nippon Printing Co.,Ltd.\n"
                    +"--------------------------\n";


    private Properties properties;

    // メール設定
    private static final String MAIL_FROM = "testnfc20180525@gmail.com";    // 送信元アドレス
    private static final String MAIL_TO = "nfc.hackathon2018@gmail.com";    // 送信先アドレス
    private static final String CHARSET = "UTF-8";
    private static final String ENCODING = "base64";
    private String subject; // メール件名
    private String content; // メール本文

    // gmail設定
    private static final String ACCOUNT_ID = "testnfc20180525@gmail.com";    // GoogleアカウントID
    private static final String ACCOUNT_PW = "nfchackathon";                   // Googleアカウントパスワード
    private static final String MAIL_HOST = "smtp.gmail.com";
    private static final String MAIL_PORT = "587";
    private static final String MAIL_AUTH = "true";
    private static final String MAIL_STARTTLS = "true";

    /**
     * コンストラクタ
     *
     * @param subject メール件名
     * @param content メール本文
     */
    public  Mail(final String subject, final String content)
    {
        properties = System.getProperties();
        this.subject = subject;
        this.content = content;
    }

    /**
     * メール送信
     */
    public void send()
    {
        properties.put("mail.smtp.host", MAIL_HOST);
        properties.put("mail.smtp.port", MAIL_PORT);
        properties.put("mail.smtp.auth", MAIL_AUTH);
        properties.put("mail.smtp.starttls.enable", MAIL_STARTTLS);

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(ACCOUNT_ID, ACCOUNT_PW);
            }
        });
        MimeMessage message = new MimeMessage(session);

        try {
            String from = MAIL_FROM;
            String[] to = {MAIL_TO};
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];
            for(int i = 0; i < to.length; i++ ){
                toAddress[i] = new InternetAddress(to[i]);
            }
            for(int i = 0; i < toAddress.length; i++){
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(this.subject, CHARSET);
            message.setText(this.content, CHARSET);
            message.setHeader("Content-Transfer-Encoding", ENCODING);
            // 送信
            Transport.send(message);

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

