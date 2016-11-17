package com.example.c_jecy.mail;

import android.os.*;
import android.support.v7.app.AppCompatActivity;

import java.util.Properties;
import android.content.*;
import android.view.View;
import android.widget.TextView;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import android.widget.Button;


public class GetMail extends AppCompatActivity {

    String user;
    String pwd;
    TextView title;
    TextView text;
    TextView fromt;
    int loc=-1;
    Handler myHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_mail);
        Intent it=this.getIntent();
        user=it.getStringExtra("account");
        pwd=it.getStringExtra("password");

        title=(TextView)findViewById(R.id.titletextView);
        text=(TextView)findViewById(R.id.maintextView);
        fromt=(TextView)findViewById(R.id.fromtextView);



        myHandler=new Handler(){
            public  void  handleMessage(android.os.Message msg){

                super.handleMessage(msg);
                String  data;
                switch  (msg.what)  {
                    case  0:
                        //完成主界面更新,拿到数据
                        data  =  (String)msg.obj;
                        title.setText(data);
                        break;
                    case 1:
                        data=(String)msg.obj;
                        text.setText(data);
                    case 2:
                        data=(String)msg.obj;
                        fromt.setText(data);
                    default:
                        break;
                }
            }
        };
        Button next=(Button)findViewById(R.id.nextbutton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t=new Thread(getMail);
                t.start();
            }
        });
        Thread t=new Thread(getMail);
        t.start();
    }

    Runnable getMail = new Runnable() {
        String pop3Server = "pop3.163.com";
        String protocol = "pop3";
        Message[] messages;


        String t,te,fr;
        public void run() {
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", protocol);
            props.setProperty("mail.pop3.host", pop3Server);


            try {
                //  使用Properties对象获得Session对象
                Session session = Session.getInstance(props);
                session.setDebug(true);

                //  利用Session对象获得Store对象，并连接pop3服务器
                Store store = session.getStore();
                store.connect(pop3Server, user, pwd);

                //  获得邮箱内的邮件夹Folder对象，以"只读"打开
                Folder folder = store.getFolder("inbox");
                folder.open(Folder.READ_ONLY);

                //  获得邮件夹Folder内的所有邮件Message对象
                messages = folder.getMessages();



                int mailCounts = messages.length;
                if(loc< 0 ){loc=mailCounts-1;}
                String subject = messages[loc].getSubject();
                String from = (messages[loc].getFrom()[0]).toString();

                fr=from;t=subject;


                te =new String();
                if(messages[loc].getContent() instanceof  Multipart){
                    Multipart multipart = (Multipart) messages[loc].getContent();
                for (int j = 0, n = multipart.getCount(); j < n; j++) {
                    Part part = multipart.getBodyPart(j);
                    if (part.getContent() instanceof Multipart) {
                        Multipart p = (Multipart) part.getContent();
                        for (int k = 0; k < p.getCount(); k++) {
                            te += p.getBodyPart(k).getContent();
                            //if (p.getBodyPart(k).getContentType().startsWith("text/plain")){}
                        }
                    } else {
                        te += part.getContent().toString();
                    }
                }
                }else{
                    te += messages[loc].getContent().toString();
                }


                loc--;
                /*for (int i = 0; i < mailCounts; i++) {

                    String subject = messages[i].getSubject();
                    String from = (messages[i].getFrom()[0]).toString();

                    //System.out.println("第  " + (i + 1) + "封邮件的主题：" + subject);
                    //System.out.println("第  " + (i + 1) + "封邮件的发件人地址：" + from);

                    //setTextView(subject,messages[i].toString(),from);
                    fr=from;t=subject;te=messages[i].toString();


                }*/
            } catch (Exception e) {;}





            //myHandler.sendEmptyMessage(0);
            android.os.Message msg=new android.os.Message();
            msg.obj=t;
            msg.what=0;
            myHandler.sendMessage(msg);
            msg=new android.os.Message();
            msg.obj=te;
            msg.what=1;
            myHandler.sendMessage(msg);
            msg=new android.os.Message();
            msg.obj=fr;
            msg.what=2;
            myHandler.sendMessage(msg);

            //title.setText(t);
            //fromt.setText(fr);
            //text.setText(te);

        }
    };


}
