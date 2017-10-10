package tuongvong.appgame.android.milionnaire;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.wang.avi.AVLoadingIndicatorView;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import tuongvong.appgame.android.milionnaire.animation.ActivityAnimator;
import tuongvong.appgame.android.milionnaire.database.DBHelper;
import tuongvong.appgame.android.milionnaire.model.People;
import tuongvong.appgame.android.milionnaire.model.Question;
import tuongvong.appgame.android.milionnaire.model.Users;
import tuongvong.appgame.android.milionnaire.myadapter.ListContactAdapter;
import tuongvong.appgame.android.milionnaire.myadapter.MenuAdapter;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView iv_stop, iv_5050, iv_ykien, iv_call;
    Button btn_dapanA, btn_dapanB, btn_dapanC, btn_dapanD;
    TextView tv_time, tv_coin, tv_cauhoi, tv_socau;
    DrawerLayout drawer;
    NavigationView mNavigation;
    private static DatabaseReference mDatabase;
    ArrayList<Question> listQuestion;
    DBHelper myDataBase;
    ArrayList<String> tien;
    MenuAdapter adapter;
    Question ob_question;
    int i = 0, id1, count = 0, index, trueCase, time=30, tiencu, coin, coin_new, num50;
    int idHelp;
    Map<String, String> question, user;
    MediaPlayer mediaPlayer;
    Dialog dialog;
    private AVLoadingIndicatorView avi;
    String indicator;
    private static DemNguocRunnable mDemnguocRun;
    private static Handler mDemnguocHandler;
    Users users;
    ListView lv_item;
    ArrayList<People> arr_people;
    ArrayList<People> four_peoples = new ArrayList<People>();
    People people;
    ListContactAdapter contact_adapter;
    public Dialog dialog1;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    String name, name_f, email, email_f, url_image, url_image_f, uid;
    Uri photoUrl_f, photoUrl;
    String diem;
    boolean isRunning = false;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 10;

    public static final int[] SOUND_QUESTIONS = {
            R.raw.ques01,
            R.raw.ques02,
            R.raw.ques03,
            R.raw.ques04,
            R.raw.ques05,
            R.raw.ques06,
            R.raw.ques07,
            R.raw.ques08,
            R.raw.ques09,
            R.raw.ques10,
            R.raw.ques11,
            R.raw.ques12,
            R.raw.ques13,
            R.raw.ques14,
            R.raw.ques15,
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play);

        iv_stop = (ImageView) findViewById(R.id.imageView_stop);
        iv_5050 = (ImageView) findViewById(R.id.imageView_5050);
        iv_ykien = (ImageView) findViewById(R.id.imageView_ykien);
        iv_call = (ImageView) findViewById(R.id.imageView_call);

        btn_dapanA = (Button) findViewById(R.id.btn_a);
        btn_dapanB = (Button) findViewById(R.id.btn_b);
        btn_dapanC = (Button) findViewById(R.id.btn_c);
        btn_dapanD = (Button) findViewById(R.id.btn_d);

        tv_time = (TextView) findViewById(R.id.textView_tg);
        tv_coin = (TextView) findViewById(R.id.textView_diem);
        tv_cauhoi = (TextView) findViewById(R.id.textview_cauhoi);
        tv_socau = (TextView) findViewById(R.id.textview_cau);

        listQuestion = new ArrayList<Question>();
        try {
            myDataBase = new DBHelper(this);
            listQuestion = myDataBase.getData();
        }catch (IOException e){
        }





        tien = new ArrayList<>();


        tien.add("15.   150000");
        tien.add("14.   80000");
        tien.add("13.   60000");
        tien.add("12.   40000");
        tien.add("11.   30000");
        tien.add("10.   22000");
        tien.add("9.    14000");
        tien.add("8.    10000");
        tien.add("7.    6000");
        tien.add("6.    3000");
        tien.add("5.    2000");
        tien.add("4.    1000");
        tien.add("3.    600");
        tien.add("2.    400");
        tien.add("1.    200");
        adapter = new MenuAdapter(PlayActivity.this, tien);

        //numprevious = new ArrayList<Integer>();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        iv_stop.setOnClickListener(this);
        iv_5050.setOnClickListener(this);
        iv_ykien.setOnClickListener(this);
        iv_call.setOnClickListener(this);

        btn_dapanA.setOnClickListener(this);
        btn_dapanB.setOnClickListener(this);
        btn_dapanC.setOnClickListener(this);
        btn_dapanD.setOnClickListener(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigation = (NavigationView) findViewById(R.id.nav_view);
        lv_item = (ListView) findViewById(R.id.lv_menu);
        lv_item.setAdapter(adapter);

        // set width nacvigation drawer
        int width = getResources().getDisplayMetrics().widthPixels/2;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mNavigation.getLayoutParams();
        params.width = width;
        mNavigation.setLayoutParams(params);

        //drawer.openDrawer(GravityCompat.START);


        indicator=getIntent().getStringExtra("BallSpinFadeLoaderIndicator");   // animation thời gian
        avi= (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.hide();
        mDemnguocHandler = new Handler();
        mDemnguocRun = new DemNguocRunnable();
        playSound(R.raw.ready);
        final Dialog dialog = new Dialog(PlayActivity.this, R.style.cust_dialog);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Include dialog.xml file
        dialog.setTitle("Thông báo");
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);
        dialog.setContentView(R.layout.ready_dialog);
        TextView text = (TextView) dialog.findViewById(R.id.textDialog);
        text.setText("Bạn đã sẵn sàng cùng chơi với chúng tôi?");

        dialog.show();

        final Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
        final Button cancelButton = (Button) dialog.findViewById(R.id.cancelButton);
        // if decline button is clicked, close the custom dialog
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog1 = new Dialog(PlayActivity.this, R.style.cust_dialog);
                //dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // Include dialog.xml file
                dialog1.setTitle("Thông báo");
                dialog1.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box2);
                dialog1.setContentView(R.layout.listcontacts_dialog);
                arr_people = new ArrayList<>();
                getAllContact();
                final Button declineButton = (Button) dialog1.findViewById(R.id.declineButton1);
                final Button cancelButton = (Button) dialog1.findViewById(R.id.cancelButton1);
                contact_adapter = new ListContactAdapter(dialog1.getContext(), arr_people);
                LinearLayoutManager lmanager=new LinearLayoutManager(dialog1.getContext());
                lmanager.setOrientation(LinearLayoutManager.VERTICAL);

                final FastScrollRecyclerView recyclerView = (FastScrollRecyclerView) dialog1.findViewById(R.id.recycler);
                recyclerView.setLayoutManager(lmanager);
                recyclerView.setHasFixedSize(false);
                recyclerView.setAdapter(contact_adapter);

                dialog1.show();
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        for(People p : contact_adapter.list){
                            four_peoples.add(p);
                        }
                        if(contact_adapter.list.size() == 1){
                            try{
                                count++;
                                tablevcoin(count);
                                playSound(R.raw.batdauchoi);
                                dialog1.dismiss();
                                Thread.sleep(2000);
                                playSound(SOUND_QUESTIONS[count - 1]);

                                tv_socau.setText("Câu " + count);
                                tv_cauhoi.setText(listQuestion.get(count - 1).cauhoi);
                                btn_dapanA.setText("A. " +listQuestion.get(count - 1).caseA);
                                btn_dapanB.setText("B. " + listQuestion.get(count - 1).caseB);
                                btn_dapanC.setText("C. " + listQuestion.get(count - 1).caseC);
                                btn_dapanD.setText("D. " + listQuestion.get(count - 1).caseD);

                                tv_time.setText(time+"");
                                thoigian();
                                avi.show();
                                playSoundLoop(R.raw.moc1);
                                trueCase();


                            }catch (Exception e){
                                Toast.makeText(PlayActivity.this, "Error loading", Toast.LENGTH_SHORT).show();
                            }
                        }else if(contact_adapter.list.size() < 1){
                            Toast.makeText(PlayActivity.this, "Bạn phải chọn 1 người thân", Toast.LENGTH_SHORT).show();
                        }
                        else if(contact_adapter.list.size() > 1){
                            Toast.makeText(PlayActivity.this, "Bạn chỉ được chọn 1 người thân", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goMainActivity();
                    }
                });


            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goMainActivity();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted

            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void getAllContact(){
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        people = new People(id, name, phoneNo);
                        arr_people.add(people);
                    }
                    pCur.close();
                }
            }
        }
    }


    @Override
    public void onClick(final View view) {

        if (view.getId() == R.id.btn_a ||
                view.getId() == R.id.btn_b ||
                view.getId() == R.id.btn_c ||
                view.getId() == R.id.btn_d) {
            view.setBackgroundResource(R.drawable.select);
            btn_dapanA.setEnabled(false);
            btn_dapanB.setEnabled(false);
            btn_dapanC.setEnabled(false);
            btn_dapanD.setEnabled(false);
            mDemnguocHandler.removeCallbacks(mDemnguocRun); // dừng thời gian
            //view.setEnabled(false);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    // nếu chọn đúng đáp án
                    if (view.getId() == trueCase) {
                        anim(trueCase);
                        view.setEnabled(true);
                        view.setBackgroundResource(R.drawable.button3);
                        switch (trueCase) {
                            case R.id.btn_a:
                                playSound(R.raw.true_a);
                                break;

                            case R.id.btn_b:
                                playSound(R.raw.true_b);
                                break;

                            case R.id.btn_c:
                                playSound(R.raw.true_c);
                                break;

                            case R.id.btn_d:
                                playSound(R.raw.true_d);
                                break;
                        }
                        tienthuong(count);

                        count++;
                        tablevcoin(count);
                        //playSound(SOUND_QUESTIONS[count-1]);
                        if (count < 16) {
                            hienthicauhoi();
                        }if(count == 16) {
                            // trả lời đúng hết 15 câu
                            mediaPlayer.stop();
                            try {
                                Thread.sleep(2000);
                                avi.hide();

                                final Dialog dialog = new Dialog(PlayActivity.this, R.style.cust_dialog);
                                //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                // Include dialog.xml file
                                dialog.setContentView(R.layout.finish_dialog);
                                // Set dialog title
                                dialog.setTitle("Thông báo");
                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);
                                // set values for custom dialog components - text, image and button

                                TextView text = (TextView) dialog.findViewById(R.id.textDialog);
                                TextView text_coin = (TextView) dialog.findViewById(R.id.textDialog_coin);
                                String tien = tv_coin.getText().toString();
                                text.setText("Chúc mừng bạn đã trở thành triệu phú!");
                                text_coin.setText(tien);
                                dialog.setCancelable(false);
                                // dialog.setContentView(view);
                                dialog.show();
                                Button btnOk = (Button) dialog.findViewById(R.id.btn_ok_finish);
                                btnOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        updatecoin();
                                        goMainActivity();
                                    }
                                });

                                tv_cauhoi.setText("");
                                btn_dapanA.setText("");
                                btn_dapanB.setText("");
                                btn_dapanC.setText("");
                                btn_dapanD.setText("");
                                btn_dapanA.setBackgroundResource(R.drawable.button3);
                                btn_dapanB.setBackgroundResource(R.drawable.button3);
                                btn_dapanC.setBackgroundResource(R.drawable.button3);
                                btn_dapanD.setBackgroundResource(R.drawable.button3);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    // nếu chọn sai đáp án
                    else {
                        btn_dapanA.setEnabled(true);
                        btn_dapanB.setEnabled(true);
                        btn_dapanC.setEnabled(true);
                        btn_dapanD.setEnabled(true);
                        mediaPlayer.stop();
                        switch (trueCase) {
                            case R.id.btn_a:
                                view.setBackgroundResource(R.drawable.button3);
                                btn_dapanA.setBackgroundResource(R.drawable.select);
                                playSound(R.raw.lose_a);
                                anim(R.id.btn_a);
                                break;
                            case R.id.btn_b:
                                view.setBackgroundResource(R.drawable.button3);
                                btn_dapanB.setBackgroundResource(R.drawable.select);
                                playSound(R.raw.lose_b);
                                anim(R.id.btn_b);
                                break;
                            case R.id.btn_c:
                                view.setBackgroundResource(R.drawable.button3);
                                btn_dapanC.setBackgroundResource(R.drawable.select);
                                playSound(R.raw.lose_c);
                                anim(R.id.btn_c);
                                break;
                            case R.id.btn_d:
                                view.setBackgroundResource(R.drawable.button3);
                                btn_dapanD.setBackgroundResource(R.drawable.select);
                                playSound(R.raw.lose_d);
                                anim(R.id.btn_d);
                                break;
                        }
                        try {

                            Thread.sleep(4000);
                            final Dialog dialog = new Dialog(PlayActivity.this, R.style.cust_dialog);
                            //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            // Include dialog.xml file
                            dialog.setContentView(R.layout.finish_dialog);
                            // Set dialog title
                            dialog.setTitle("Thông báo");
                            dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);
                            // set values for custom dialog components - text, image and button

                            TextView text = (TextView) dialog.findViewById(R.id.textDialog);
                            TextView text_coin = (TextView) dialog.findViewById(R.id.textDialog_coin);
                            text.setText("Thật tiếc bạn đã thua!");
                            finalcoin(text_coin, count);
                            dialog.setCancelable(false);
                            // dialog.setContentView(view);
                            dialog.show();
                            Button btnOk = (Button) dialog.findViewById(R.id.btn_ok_finish);
                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    updatecoin_fail();
                                    goMainActivity();

                                }
                            });
                            /*tv_cauhoi.setText("");
                            btn_dapanA.setText("");
                            btn_dapanB.setText("");
                            btn_dapanC.setText("");
                            btn_dapanD.setText("");
                            btn_dapanA.setBackgroundResource(R.drawable.button3);
                            btn_dapanB.setBackgroundResource(R.drawable.button3);
                            btn_dapanC.setBackgroundResource(R.drawable.button3);
                            btn_dapanD.setBackgroundResource(R.drawable.button3);*/


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }


                }
            }, 4000);


        }
        // quyền trợ giúp 50/50
        if (view.getId() == R.id.imageView_5050) {
            iv_5050.setImageResource(R.drawable.atp__activity_player_button_image_help_5050_x);
            iv_5050.setEnabled(false);
            playSound(R.raw.sound5050);
            try{
                Thread.sleep(4000);
                if (trueCase == R.id.btn_a) {
                    btn_dapanB.setText("");
                    btn_dapanB.setEnabled(false);
                    btn_dapanC.setText("");
                    btn_dapanC.setEnabled(false);
                } else if (trueCase == R.id.btn_b) {
                    btn_dapanA.setText("");
                    btn_dapanA.setEnabled(false);
                    btn_dapanD.setText("");
                    btn_dapanD.setEnabled(false);
                } else if (trueCase == R.id.btn_c) {
                    btn_dapanD.setText("");
                    btn_dapanD.setEnabled(false);
                    btn_dapanB.setText("");
                    btn_dapanB.setEnabled(false);
                } else if (trueCase == R.id.btn_d) {
                    btn_dapanC.setText("");
                    btn_dapanC.setEnabled(false);
                    btn_dapanA.setText("");
                    btn_dapanA.setEnabled(false);
                }
                playSound(R.raw.s50);
                num50=count;
            }catch (Exception e){
            }
        }

        // quyền trợ giúp gọi cho người thân
        if (view.getId() == R.id.imageView_call) {
            mDemnguocHandler.removeCallbacks(mDemnguocRun);
            iv_call.setImageResource(R.drawable.atp__activity_player_button_image_help_call_x);
            iv_call.setEnabled(false);
            playSound(R.raw.call);
            try{
                Thread.sleep(2000);
                call();
            }catch (Exception e){
            }

        }
        // xin dừng cuộc chơi
        if (view.getId() == R.id.imageView_stop) {
            stop();
        }
        // quyền trợ giúp hỏi ý kiến khán giả
        if (view.getId() == R.id.imageView_ykien) {
            mDemnguocHandler.removeCallbacks(mDemnguocRun);
            iv_ykien.setImageResource(R.drawable.atp__activity_player_button_image_help_audience_x);
            iv_ykien.setEnabled(false);
            playSound(R.raw.khan_gia);
            try{
                Thread.sleep(6000);
                initDialogAudience();
            }catch (Exception e){}

        }

    }

    public void hienthicauhoi(){
        tablevcoin(count);
        trueCase();
        playSound(SOUND_QUESTIONS[count - 1]);
        action();
        tv_socau.setText("Câu " + count);
        tv_cauhoi.setText(listQuestion.get(count - 1).cauhoi);
        btn_dapanA.setText("A. " +listQuestion.get(count - 1).caseA);
        btn_dapanB.setText("B. " + listQuestion.get(count - 1).caseB);
        btn_dapanC.setText("C. " + listQuestion.get(count - 1).caseC);
        btn_dapanD.setText("D. " + listQuestion.get(count - 1).caseD);
        time = 30;
        tv_time.setText(time+"");
        thoigian();
    }


    public void call(){
        String num = four_peoples.get(0).numberphone;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+num));
        if (ActivityCompat.checkSelfPermission(PlayActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PlayActivity.this,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    10);
            return;
        }else {
            try{
                startActivity(callIntent);
            }
            catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(getApplicationContext(),"Can't call for"+num,Toast.LENGTH_SHORT).show();
            }
        }
    }

    // gán đáp án đúng cho mỗi câu hỏi ở level 1
    public void trueCase() {
        id1 = listQuestion.get(count-1).trueCase;
        switch (id1) {
            case 1:
                trueCase = R.id.btn_a;
                break;
            case 2:
                trueCase = R.id.btn_b;
                break;
            case 3:
                trueCase = R.id.btn_c;
                break;
            case 4:
                trueCase = R.id.btn_d;
                break;
        }
    }

    // hiển thị tiền thuởng sau mỗi câu hỏi
    public void tienthuong(int count) {
        switch (count) {
            case 1:
                coin = 200;
                tv_coin.setText(String.valueOf(coin));
                break;
            case 2:
                coin = 400;
                tv_coin.setText(String.valueOf(coin));
                break;
            case 3:
                coin = 600;
                tv_coin.setText(String.valueOf(coin));
                break;
            case 4:
                coin = 1000;
                tv_coin.setText(String.valueOf(coin));
                break;
            case 5:
                coin = 2000;
                tv_coin.setText(String.valueOf(coin));
                break;
            case 6:
                coin = 3000;
                tv_coin.setText(String.valueOf(coin));
                break;
            case 7:
                coin = 6000;
                tv_coin.setText(String.valueOf(coin));
                break;
            case 8:
                coin = 10000;
                tv_coin.setText(String.valueOf(coin));
                break;
            case 9:
                coin = 14000;
                tv_coin.setText(String.valueOf(coin));
                break;
            case 10:
                coin = 22000;
                tv_coin.setText(String.valueOf(coin));
                break;
            case 11:
                coin = 30000;
                tv_coin.setText(String.valueOf(coin));
                break;
            case 12:
                coin = 40000;
                tv_coin.setText(String.valueOf(coin));
                break;
            case 13:
                coin = 60000;
                tv_coin.setText(String.valueOf(coin));
                break;
            case 14:
                coin = 85000;
                tv_coin.setText(String.valueOf(coin));
                break;
            case 15:
                coin = 150000;
                tv_coin.setText(String.valueOf(coin));
                break;
        }
    }

    // tính tiền thuởng khi chọn sai câu hỏi và hết thời gian
    public void finalcoin(TextView tv, int count){
        if(count<=5){
            switch (count) {
                case 1:
                    coin = 0;
                    tv.setText(String.valueOf(coin));
                    break;
                case 2:
                    coin = 200;
                    tv.setText(String.valueOf(coin));
                    break;
                case 3:
                    coin = 400;
                    tv.setText(String.valueOf(coin));
                    break;
                case 4:
                    coin = 600;
                    tv.setText(String.valueOf(coin));
                    break;
                case 5:
                    coin = 1000;
                    tv.setText(String.valueOf(coin));
                    break;
            }
        }
        else if(count>5 && count <=10){
            coin = 2000;
            tv.setText(String.valueOf(coin));
        }
        else if(count>10){
            switch (count){
                case 11:
                    coin = 22000;
                    tv.setText(String.valueOf(coin));
                    break;
                case 12:
                    coin = 30000;
                    tv.setText(String.valueOf(coin));
                    break;
                case 13:
                    coin = 40000;
                    tv.setText(String.valueOf(coin));
                    break;
                case 14:
                    coin = 60000;
                    tv.setText(String.valueOf(coin));
                    break;
                case 15:
                    coin = 85000;
                    tv.setText(String.valueOf(coin));
                    break;
            }
        }
    }

    // bảng navigation drawer
    public  void tablevcoin(int count){

        if(count == 1) {
            drawer.openDrawer(GravityCompat.START);
            if (lv_item.getChildAt(tien.size()-count).findViewById(R.id.bt_item).isEnabled()) {
                lv_item.getChildAt(tien.size() - count).findViewById(R.id.bt_item).setEnabled(false);
            }


        }

        else if(count > 1 && count <16) {
            drawer.openDrawer(GravityCompat.START);
            if(count == 5 | count == 10| count ==15){
                lv_item.getChildAt(tien.size()-count + 1).findViewById(R.id.bt_item).setEnabled(true);
                lv_item.getChildAt(tien.size()-count).findViewById(R.id.bt_item).setEnabled(true);
                lv_item.getChildAt(tien.size()-count).findViewById(R.id.bt_item).setBackgroundResource(R.drawable.select);
            }
            else if (lv_item.getChildAt(tien.size()-count).findViewById(R.id.bt_item).isEnabled()) {
                lv_item.getChildAt(tien.size()-count + 1).findViewById(R.id.bt_item).setEnabled(true);
                lv_item.getChildAt(tien.size()-count).findViewById(R.id.bt_item).setEnabled(false);
            }


        }


    }

    // update điểm lên firebase
    public  void updatecoin() {

        FirebaseUser u = firebaseAuth.getInstance().getCurrentUser();
        uid = u.getUid();
        name_f = u.getDisplayName();
        email_f = u.getEmail();
        photoUrl_f = u.getPhotoUrl();
        url_image_f = String.valueOf(photoUrl_f);


        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = (Map) dataSnapshot.child(uid).getValue();
                name = user.get("name");
                email = user.get("email");
                url_image = user.get("url_image");
                diem = user.get("diem");
                coin = Integer.parseInt(tv_coin.getText().toString());
                // tính tiền thuởng khi dừng cuộc chơi và trả lời đúng hết 15 câu hỏi
                tiencu = Integer.parseInt(diem);
                coin_new = tiencu + coin;
                users = new Users(name_f, email_f, url_image_f, String.valueOf(coin_new));
                mDatabase.child("Users").child(uid).setValue(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    // update điểm lên firebase khi chọn sai câu hỏi và hết thời gian
    public  void updatecoin_fail() {

        FirebaseUser u = firebaseAuth.getInstance().getCurrentUser();
        uid = u.getUid();
        name_f = u.getDisplayName();
        email_f = u.getEmail();
        photoUrl_f = u.getPhotoUrl();
        url_image_f = String.valueOf(photoUrl_f);


        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = (Map) dataSnapshot.child(uid).getValue();
                name = user.get("name");
                email = user.get("email");
                url_image = user.get("url_image");
                diem = user.get("diem");
                finalcoin(tv_coin, count);
                coin = Integer.parseInt(tv_coin.getText().toString());
                tiencu = Integer.parseInt(diem);
                coin_new = tiencu + coin;
                users = new Users(name_f, email_f, url_image_f, String.valueOf(coin_new));
                mDatabase.child("Users").child(uid).setValue(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showDemNguoc() {
        if(!drawer.isDrawerOpen(GravityCompat.START)) {
            time = 30;
            mDemnguocHandler.removeCallbacks(mDemnguocRun);
            mDemnguocHandler.postDelayed(mDemnguocRun, 1000);
        }
    }

    private class DemNguocRunnable implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            handleDemnguoc();
        }

    }

    private void handleDemnguoc() {
        time--;
        if (time == 0) {
            isRunning = false;
            mediaPlayer.stop();
            playSound(R.raw.out_of_time);
            switch (trueCase) {
                case R.id.btn_a:
                    btn_dapanA.setEnabled(false);
                    btn_dapanA.setBackgroundResource(R.drawable.answer_true);
                    playSound(R.raw.lose_a);
                    anim(R.id.btn_a);
                    break;
                case R.id.btn_b:
                    btn_dapanB.setEnabled(false);
                    btn_dapanB.setBackgroundResource(R.drawable.answer_true);
                    playSound(R.raw.lose_b);
                    anim(R.id.btn_b);
                    break;
                case R.id.btn_c:
                    btn_dapanC.setEnabled(false);
                    btn_dapanC.setBackgroundResource(R.drawable.answer_true);
                    playSound(R.raw.lose_c);
                    anim(R.id.btn_c);
                    break;
                case R.id.btn_d:
                    btn_dapanD.setEnabled(false);
                    btn_dapanD.setBackgroundResource(R.drawable.answer_true);
                    playSound(R.raw.lose_d);
                    anim(R.id.btn_d);
                    break;
            }
            try {
                Thread.sleep(2000);
                final Dialog dialog = new Dialog(PlayActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // Include dialog.xml file
                dialog.setContentView(R.layout.finish_dialog);
                // Set dialog title
                //dialog.setTitle("Thông báo");

                // set values for custom dialog components - text, image and button

                TextView text = (TextView) dialog.findViewById(R.id.textDialog);
                TextView text_coin = (TextView) dialog.findViewById(R.id.textDialog_coin);
                text.setText("Thật tiếc bạn đã thua!");
                finalcoin(text_coin, count);
                dialog.setCancelable(true);
                // dialog.setContentView(view);
                dialog.show();
                Button btnOk = (Button) dialog.findViewById(R.id.btn_ok_finish);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        updatecoin_fail();
                        goMainActivity();
                    }
                });
                tv_cauhoi.setText("");
                btn_dapanA.setText("");
                btn_dapanB.setText("");
                btn_dapanC.setText("");
                btn_dapanD.setText("");
                btn_dapanA.setBackgroundResource(R.drawable.button3);
                btn_dapanB.setBackgroundResource(R.drawable.button3);
                btn_dapanC.setBackgroundResource(R.drawable.button3);
                btn_dapanD.setBackgroundResource(R.drawable.button3);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tv_time.setText(time + "");
            mDemnguocHandler.removeCallbacks(mDemnguocRun);
            avi.hide();
        }
        //avi.setIndicator(indicator);
        // avi.show();
        else {
            isRunning = true;
            tv_time.setText(time + "");
            mDemnguocHandler.postDelayed(mDemnguocRun, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        isRunning = false;
        mDemnguocHandler.removeCallbacks(mDemnguocRun);
        mediaPlayer.stop();
    }

    public void thoigian() {
        showDemNguoc();
    }

    // dừng cuộc chơi
    public  void stop(){
        avi.hide();
        mediaPlayer.stop();
        try {
            Thread.sleep(2000);
            final Dialog dialog = new Dialog(PlayActivity.this, R.style.cust_dialog);
            //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            // Include dialog.xml file
            dialog.setContentView(R.layout.finish_dialog);
            // Set dialog title
            dialog.setTitle("Thông báo");
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);
            // set values for custom dialog components - text, image and button

            TextView text = (TextView) dialog.findViewById(R.id.textDialog);
            TextView text_coin = (TextView) dialog.findViewById(R.id.textDialog_coin);
            String tien = tv_coin.getText().toString();
            text.setText("Bạn đã dừng cuộc chơi. Cảm ơn bạn đã tham gia chương trình");
            text_coin.setText(tien);
            dialog.setCancelable(false);
            // dialog.setContentView(view);
            dialog.show();

            Button btnOk = (Button) dialog.findViewById(R.id.btn_ok_finish);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    updatecoin();
                    goMainActivity();
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // hiển thị dialog chức năng gọi điện
    public void initDiaLogHelpCall() {
        dialog = new Dialog(this, R.style.cust_dialog);
        dialog.setTitle("Gọi điện thoại cho người thân");
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);
        dialog.setContentView(R.layout.call_dialog);
        ImageView imTroll1 = (ImageView) dialog.findViewById(R.id.help_troll1);
        TextView  tvTroll1 = (TextView)  dialog.findViewById(R.id.tv_help_troll1);
        ImageView imTroll2 = (ImageView) dialog.findViewById(R.id.help_troll2);
        TextView  tvTroll2 = (TextView)  dialog.findViewById(R.id.tv_help_troll2);
        ImageView imTroll3 = (ImageView) dialog.findViewById(R.id.help_troll3);
        TextView  tvTroll3 = (TextView)  dialog.findViewById(R.id.tv_help_troll3);
        ImageView imTroll4 = (ImageView) dialog.findViewById(R.id.help_troll4);
        TextView  tvTroll4 = (TextView)  dialog.findViewById(R.id.tv_help_troll4);

        tvTroll1.setText(four_peoples.get(0).name);
        tvTroll2.setText(four_peoples.get(1).name);
        tvTroll3.setText(four_peoples.get(2).name);
        tvTroll4.setText(four_peoples.get(3).name);

        imTroll1.setOnClickListener(this);
        imTroll2.setOnClickListener(this);
        imTroll3.setOnClickListener(this);
        imTroll4.setOnClickListener(this);
        dialog.setCancelable(true);

        dialog.show();
    }

    // hiển thị dialog khi chức năng hỏi ý kiến
    public void initDialogAudience() {
        final Dialog dialog = new Dialog(this, R.style.cust_dialog);
        dialog.setTitle("Hỏi ý kiến khán giả");
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);
        dialog.setContentView(R.layout.ykien_dialog);
        //LayoutInflater layoutInflater = LayoutInflater.from(this);
       // View view = layoutInflater.inflate(R.layout.ykien_dialog, null);

        BarChart mBarChart;
        Button btnOk = (Button) dialog.findViewById(R.id.cancelButton);
        Random random = new Random();
        int rd1True = random.nextInt(10) + 40;
        int rdFalse1 = random.nextInt(10) + 5;
        int rdFalse2 = random.nextInt(10) + 5;
        int rdFalse3 = 100 - rd1True - rdFalse1 - rdFalse2;


        // nếu chọn 50-50 trước và sau đó chọn hỏi ý kiến khán giả
        if (iv_5050.isEnabled()==false && trueCase == R.id.btn_a && num50 == count) { // nếu đáp án đúng là A
            mBarChart = (BarChart) dialog.findViewById(R.id.barchart);
            rd1True = random.nextInt(10) + 50;
            rdFalse3 = 100 - rd1True;
            mBarChart.addBar(new BarModel("A",rd1True, 0xFF563456));
            mBarChart.addBar(new BarModel("B",0,  0xFF563456));
            mBarChart.addBar(new BarModel("C",0, 0xFF563456));
            mBarChart.addBar(new BarModel("D",rdFalse3, 0xFF563456));
            mBarChart.startAnimation();

        } else if (iv_5050.isEnabled()==false && trueCase == R.id.btn_b && num50 == count) {  // nếu đáp án đúng là B
            mBarChart = (BarChart) dialog.findViewById(R.id.barchart);
            rd1True = random.nextInt(10) + 50;
            rdFalse2 = 100 - rd1True;
            mBarChart.addBar(new BarModel("A",0, 0xFF563456));
            mBarChart.addBar(new BarModel("B",rd1True,  0xFF563456));
            mBarChart.addBar(new BarModel("C",rdFalse2, 0xFF563456));
            mBarChart.addBar(new BarModel("D",0, 0xFF563456));
            mBarChart.startAnimation();
        } else if (iv_5050.isEnabled()==false && trueCase == R.id.btn_c && num50 == count) {  // nếu đáp án đúng là C
            mBarChart = (BarChart) dialog.findViewById(R.id.barchart);
            rd1True = random.nextInt(10) + 50;
            rdFalse1 = 100 - rd1True;
            mBarChart.addBar(new BarModel("A",rdFalse1, 0xFF563456));
            mBarChart.addBar(new BarModel("B",0,  0xFF563456));
            mBarChart.addBar(new BarModel("C",rd1True, 0xFF563456));
            mBarChart.addBar(new BarModel("D",0, 0xFF563456));
            mBarChart.startAnimation();

        } else if (iv_5050.isEnabled()==false && trueCase == R.id.btn_d && num50 == count) {  // nếu đáp án đúng là D
            mBarChart = (BarChart) dialog.findViewById(R.id.barchart);
            rd1True = random.nextInt(10) + 50;
            rdFalse2 = 100 - rd1True;
            mBarChart.addBar(new BarModel("A",0, 0xFF563456));
            mBarChart.addBar(new BarModel("B",rdFalse2,  0xFF563456));
            mBarChart.addBar(new BarModel("C",0, 0xFF563456));
            mBarChart.addBar(new BarModel("D",rd1True, 0xFF563456));
            mBarChart.startAnimation();

        }

        // trước khi chọn trợ giúp 50-50 và chọn 50-50 với ý kiến ko cùng 1 câu
        else if (trueCase == R.id.btn_a) {                                  // nếu đáp án đúng là A
            mBarChart = (BarChart) dialog.findViewById(R.id.barchart);
            mBarChart.addBar(new BarModel("A",rd1True, 0xFF563456));
            mBarChart.addBar(new BarModel("B",rdFalse1,  0xFF563456));
            mBarChart.addBar(new BarModel("C",rdFalse2, 0xFF563456));
            mBarChart.addBar(new BarModel("D",rdFalse3, 0xFF563456));
            playSound(R.raw.bg_audience);
            try{
                Thread.sleep(6000);
                mBarChart.startAnimation();
            }catch (Exception e){

            }

        } else if (trueCase == R.id.btn_b) {                                // nếu đáp án đúng là B
            mBarChart = (BarChart) dialog.findViewById(R.id.barchart);
            mBarChart.addBar(new BarModel("A",rdFalse1, 0xFF563456));
            mBarChart.addBar(new BarModel("B",rd1True,  0xFF563456));
            mBarChart.addBar(new BarModel("C",rdFalse2, 0xFF563456));
            mBarChart.addBar(new BarModel("D",rdFalse3, 0xFF563456));
            playSound(R.raw.bg_audience);
            try{
                Thread.sleep(6000);
                mBarChart.startAnimation();
            }catch (Exception e){

            }

        } else if (trueCase == R.id.btn_c) {                                   // nếu đáp án đúng là C
            mBarChart = (BarChart) dialog.findViewById(R.id.barchart);
            mBarChart.addBar(new BarModel("A",rdFalse1, 0xFF563456));
            mBarChart.addBar(new BarModel("B",rdFalse2,  0xFF563456));
            mBarChart.addBar(new BarModel("C",rd1True, 0xFF563456));
            mBarChart.addBar(new BarModel("D",rdFalse3, 0xFF563456));
            playSound(R.raw.bg_audience);
            try{
                Thread.sleep(6000);
                mBarChart.startAnimation();
            }catch (Exception e){

            }

        } else if (trueCase == R.id.btn_d) {                                    // nếu đáp án đúng là D
            mBarChart = (BarChart) dialog.findViewById(R.id.barchart);
            mBarChart.addBar(new BarModel("A",rdFalse1, 0xFF563456));
            mBarChart.addBar(new BarModel("B",rdFalse2,  0xFF563456));
            mBarChart.addBar(new BarModel("C",rdFalse3, 0xFF563456));
            mBarChart.addBar(new BarModel("D",rd1True, 0xFF563456));
            playSound(R.raw.bg_audience);
            try{
                Thread.sleep(6000);
                mBarChart.startAnimation();
            }catch (Exception e){

            }

        }


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mDemnguocRun.run();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    // enable các button
    public void action(){
        btn_dapanA.setEnabled(true);
        btn_dapanB.setEnabled(true);
        btn_dapanC.setEnabled(true);
        btn_dapanD.setEnabled(true);
    }

    // animation khi đáp án đúng
    public void anim(int v) {
        YoYo.with(Techniques.Flash).duration(550).playOn(findViewById(v));
    }

    // hàm play âm thanh
    public void playSound(int type) {
        mediaPlayer = MediaPlayer.create(this, type);
        mediaPlayer.start();
    }
    public void playSoundLoop(int type) {
        mediaPlayer = MediaPlayer.create(this, type);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    /*public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Play Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }*/

    // trở vê MainActivity
    public void goMainActivity(){
        mediaPlayer.stop();
        finish();
        try
        {
            ActivityAnimator anim = new ActivityAnimator();
            anim.unzoomAnimation(PlayActivity.this);
        }
        catch (Exception e){

        }
    }

    /* mở dialog invite bạn bè
    public void openDialogInvite(final Activity activity) {
        String AppURl = "https://fb.me/974844702646752";  //Generated from //fb developers

        String previewImageUrl = "https://lh3.googleusercontent.com/VPiu_IDXFdN2FDbmDLQINLmUq92lYzqNgQ1XroCtOz8BUpke0puAruww7r9GoUzJ_A=w300";

        sCallbackManager = CallbackManager.Factory.create();

        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(AppURl).setPreviewImageUrl(previewImageUrl)
                    .build();

            AppInviteDialog appInviteDialog = new AppInviteDialog(activity);
            appInviteDialog.registerCallback(sCallbackManager,
                    new FacebookCallback<AppInviteDialog.Result>() {
                        @Override
                        public void onSuccess(AppInviteDialog.Result result) {
                            Log.d("Invitation", "Invitation Sent Successfully");
                            finish();
                        }

                        @Override
                        public void onCancel() {
                        }

                        @Override
                        public void onError(FacebookException e) {
                            Log.d("Invitation", "Error Occured");
                        }
                    });

            appInviteDialog.show(content);
        }

    }*/

    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            goMainActivity();
            super.onBackPressed();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // client.connect();

    }

    @Override
    protected void onResume() {
        if(isRunning == true){
            mDemnguocRun.run();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        mDemnguocHandler.removeCallbacks(mDemnguocRun);
        super.onPause();
    }

    @Override
    public void onStop() {
        mediaPlayer.stop();
        super.onStop();

    }



}
