package application.dopy_inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.List;

public class deIssueLensListAdapterB extends BaseAdapter {

    List<getItem> names;
    Context context;
    LayoutInflater inflter;
    String value;
    List<getItem> checkedList = new ArrayList<>();

    public deIssueLensListAdapterB(Context context, List<getItem> names) {
        this.context = context;
        this.names = names;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.deissuelenslayout, null);
        final CheckedTextView lensCheckedView = (CheckedTextView) view.findViewById(R.id.lensListTxt);
        final CheckedTextView coverCheckedView = (CheckedTextView) view.findViewById(R.id.coverListTxt);
        lensCheckedView.setText(names.get(position).getLensName());
        coverCheckedView.setText(names.get(position).getCover());

        lensCheckedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lensCheckedView.isChecked()) {
// set cheek mark drawable and set checked property to false
                    lensCheckedView.setCheckMarkDrawable(null);
                    lensCheckedView.setChecked(false);
                    checkedList.remove(names.get(position));
                } else {
// set cheek mark drawable and set checked property to true
                    lensCheckedView.setCheckMarkDrawable(R.drawable.checked);
                    lensCheckedView.setChecked(true);
                    if(coverCheckedView.isChecked()){
                        checkedList.add(names.get(position));
                    }
                }

            }
        });

        coverCheckedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (coverCheckedView.isChecked()) {
// set cheek mark drawable and set checked property to false
                    coverCheckedView.setCheckMarkDrawable(null);
                    coverCheckedView.setChecked(false);
                    checkedList.remove(names.get(position));
                } else {
// set cheek mark drawable and set checked property to true
                    coverCheckedView.setCheckMarkDrawable(R.drawable.checked);
                    coverCheckedView.setChecked(true);
                    if(lensCheckedView.isChecked()){
                        checkedList.add(names.get(position));
                    }
                }

            }
        });
// perform on Click Event Listener on CheckedTextView
        return view;
    }

    public List<getItem> getList(){
        return checkedList;
    }

}
