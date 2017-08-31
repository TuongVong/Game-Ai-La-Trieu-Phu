package tuongvong.appgame.android.milionnaire.myadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import tuongvong.appgame.android.milionnaire.R;

public class ContactViewHolder extends RecyclerView.ViewHolder{

    public ImageView iv_user;

    public TextView tv_stt, tv_name;

    public CheckBox chb;

    public ContactViewHolder(View view)

    {

        super(view);
        this.tv_stt = (TextView) view.findViewById(R.id.tv_stt);

        this.iv_user = (ImageView) view.findViewById(R.id.iv_user);

        this.tv_name = (TextView) view.findViewById(R.id.tv_name);

        this.chb  = (CheckBox)  view.findViewById(R.id.chb);
    }
}
