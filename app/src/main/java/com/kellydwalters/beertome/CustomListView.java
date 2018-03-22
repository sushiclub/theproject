package com.kellydwalters.beertome;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kelly on 2018-03-19.
 */
public class CustomListView extends ArrayAdapter<String> {
    private ArrayList<String> title, abvValue, image;
    private Activity context;

    public CustomListView(Activity context, ArrayList<String> title, ArrayList<String> abvValue,  ArrayList<String> image) {
        super(context, R.layout.found_row, title);

        this.title = title;
        this.context = context;
        this.abvValue = abvValue;
        this.image = image;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.found_row, null, true);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.tvName.setText(title.get(position));
        vh.tvABV.setText(abvValue.get(position));


        // Loads the image via url into the image view
        Picasso.get().load(image.get(position)).into(vh.image);

        return convertView;
    }

    class ViewHolder {
        TextView tvName, tvABV;
        ImageView image;

        ViewHolder(View v) {
            this.tvName = v.findViewById(R.id.tvName);
            this.tvABV = v.findViewById(R.id.tvABV);
            this.image = v.findViewById(R.id.imageView);

        }
    }
}
