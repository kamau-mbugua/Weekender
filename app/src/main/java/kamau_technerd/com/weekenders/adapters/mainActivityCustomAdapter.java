package kamau_technerd.com.weekenders.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kamau_technerd.com.weekenders.R;
import kamau_technerd.com.weekenders.activities.MainActivity;
import kamau_technerd.com.weekenders.activities.specificEvent;
import kamau_technerd.com.weekenders.activities.whole;

public class mainActivityCustomAdapter extends ArrayAdapter<mainactivityListRow> {
    private Context context;
    private List<mainactivityListRow> List = new ArrayList<>();


    public mainActivityCustomAdapter(Context context, List<mainactivityListRow> List) {
        super(context, 0, List);
        this.context = context;
        this.List = List;
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
    public mainactivityListRow getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.homelistview, parent, false);

        mainactivityListRow currentlist = List.get(position);
        final String[] imageid = currentlist.getImageid();
        ImageView imageView1 = listItem.findViewById(R.id.image1);
        Picasso.with(context).load(imageid[0]).fit().into(imageView1);

        ImageView imageView2 = listItem.findViewById(R.id.image2);
        Picasso.with(context).load(imageid[1]).fit().into(imageView2);

        ImageView imageView3 = listItem.findViewById(R.id.image3);
        Picasso.with(context).load(imageid[2]).fit().into(imageView3);

        ImageView imageView4 = listItem.findViewById(R.id.image4);
        Picasso.with(context).load(imageid[3]).fit().into(imageView4);

        ImageView imageView5 = listItem.findViewById(R.id.image5);
        Picasso.with(context).load(imageid[4]).fit().into(imageView5);

        ImageView imageView6 = listItem.findViewById(R.id.image6);
        Picasso.with(context).load(imageid[5]).fit().into(imageView6);

        Button title = listItem.findViewById(R.id.category);
        title.setText(currentlist.getTitle());

        title.setTag(new Integer(position));
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,whole.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                context.startActivity(i);
            }
        });

        Button more = listItem.findViewById(R.id.more);
        more.setTag(new Integer(position));
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, whole.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                context.startActivity(i);
            }
        });


        imageView1.setTag(new Integer(position));
        imageView1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, specificEvent.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                i.putExtra("list",imageid[0]);
                context.startActivity(i);
            }
        });

        imageView2.setTag(new Integer(position));
        imageView2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,specificEvent.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                i.putExtra("list",imageid[1]);
                context.startActivity(i);

            }
        });

        imageView3.setTag(new Integer(position));
        imageView3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,specificEvent.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                i.putExtra("list",imageid[2]);
                context.startActivity(i);

            }
        });

        imageView4.setTag(new Integer(position));
        imageView4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,specificEvent.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                i.putExtra("list",imageid[3]);
                context.startActivity(i);
            }
        });

        imageView5.setTag(new Integer(position));
        imageView5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,specificEvent.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                i.putExtra("list",imageid[4]);
                context.startActivity(i);
            }
        });

        imageView6.setTag(new Integer(position));
        imageView6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,specificEvent.class);
                String str=view.getTag().toString();
                int row=Integer.valueOf(str);
                i.putExtra("row", row);
                i.putExtra("list",imageid[5]);
                context.startActivity(i);
            }
        });


        return listItem;}
}
