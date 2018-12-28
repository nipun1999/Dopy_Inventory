package application.dopy_inventory;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class HomeActivity extends AppCompatActivity {

    com.github.clans.fab.FloatingActionButton storageFab;
    com.github.clans.fab.FloatingActionButton memberFab;
    private RecyclerView issueRecycler;
    private FloatingActionMenu materialDesignFAM;
    DatabaseReference issueDatabase;
    issueAdapter adapter;
    Vector<getIssue> issueVector = new Vector<>();
    ValueEventListener listener;
    ProgressDialog loading;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        issueRecycler = findViewById(R.id.issueRecycler);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        issueDatabase = FirebaseDatabase.getInstance().getReference().child("currentIssue");
        storageFab = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.storageFab);
        memberFab = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.memberFab);
        issueRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new issueAdapter(issueVector, this);
        issueRecycler.setAdapter(adapter);
        loading = new ProgressDialog(HomeActivity.this);
        loading.setCancelable(false);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Please Wait");


        storageFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inventory = new Intent(HomeActivity.this,addItemsActivity.class);
                startActivity(inventory);
            }
        });

        memberFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent member = new Intent(HomeActivity.this,issueKitActivity.class);
                startActivity(member);
            }
        });

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                issueVector.clear();

                for (final DataSnapshot issueID : dataSnapshot.getChildren()) {
                    final getIssue issue = new getIssue();

                        if(issueID.hasChild("name")&&issueID.hasChild("cameraName")&&issueID.hasChild("eventName")){
                            issue.setStudentName(issueID.child("name").getValue().toString());
                            issue.setCameraName(issueID.child("cameraName").getValue().toString());
                            issue.setEventName(issueID.child("eventName").getValue().toString());
                            issue.setIssueID(issueID.getKey());
                            issueVector.add(issue);
                        }else{
                            try{

                                issue.setStudentName(issueID.child("name").getValue().toString());
                                issue.setEventName(issueID.child("eventName").getValue().toString());
                                issue.setIssueID(issueID.getKey());
                                issue.setCameraName("Camera Returned");
                                issueVector.add(issue);


                            }catch (Exception e){

                            }

                        }
                }

                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };






    }

    @Override
    public void onStart(){
        super.onStart();
        issueDatabase.addListenerForSingleValueEvent(listener);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        issueDatabase.removeEventListener(listener);
    }
    @Override
    public void onResume(){
        super.onResume();
        issueDatabase.addValueEventListener(listener);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



}
