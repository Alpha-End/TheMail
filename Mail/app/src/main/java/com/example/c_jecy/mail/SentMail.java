package com.example.c_jecy.mail;

import android.content.Intent;
import android.content.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class SentMail extends AppCompatActivity {

    String to ;
    String from ;
    String account ;
    String pass ;
    String sub ;
    String text ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_mail);
        Intent it=this.getIntent();
        account=it.getStringExtra("account");
        pass=it.getStringExtra("password");
        from =account;
        Button send=(Button)findViewById(R.id.sendbutton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tot=(TextView)findViewById(R.id.toeditText);
                to=tot.getText().toString();
                TextView tit=(TextView)findViewById(R.id.titleeditText);
                sub=tit.getText().toString();
                TextView tet=(TextView)findViewById(R.id.main_bodyeditText);
                text=tet.getText().toString();
                new Thread(sentMail).start();
            }
        });

    }
    Runnable sentMail = new Runnable() {



        public void run() {
            try {


                String host = "smtp.163.com";

                Properties properties = System.getProperties();

                properties.setProperty("mail.smtp.host", host);

                properties.put("mail.smtp.auth", "true");
                Session session = Session.getDefaultInstance(properties, new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(account, pass);
                    }
                });
                session.setDebug(true);
                MimeMessage message = new MimeMessage(session);

                message.setFrom(new InternetAddress(from));

                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(to));

                message.setSubject(sub);

                message.setText(text);

                Transport.send(message);
                SentMail.this.finish();
            } catch (MessagingException mex) {

                mex.printStackTrace();
            }


        }
    };
}
