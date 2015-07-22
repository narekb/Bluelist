package am.narekb.bluelist;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MultiViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Ad> ads;
    private int itemLayout;
    private Context ctx;

    private class ViewHolder {
        TextView name;
        TextView price;
        ImageView image;
    }

    public MultiViewAdapter(Context context, ArrayList<Ad> objects, int resource) {
        inflater = LayoutInflater.from(context);
        ads = objects;
        itemLayout = resource;
        ctx = context;
    }

    @Override
    public int getCount() {
        return ads.size();
    }

    @Override
    public Object getItem(int position) {
        return ads.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(itemLayout, null);
            holder.name = (TextView) convertView.findViewById(R.id.adTitle);
            holder.price = (TextView) convertView.findViewById(R.id.adPrice);
            holder.image = (ImageView) convertView.findViewById(R.id.adImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(ctx).load(ads.get(position).getImageUrl()).placeholder(R.drawable.placeholder).fit().centerInside().into(holder.image);
        holder.name.setText(ads.get(position).getName());
        holder.price.setText(ads.get(position).getPrice());

        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent();
                browserIntent.setAction(Intent.ACTION_VIEW);
                browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                browserIntent.setData(Uri.parse(ads.get(position).getUrl()));
                ctx.startActivity(browserIntent);
            }

        });
        return convertView;

    }
}
