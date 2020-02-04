package com.grocito.grocito.activities;

import android.content.Context;
import android.content.Intent;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.grocito.grocito.R;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;
import com.grocito.grocito.utils.DataObject;

public class IntroActivity extends AppCompatActivity {


//    @BindView(R.id.skip)
//    TextView Skip;
    @BindView(R.id.next)
    TextView Next;

    @BindView(R.id.view_pager)
    ViewPager vp;

    @BindView(R.id.dots_indicator)
    WormDotsIndicator dotsIndicator;

    CustomPageAdapter myvpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ButterKnife.bind(this,this);


        SharedPrefManager.setIntro(Constrants.IsIntro,true);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Next.getText().toString().compareTo(getResources().getString(R.string.let_s_start))==0){
                    Intent intent = new Intent(IntroActivity.this, Login.class);
                    startActivity(intent);
                }else{
                    vp.setCurrentItem(myvpAdapter.getItem(+1), true);
                }
            }
        });
        vp.addOnPageChangeListener(viewPagerPageChangeListener);

        List<DataObject> getData = dataSource();
        myvpAdapter = new CustomPageAdapter(IntroActivity.this,getData);
        vp.setAdapter(myvpAdapter);
        dotsIndicator.setViewPager(vp);

    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            nextButtonBehaviour(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    @SuppressWarnings("PointlessBooleanExpression")
    private void nextButtonBehaviour(final int position) {
//        boolean hasPermissionToGrant = fragment.hasNeededPermissionsToGrant();
        if (myvpAdapter.isLastSlide(position)) {
            Next.setText(getResources().getString(R.string.let_s_start));
        } else {
            Next.setText(getResources().getString(R.string.next));
            Next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Next.getText().toString().compareTo(getResources().getString(R.string.let_s_start))==0){
                        Intent intent = new Intent(IntroActivity.this, Login.class);
                        startActivity(intent);
                    }else{
                        vp.setCurrentItem(myvpAdapter.getItem(+1), true);
                    }
                }
            });
        }
    }

    private List<DataObject> dataSource(){
        List<DataObject> data = new ArrayList<DataObject>();
        data.add(new DataObject(R.drawable.intro1, getResources().getString(R.string.supermarket_in_n_your_mobile)));
        data.add(new DataObject(R.drawable.intro2, getResources().getString(R.string.grocito_provided_door_step_delivery)));
        data.add(new DataObject(R.drawable.intro3, getResources().getString(R.string.easy_payment_option_to_pay)));
        return data;
    }
    public class CustomPageAdapter extends PagerAdapter {
        private Context context;
        private List<DataObject> dataObjectList;
        private LayoutInflater layoutInflater;
        public CustomPageAdapter(Context context, List<DataObject> dataObjectList){
            this.context = context;
            this.layoutInflater = (LayoutInflater)this.context.getSystemService(this.context.LAYOUT_INFLATER_SERVICE);
            this.dataObjectList = dataObjectList;
        }

        private int getItem(int i) {
            return vp.getCurrentItem() + i;
        }
        @Override
        public int getCount() {
            return dataObjectList.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View)object);
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = this.layoutInflater.inflate(R.layout.intro1, container, false);
            ImageView displayImage = (ImageView)view.findViewById(R.id.imageView);
            TextView imageText = (TextView)view.findViewById(R.id.textView1);
            displayImage.setImageResource(this.dataObjectList.get(position).getImageId());
            imageText.setText(this.dataObjectList.get(position).getImageName());
            container.addView(view);
            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
        public boolean isLastSlide(int position) {
            return position == getCount() - 1;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
