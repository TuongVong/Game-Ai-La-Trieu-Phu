package tuongvong.appgame.android.milionnaire.myadapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tuongvong.appgame.android.milionnaire.R;
import tuongvong.appgame.android.milionnaire.model.Users;

/**
 * Created by phamtiendat on 12/1/16.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        ArrayList<Users> ds;

        Context context;

        public MyAdapter(Context context, ArrayList<Users> ds)

                {

                this.ds=ds;

                this.context=context;

                }

        @Override

        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View v= LayoutInflater.from(context).inflate(R.layout.view_holder_layout,parent,false);

                return new MyViewHolder(v);

                }

        @Override

        public void onBindViewHolder(MyViewHolder holder, int position) {

            Users m;
            // sắp xếp arraylist theo thứ tự điểm giảm dần
            for(int i=0; i<ds.size()-1; i++) {
                for (int j = i + 1; j < ds.size(); j++) {
                    int num1 = Integer.parseInt(ds.get(i).diem);
                    int num2 = Integer.parseInt(ds.get(j).diem);
                    if (num1 < num2) {
                        m = ds.get(i);
                        ds.set(i, ds.get(j));
                        ds.set(j, m);
                    }
                    if (position < 11) {

                        Users users = ds.get(position);
                        Uri url = Uri.parse(users.url_image);

                        holder.tv_rank.setText(String.valueOf(position + 1));

                        Picasso.with(context)
                                .load(url)
                                .into(holder.iv_user);

                        holder.tv_name.setText(users.name);

                        int diem = Integer.parseInt(users.diem);

                        // phân cấp level cho người dùng
                        if (diem >= 100000) {
                            holder.iv_rank.setImageResource(R.drawable.dong);
                        }
                        if (diem >= 500000) {
                            holder.iv_rank.setImageResource(R.drawable.bac);
                        }
                        if (diem >= 700000) {
                            holder.iv_rank.setImageResource(R.drawable.vang);
                        }
                        if (diem >= 1000000) {
                            holder.iv_rank.setImageResource(R.drawable.platinum);
                        }
                        if (diem >= 3000000) {
                            holder.iv_rank.setImageResource(R.drawable.kimcuong);
                        }

                        holder.tv_diem.setText(users.diem);
                    }
                }

            }

                }

        @Override

        public int getItemCount() {

                return ds.size();

                }


}
