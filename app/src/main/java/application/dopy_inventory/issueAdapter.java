package application.dopy_inventory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Vector;

public class issueAdapter extends RecyclerView.Adapter<issueAdapter.MyViewHolder> {

    Context context;
    private Vector<getIssue> issueVector;
    ProgressDialog loading;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nametxt, cameraTxt, eventtxt;




        public MyViewHolder(View view) {
            super(view);
            nametxt = (TextView) view.findViewById(R.id.nametxt);
            cameraTxt = (TextView) view.findViewById(R.id.cameratxt);
            eventtxt = (TextView) view.findViewById(R.id.eventtxt);
        }
    }

    public issueAdapter(Vector<getIssue> issue, Context context) {
        this.issueVector = issue;
        this.context = context;
    }


    @Override
    public issueAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View billview = inflater.inflate(R.layout.currentissuelayout, parent, false);
        return new issueAdapter.MyViewHolder(billview);

    }

    @Override
    public void onBindViewHolder(final issueAdapter.MyViewHolder holder, final int position) {
        loading = new ProgressDialog(context);
        loading.setCancelable(false);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Please Wait");

        holder.cameraTxt.setText(issueVector.get(position).getCameraName());
        holder.eventtxt.setText(issueVector.get(position).getEventName());
        holder.nametxt.setText(issueVector.get(position).getStudentName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent issue = new Intent(context,deIssueKit.class);
                issue.putExtra("issueID",issueVector.get(position).getIssueID());
                context.startActivity(issue);
            }
        });

    }

    @Override
    public int getItemCount() {
        return issueVector.size();
    }




}
