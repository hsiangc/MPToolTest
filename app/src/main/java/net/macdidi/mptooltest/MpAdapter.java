package net.macdidi.mptooltest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by macbear on 16/5/23.
 */
public class MpAdapter extends ArrayAdapter<MpItem> {
    private int mResID;
    private List<MpItem> mItems;

    public MpAdapter(Context context, int resource, List<MpItem> objects) {
        super(context, resource, objects);
        this.mItems = objects;
        this.mResID = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MpItem item = getItem(position);
        LinearLayout itemView;

        if (convertView == null)
        {
            itemView = new LinearLayout(getContext());
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            li.inflate(mResID, itemView, true);
        }
        else {
            itemView = (LinearLayout)convertView;
        }
        TextView textID = (TextView) itemView.findViewById(R.id.textIndex);
        TextView textDesc = (TextView) itemView.findViewById(R.id.textDesc);
        TextView textResult = (TextView) itemView.findViewById(R.id.textResult);
        textID.setText(Integer.toString(item.mIndex));
        textDesc.setText(item.mDesc);
        textResult.setText(item.mResult);

        return itemView;
    }

    public void setData(int index, MpItem item) {
        if (index >= 0 && index < mItems.size()) {
            mItems.set(index, item);
            notifyDataSetChanged();
        }
    }

    public MpItem getItem(int index) {
        return mItems.get(index);

    }
}
