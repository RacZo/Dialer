/***
 * Copyright (c) 2015 Oscar Salguero www.oscarsalguero.com
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oscarsalguero.dialer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.oscarsalguero.dialer.R;

import java.util.List;
import java.util.Map;

/**
 * Spinner adapter that shows the country code selection using a flag.
 * <p/>
 * Created by RacZo on 9/28/15.
 */
public class CustomSpinnerAdapter extends SimpleAdapter {

    LayoutInflater mInflater;
    private List<? extends Map<String, ?>> data;

    public CustomSpinnerAdapter(Context context, List<? extends Map<String, ?>> data,
                                int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.spinner_view, null);
        }

        ((ImageView) convertView.findViewById(R.id.image_view_flag))
                .setImageResource(((Integer) this.data.get(position).get("flag")).intValue());
        ((TextView) convertView.findViewById(R.id.text_view_code))
                .setText((String) this.data.get(position).get("code"));

        return convertView;
    }
}