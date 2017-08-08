package com.example.francisco.homeworkcontacts;

/**
 * Created by FRANCISCO on 07/08/2017.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final byte[][] imgid;
    private final String id[];
    private final String phone[];

    public CustomListAdapter(Activity context, String[] itemname, byte[][] imgid, String id[], String phone[]) {
        super(context, R.layout.mylist, itemname);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.itemname = itemname;
        this.imgid = imgid;
        this.id = id;
        this.phone = phone;
    }

    public View getView(int position, View view, final ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.mylist, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
        try {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgid[position], 0, imgid[position].length);
            bitmap = getResizedBitmap(bitmap, 60, 60);
            imageView.setImageBitmap(bitmap);
        }catch(Exception ex){}
        extratxt.setText(phone[position]);


//        ImageButton btnclose = (ImageButton) rowView.findViewById(R.id.btnclose);
//
//        btnclose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ViewGroup parentView = (ViewGroup) view.getParent();
//                parentView.removeView(view);
//            }
//        });
        return rowView;

    }

    ;

    public Bitmap getResizedBitmap(Bitmap image, int bitmapWidth, int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true);
    }
}
