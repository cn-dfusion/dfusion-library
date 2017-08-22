/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package cn.dfusion.mylibrary.ui;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

import cn.dfusion.mylibrary.R;
import cn.dfusion.mylibrary.base.BaseAdapter;
import cn.dfusion.mylibrary.model.Entry;
import cn.dfusion.mylibrary.util.StringUtil;

/**
 * 网格选择器adapter
 * <p>
 * 使用 new GridPickerAdapter(...); 具体参考.DemoAdapter
 */
class GridPickerAdapter extends BaseAdapter<Entry<Integer, String>> {

    static final int TYPE_CONTNET_ENABLE = 0;
    static final int TYPE_TITLE = 2;

    private OnItemSelectedListener onItemSelectedListener;

    void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    private int currentPosition;//初始选中位置
    private int height;//item高度

    GridPickerAdapter(Activity context, int currentPosition, int height) {
        super(context);
        this.currentPosition = currentPosition;
        this.height = height;
    }

    private int getCurrentPosition() {
        return currentPosition;
    }

    String getCurrentItemName() {
        return StringUtil.getTrimedString(getItem(getCurrentPosition()).getValue());
    }


    //getView的常规写法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    private LayoutParams layoutParams;

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = convertView == null ? null : (ViewHolder) convertView.getTag();
        if (holder == null) {
            convertView = inflater.inflate(R.layout.grid_picker_item, parent, false);
            holder = new ViewHolder();

            holder.tv = convertView.findViewById(R.id.tvGridPickerItem);

            convertView.setTag(holder);
        }

        final Entry<Integer, String> data = getItem(position);
        final int type = data.getKey();

        holder.tv.setText(StringUtil.getTrimedString(data.getValue()));
        holder.tv.setTextColor(resources.getColor(type == TYPE_CONTNET_ENABLE ? R.color.black : R.color.gray_2));
        holder.tv.setBackgroundResource(position == currentPosition
                ? R.drawable.round_green : R.drawable.null_drawable);

        convertView.setBackgroundResource(type == TYPE_TITLE ? R.color.alpha_1 : R.color.alpha_complete);

        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == TYPE_CONTNET_ENABLE) {
                    currentPosition = position;
                    if (onItemSelectedListener != null) {
                        onItemSelectedListener.onItemSelected(null, v, position, getItemId(position));
                    }
                    notifyDataSetChanged();
                }
            }
        });

        if (height > 0) {
            if (layoutParams == null || layoutParams.height != height) {
                layoutParams = convertView.getLayoutParams();
                layoutParams.height = height;
            }
            convertView.setLayoutParams(layoutParams);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tv;
    }
    //getView的常规写法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
