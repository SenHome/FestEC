package com.starry.latte.ec.main.sort.list;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.ec.R;
import com.starry.latte.ec.main.sort.SortDelegate;
import com.starry.latte.ec.main.sort.content.ContentDelegate;
import com.starry.latte.ui.recycler.ItemType;
import com.starry.latte.ui.recycler.MultipleFields;
import com.starry.latte.ui.recycler.MultipleItemEntity;
import com.starry.latte.ui.recycler.MultipleRecyclerAdapter;
import com.starry.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

import me.yokeyword.fragmentation.SupportHelper;

/**
 * Created by wangsen on 2018/4/22.
 */

public class SortRecyclerViewAdapter extends MultipleRecyclerAdapter{
    private final SortDelegate DELEGATE;

    //上一个的位置
    private int mPrePosition = 0;

    protected SortRecyclerViewAdapter(List<MultipleItemEntity> data, SortDelegate delegate  ) {
        super(data);
        this.DELEGATE = delegate;
        //添加垂直菜单布局
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list);

    }


    @Override
    protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()){
            case ItemType.VERTICAL_MENU_LIST:
                final String text = entity.getField(MultipleFields.TEXT);
                final boolean isClicked = entity.getField(MultipleFields.TAG);

                final AppCompatTextView name = holder.getView(R.id.tv_vertical_item_name);
                final View line = holder.getView(R.id.view_line);
                final View itemView = holder.itemView;

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentPosition = holder.getAdapterPosition();
                        if(mPrePosition != currentPosition){
                            //还原上一个
                            getData().get(mPrePosition).setField(MultipleFields.TAG,false);
                            //更新RecyclerView
                            notifyItemChanged(mPrePosition);

                            //更新选中的Item
                            entity.setField(MultipleFields.TAG,true);
                            notifyItemChanged(currentPosition);
                            mPrePosition = currentPosition;

                            //内容Id
                            final int contentId = getData().get(currentPosition).getField(MultipleFields.ID);
                            showContent(contentId);
                        }
                    }
                });

                if(!isClicked){
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext,R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.item_background));
                }else {
                    line.setVisibility(View.VISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext,R.color.app_main));
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
                    itemView.setBackgroundColor(Color.WHITE);
                }

                holder.setText(R.id.tv_vertical_item_name,text);

                break;
            default:
                break;
        }
    }


    private void showContent(int contenId){
        final ContentDelegate delegate = ContentDelegate.newInstance(contenId);
        switchContent(delegate);
    }

    private void switchContent(ContentDelegate delegate){
        final LatteDelegate contentDelegate =
//                SupportHelper.findFragment(DELEGATE.getChildFragmentManager(),ContentDelegate.class);
                DELEGATE.findChildFragment(ContentDelegate.class);
        if(contentDelegate!= null){
            contentDelegate.replaceFragment(delegate,false);
//            contentDelegate.getSupportDelegate().replaceFragment(delegate,false);
        }
    }
}
