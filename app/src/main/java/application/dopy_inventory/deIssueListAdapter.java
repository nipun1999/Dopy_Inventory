package application.dopy_inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;
import java.util.List;

public class deIssueListAdapter extends BaseAdapter {
    List<String> names;
    Context context;
    LayoutInflater inflter;
    String value;
    List<String> checkedList = new ArrayList<>();

    public deIssueListAdapter(Context context, List<String> names) {
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
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.deissuelistlayout, null);
        final CheckedTextView simpleCheckedTextView = (CheckedTextView) view.findViewById(R.id.listTxt);
        simpleCheckedTextView.setText(names.get(position));
// perform on Click Event Listener on CheckedTextView
        simpleCheckedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (simpleCheckedTextView.isChecked()) {
// set cheek mark drawable and set checked property to false
                    simpleCheckedTextView.setCheckMarkDrawable(null);
                    simpleCheckedTextView.setChecked(false);
                    checkedList.remove(simpleCheckedTextView.getText().toString());
                } else {
// set cheek mark drawable and set checked property to true
                    simpleCheckedTextView.setCheckMarkDrawable(R.drawable.checked);
                    simpleCheckedTextView.setChecked(true);
                    checkedList.add(simpleCheckedTextView.getText().toString());
                }
            }
        });
        return view;
    }

    public List<String> getList(){
        return checkedList;
    }
}
