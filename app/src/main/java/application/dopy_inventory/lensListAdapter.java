package application.dopy_inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class lensListAdapter extends ArrayAdapter<getItem> {
    private int resourceLayout;
    private Context mContext;

    public lensListAdapter(Context context, int resource, List<getItem> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        getItem p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.typeTxt);
            TextView tt2 = (TextView) v.findViewById(R.id.coverTxt);

            if (tt1 != null) {
                tt1.setText(p.getLensName());
            }

            if (tt2 != null) {
                tt2.setText(p.getCover());
            }
        }

        return v;
    }

}
