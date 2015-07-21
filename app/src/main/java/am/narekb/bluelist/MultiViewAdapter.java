package am.narekb.bluelist;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MultiViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<Ad> ads;
    private int itemLayout;
    private Bitmap bm;

    private class ViewHolder {
        TextView name;
        TextView price;
        //ImageView image;
    }

    public MultiViewAdapter(Context context, ArrayList<Ad> objects, int resource) {
        inflater = LayoutInflater.from(context);
        ads = objects;
        itemLayout = resource;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(itemLayout, null);
            holder.name = (TextView) convertView.findViewById(R.id.adTitle);
            holder.price = (TextView) convertView.findViewById(R.id.adPrice);
            //holder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(ads.get(position).getName());
        holder.price.setText(ads.get(position).getPrice());
        //holder.image.setImageBitmap(bm); //TODO: Use Volley or UrlImageViewHelper to get product picture
        return convertView;

    }
}
