package tuongvong.appgame.android.milionnaire.myadapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import tuongvong.appgame.android.milionnaire.R;
import tuongvong.appgame.android.milionnaire.model.People;

/**
 * Created by admin on 12/29/2016.
 */

public class ListContactAdapter extends RecyclerView.Adapter<ContactViewHolder> implements FastScrollRecyclerView.SectionedAdapter{

    ArrayList<People> ds;
    public ArrayList<People> list = new ArrayList<People>();
    People people;
    Context context;
    int numberOfCheckboxesChecked = 0;

    public ListContactAdapter(Context context, ArrayList<People> ds)

    {

        this.ds=ds;

        this.context=context;

    }

    @Override

    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.activity_contact_view_holder,parent,false);
        return new ContactViewHolder(v);

    }

    @Override

    public void onBindViewHolder(final ContactViewHolder holder, final int position) {

        final int index = position + 1;

        for(int i=0; i<ds.size(); i++) {
            for (int j = i + 1; j < ds.size(); j++) {
                int num1 = Integer.parseInt(ds.get(i).id);
                int num2 = Integer.parseInt(ds.get(j).id);
                if (num1 == num2) {
                    people = ds.get(i);
                    ds.remove(j);
                    ds.set(i, people);
                }
                Collections.sort(ds,new Comparator<People>() {
                    @Override
                    public int compare(People a, People b) {
                        return b.name.compareTo(a.name);
                    }
                });
                people = ds.get(position);
                holder.tv_stt.setText(String.valueOf(index));

                holder.tv_name.setText(people.name);

                holder.iv_user.setBackgroundResource(R.drawable.default_user);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "Clicked on row: " + index, Toast.LENGTH_SHORT).show();
                }
            });

        }

        holder.chb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked && numberOfCheckboxesChecked < 2) {
                    numberOfCheckboxesChecked++;
                    people = ds.get(position);
                    list.add(people);
                }

                else if (isChecked == false){
                    numberOfCheckboxesChecked--;
                    people = ds.get(position);
                    for(int i=0; i<list.size(); i++){
                        if(Integer.parseInt(people.id) == Integer.parseInt(list.get(i).id)) {
                            list.remove(i);
                        }
                    }



                }
            }
        });


    }


    @Override
    public String getSectionName(int position) {
        char first = ds.get(position).name.charAt(0);
        return String.valueOf(first).toUpperCase();
    }

    @Override

    public int getItemCount() {

        return ds.size();

    }




}
