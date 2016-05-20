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
 * Created by hsianghua on 16/5/20.
 */
public class ItemAdapter extends ArrayAdapter<Item> {


    // 畫面資源編號
    private int resource;
    // 包裝的記事資料
    private List<Item> items;

    public ItemAdapter(Context context,int resource, List<Item> items) {
        super(context,resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        // 讀取目前位置的記事物件
        final Item item = getItem(position);

        if (convertView == null) {
            // 建立項目畫面元件
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li = (LayoutInflater)
                    getContext().getSystemService(inflater);
            li.inflate(resource, itemView, true);
        }
        else {
            itemView = (LinearLayout) convertView;
        }

        TextView idView = (TextView) itemView.findViewById(R.id.test_id);
        TextView nameView = (TextView) itemView.findViewById(R.id.test_name);
        TextView passView = (TextView) itemView.findViewById(R.id.test_pass);

        idView.setText(Integer.toString(item.getId()));
        nameView.setText(item.getName());
        passView.setText(item.getPass());

        return itemView;
    }

}
