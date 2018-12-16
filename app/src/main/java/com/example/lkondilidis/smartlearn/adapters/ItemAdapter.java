package com.example.lkondilidis.smartlearn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.User;

import java.util.List;


public class ItemAdapter extends BaseAdapter {

    Context context;
    List users;

    public ItemAdapter(Context c, List<User> users){
        context = c;
        this.users = users;
    }

    @Override
    public int getCount() {
       return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ((User) users.get(i)).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.my_list_view_detail, viewGroup, false);

        return view;
    }

    /*
    private void scaleImg(View view, int pic){
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, pic, options);

        int width = options.outWidth;
        int height = options.outHeight;
        int screenWidth = screen.getWidth();
        int screenHeight = screen.getHeight();

        if(width > screenWidth){
            int ratio = Math.round((float) width/(float) screenWidth);
            options.inSampleSize = ratio;
        }

        float relativeHeight = (float)screenHeight/20.0f;
        if(height > relativeHeight){
            int ratio = Math.round((float) height/relativeHeight);
            options.inSampleSize = ratio;
        }

        options.inJustDecodeBounds = false;

        Bitmap scaledImg = BitmapFactory.decodeResource(resources, pic, options);
        BitmapDrawable ob = new BitmapDrawable(resources, scaledImg);
        view.setBackground(ob);
    }
    */
}
