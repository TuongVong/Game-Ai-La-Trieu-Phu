package tuongvong.appgame.android.milionnaire;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import tuongvong.appgame.android.milionnaire.animation.ActivityAnimator;
import tuongvong.appgame.android.milionnaire.model.Users;
import tuongvong.appgame.android.milionnaire.myadapter.MyAdapter;

public class RankActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Users> user;
    FirebaseDatabase database;
    DatabaseReference User;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rank);

        User = database.getInstance().getReference();
        recyclerView = (RecyclerView)  findViewById(R.id.recycler_view);
        user = new ArrayList<Users>();

        adapter=new MyAdapter(RankActivity.this,user);

        LinearLayoutManager lmanager=new LinearLayoutManager(RankActivity.this);
        lmanager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lmanager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);

        // lấy dữ liệu người dùng từ firebase đổ lên adapter
        User.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //user.clear();
                for(DataSnapshot tt:dataSnapshot.getChildren())
                {
                    Users c=tt.getValue(Users.class);
                    c.id=tt.getKey();//dua id vào đối tượng User
                    user.add(c);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try
        {
            ActivityAnimator anim = new ActivityAnimator();
            anim.appearTopLeftAnimation(RankActivity.this);
        }
        catch (Exception e) { Toast.makeText(RankActivity.this, "An error occured " + e.toString(), Toast.LENGTH_LONG).show(); }

    }
}
