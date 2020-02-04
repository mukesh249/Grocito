package com.grocito.grocito.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import com.bumptech.glide.Glide;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.model.CategoryModelE;
import com.grocito.grocito.model.SubCategoryModelE;
import com.grocito.grocito.R;

public class ExpandableListCategoryAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<CategoryModelE> listDataHeader;
    private HashMap<CategoryModelE, List<SubCategoryModelE>> listDataChild;

    public ExpandableListCategoryAdapter(Context context, List<CategoryModelE> listDataHeader,
                                         HashMap<CategoryModelE, List<SubCategoryModelE>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public SubCategoryModelE getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).getMenuName();
        final String image_icon = getChild(groupPosition, childPosition).getImage_icon();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_submenu, null);
        }

        TextView txtListChild = convertView.findViewById(R.id.submenu);
        ImageView imageView = convertView.findViewById(R.id.iconimage);

        Glide.with(context).load(WebUrls.BASE_URL + WebUrls.Catgory_Image_URL + image_icon)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(imageView);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if (this.listDataChild.get(this.listDataHeader.get(groupPosition)) == null)
            return 0;
        else
            return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
    }

    @Override
    public CategoryModelE getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).getMenuName();
        int image_icon = getGroup(groupPosition).getImage();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listheader, null);
        }

        TextView lblListHeader = convertView.findViewById(R.id.submenu);
        ImageView imageView = convertView.findViewById(R.id.iconimage);
        ImageView side_menu_dropicon = convertView.findViewById(R.id.endicon);
        if (headerTitle.compareTo(context.getResources().getString(R.string.my_account))==0){
            side_menu_dropicon.setVisibility(View.GONE);
        }else {
            side_menu_dropicon.setVisibility(View.GONE);
        }
        Log.d("cate_image_icon", WebUrls.BASE_URL + WebUrls.Catgory_Image_URL + listDataHeader.get(groupPosition).getIcon());
        Glide.with(context).load(WebUrls.BASE_URL + WebUrls.Catgory_Image_URL + listDataHeader.get(groupPosition).getIcon())
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(imageView);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}