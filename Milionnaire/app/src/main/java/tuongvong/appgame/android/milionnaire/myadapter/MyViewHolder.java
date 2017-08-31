package tuongvong.appgame.android.milionnaire.myadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tuongvong.appgame.android.milionnaire.R;

/**
 * Created by phamtiendat on 12/1/16.
 */

public class MyViewHolder extends RecyclerView.ViewHolder{

    public ImageView iv_user, iv_rank;

    public TextView tv_rank, tv_name, tv_diem;

    public MyViewHolder(View view)

    {

        super(view);
        this.tv_rank = (TextView) view.findViewById(R.id.tv_rank);

        this.iv_user = (ImageView) view.findViewById(R.id.iv_user_rank);

        this.tv_name = (TextView) view.findViewById(R.id.tv_name_rank);

        this.iv_rank = (ImageView) view.findViewById(R.id.rank_icon);

        this.tv_diem = (TextView) view.findViewById(R.id.tv_diem_rank);
    }
}
