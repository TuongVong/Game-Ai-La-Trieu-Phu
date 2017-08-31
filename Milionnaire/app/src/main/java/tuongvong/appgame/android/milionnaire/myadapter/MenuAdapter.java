package tuongvong.appgame.android.milionnaire.myadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

import tuongvong.appgame.android.milionnaire.R;

/**
 * Created by phamtiendat on 12/7/16.
 */

public class MenuAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> tien;

    public MenuAdapter(Context context, ArrayList<String> tien){
        this.context = context;
        this.tien = tien;
    }

    public static class View_Mot_O

    {



        public Button bt;

    }

    @Override
    public int getCount() {
        return tien.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View_Mot_O mot_o;

        LayoutInflater layoutinflater=

                ((Activity)context).getLayoutInflater();

        if(view==null)

        {

            mot_o = new View_Mot_O();

            view = layoutinflater.inflate(R.layout.one_item,

                    null);

            mot_o.bt = (Button)

                    view.findViewById(R.id.bt_item);


            view.setTag(mot_o);

        }

        else

            mot_o=(View_Mot_O)view.getTag();

                if(position==0 | position==5 | position==10){
                    mot_o.bt.setBackgroundResource(R.drawable.atp__dialog_confirm_background_button_active);
                    mot_o.bt.setText(tien.get(position)+"");
                }else {
                    mot_o.bt.setText(tien.get(position) + "");
                }

        return view;
    }
}
