package tuongvong.appgame.android.milionnaire;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import tuongvong.appgame.android.milionnaire.animation.ActivityAnimator;
import tuongvong.appgame.android.milionnaire.model.Users;

public class MainActivity extends AppCompatActivity {

    private ImageView photoImageView, iv_rank, iv_level;
    private TextView nameTextView, diemTextView;
    Button btn_batdau, btn_Huongdan, btn_logout;
    FirebaseDatabase database;
    DatabaseReference User;
    String name, email, url_image, uid;
    Uri photoUrl;
    String diem;
    ArrayList<Users> u;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set cho giao diện full màn hình
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        User = database.getInstance().getReference();
        photoImageView = (ImageView) findViewById(R.id.imageView1);
        iv_rank = (ImageView) findViewById(R.id.imageView);
        iv_level = (ImageView) findViewById(R.id.rank_icon);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        diemTextView = (TextView) findViewById(R.id.diemTextView);
        btn_batdau = (Button) findViewById(R.id.btn_batDau);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_Huongdan = (Button) findViewById(R.id.btn_troGiup);
        u = new ArrayList<Users>();
        playSound(R.raw.bg_music);

        // button bắt đầu chơi
        btn_batdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
                try
                {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.unzoomAnimation(MainActivity.this);
                }
                catch (Exception e) {
                }
            }
        });

        // huớng dẫn người chơi
        btn_Huongdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this, R.style.cust_dialog);
                //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                // Include dialog.xml file
                dialog.setContentView(R.layout.intro_dialog);
                dialog.setTitle("Hướng dẫn");
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);
                TextView text = (TextView) dialog.findViewById(R.id.textIntro);
                text.setText(R.string.batdau);

                dialog.show();


            }
        });

        // button logout
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(view);
            }
        });

        iv_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                Intent intent = new Intent(MainActivity.this, RankActivity.class);
                startActivity(intent);
                try
                {
                    ActivityAnimator anim = new ActivityAnimator();
                    anim.appearTopLeftAnimation(MainActivity.this);
                }
                catch (Exception e) { Toast.makeText(MainActivity.this, "An error occured " + e.toString(), Toast.LENGTH_LONG).show(); }
            }
        });

        // lấy thông tin account người dùng
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        name = user.getDisplayName();
        email = user.getEmail();
        photoUrl = user.getPhotoUrl();
        url_image = String.valueOf(photoUrl);
        diem = "0";
        uid = user.getUid();
        layuser();

    }

    // quay về LoginActivity
    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // lấy dữ liệu của user trên firebase
    private  void layuser(){
        User.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> user_info = (Map) dataSnapshot.getValue();

                name = user_info.get("name");
                email = user_info.get("email");
                url_image = user_info.get("url_image");
                Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                photoUrl = Uri.parse(url_image);
                diem = user_info.get("diem");

                Picasso.with(MainActivity.this)
                        .load(photoUrl)
                        .into(photoImageView);
                nameTextView.setText(name);
                int diem_rank = Integer.parseInt(diem);

                if(diem_rank >= 100000){
                    iv_level.setImageResource(R.drawable.dong);
                }
                if(diem_rank >= 300000){
                    iv_level.setImageResource(R.drawable.bac);
                }
                if(diem_rank >= 500000){
                    iv_level.setImageResource(R.drawable.vang);
                }
                if(diem_rank >= 700000){
                    iv_level.setImageResource(R.drawable.platinum);
                }
                if(diem_rank >= 1000000){
                    iv_level.setImageResource(R.drawable.kimcuong);
                }

                diemTextView.setText(String.valueOf(diem));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // log out về lại màn hình đăng nhập
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }

    // hàm play âm thanh
    public void playSound(int type){
        mediaPlayer= MediaPlayer.create(this,type);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    // dừng âm thanh khi activity pause
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        playSound(R.raw.bg_music);
    }

    // dừng âm thanh khi activity destroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}
