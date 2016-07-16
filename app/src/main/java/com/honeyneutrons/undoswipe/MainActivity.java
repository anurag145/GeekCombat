package com.honeyneutrons.undoswipe;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.TextView;

import com.honeyneutrons.undoswipe.helper.SimpleItemTouchHelperCallback;


import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;




public class MainActivity extends AppCompatActivity implements ItemAdapter.OnStartDragListener{

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "uc8jbK2QQDuofWoU8gzMy8fIq";
    private static final String TWITTER_SECRET = "FtnkgRDIXB7MdlKOHVWeNfBoq1XSbav46eLSzI4yCLZKOv0v1z";

    public static final String KEY_USERNAME = "username";



    private ItemTouchHelper mItemTouchHelper;
    private int nu=0;
    TextView tvNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        TextView tvDate=(TextView)findViewById(R.id.tvDate);
        TextView tvDay=(TextView)findViewById(R.id.tvDay);
        tvDay.setText("Hi! "+getIntent().getStringExtra(KEY_USERNAME));
         tvNumber=(TextView)findViewById(R.id.tvNumber);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("MM.dd.yyyy", Locale.getDefault());
        assert tvDate!=null;
        assert  tvDay!=null;
        tvDate.setTypeface(Typefaces.getRobotoBlack(this));
        tvDay.setTypeface(Typefaces.getRobotoBlack(this));
        tvDate.setText( dateformat.format(c.getTime()).toUpperCase());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardList);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        final ItemAdapter itemAdapter = new ItemAdapter(getApplicationContext(),this,tvNumber);
        recyclerView.setAdapter(itemAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(itemAdapter,this);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        loadItems();



    }

    @Override
    public void onDestroy()
    {
      super.onDestroy();
        ItemAdapter.itemList.clear();

    }






    private void loadItems()
    {
        //Initial items
        for(int i=10;i>0;i--)
        {
            Item item = new Item();
            item.setItemName("item"+i);
            ItemAdapter.itemList.add(item);
        }
        tvNumber.setText(String.valueOf(ItemAdapter.itemList.size()));
    }

}
