package kamau_technerd.com.weekenders.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kamau_technerd.com.weekenders.R;
import kamau_technerd.com.weekenders.activities.specificEvent;

public class wholeCustomAdapter extends ArrayAdapter<wholeList> {
    private Context mContext;
    private java.util.List<wholeList> List = new ArrayList<>();

    public wholeCustomAdapter(Context mContext, java.util.List<wholeList> list) {
        super(mContext,0,list);
        this.mContext = mContext;
        List = list;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public wholeList getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull final ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.wholelistview, parent, false);

        wholeList currentlist = List.get(position);
        final String imageid1 = currentlist.getImageid1();
        ImageView imageView1 = listItem.findViewById(R.id.img1);
        Picasso.with(mContext).load(imageid1).fit().into(imageView1);

        final String imageid2 = currentlist.getImageid2();
        ImageView imageView2 = listItem.findViewById(R.id.img2);
        Picasso.with(mContext).load(imageid2).fit().into(imageView2);

        imageView1.setTag(new Integer(position));
        imageView1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, specificEvent.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("activity",2);
                i.putExtra("list",imageid1);
                i.putExtra("whole",1);
                i.putExtra("row",row);
                i.putExtra("pos",0);
                mContext.startActivity(i);

            }
        });

        if((imageid2.equals("0")))
            imageView2.setEnabled(false);
        else
            imageView2.setEnabled(true);

        imageView2.setTag(new Integer(position));
        imageView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,specificEvent.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("activity",2);
                i.putExtra("list",imageid2);
                i.putExtra("whole",1);
                i.putExtra("row",row);
                i.putExtra("pos",1);
                mContext.startActivity(i);

            }
        });


        return listItem;
    }
}
