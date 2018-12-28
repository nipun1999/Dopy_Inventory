package application.dopy_inventory;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class deIssueKit extends AppCompatActivity {

    TextView nameTxt,eventTxt;
    CheckedTextView cameraTxt,BagTxt,othersTxt;
    private DatabaseReference issueDatabase;
    private List<String> batteryList,sdList,batteryFinalList,sdListFinal;
    private ListView batteryListView,lensListView,sdListView;
    List<getItem> lensList;
    private Button deIssueBtn;
    deIssueLensListAdapterB deIssueLensListAdapter;
    ValueEventListener deIssueListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_de_issue_kit);

        final Intent issue = getIntent();
        String id = issue.getStringExtra("issueID");
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        nameTxt = findViewById(R.id.nameTxt);
        eventTxt = findViewById(R.id.eventTxt);
        cameraTxt = findViewById(R.id.cameraTxt);
        deIssueBtn = findViewById(R.id.deIssueBtn);
        othersTxt = findViewById(R.id.accessoriestxt);
        BagTxt = findViewById(R.id.bagTxt);
        batteryListView = findViewById(R.id.batteryissueList);
        lensListView = findViewById(R.id.lensdeissueList);
        sdListView = findViewById(R.id.sddeissueList);
        batteryList = new ArrayList<String >();
        batteryFinalList = new ArrayList<String>();
        sdList = new ArrayList<String>();
        lensList = new ArrayList<getItem>();

        issueDatabase = FirebaseDatabase.getInstance().getReference().child("currentIssue").child(id);
        issueDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    nameTxt.setText(dataSnapshot.child("name").getValue().toString());
                    eventTxt.setText(dataSnapshot.child("eventName").getValue().toString());
                    if(dataSnapshot.hasChild("cameraName")){
                        cameraTxt.setText(dataSnapshot.child("cameraName").getValue().toString());
                    }else{
                        cameraTxt.setText("Camera Returned");
                    }
                    if(dataSnapshot.hasChild("bagType")){
                        BagTxt.setText(dataSnapshot.child("bagType").getValue().toString());

                    }else{
                        BagTxt.setText("Bag Returned");
                    }
                    if(dataSnapshot.hasChild("accessories")){
                        othersTxt.setText(dataSnapshot.child("accessories").getValue().toString());
                        if(dataSnapshot.child("accessories").getValue().toString().equals("None")){
                            othersTxt.setCheckMarkDrawable(R.drawable.checked);
                            othersTxt.setChecked(true);
                        }
                    }else{
                        othersTxt.setText("accessories returned");
                    }

                    }catch (Exception e){

                    Log.e("Error",e.toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        cameraTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cameraTxt.isChecked()){
                    cameraTxt.setCheckMarkDrawable(null);
                    cameraTxt.setChecked(false);
                } else {
                    cameraTxt.setCheckMarkDrawable(R.drawable.checked);
                    cameraTxt.setChecked(true);
                }
            }
        });

        BagTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BagTxt.isChecked()){
                    BagTxt.setCheckMarkDrawable(null);
                    BagTxt.setChecked(false);
                } else {
                    BagTxt.setCheckMarkDrawable(R.drawable.checked);
                    BagTxt.setChecked(true);
                }
            }
        });

        othersTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(othersTxt.isChecked()){
                    othersTxt.setCheckMarkDrawable(null);
                    othersTxt.setChecked(false);
                } else {
                    othersTxt.setCheckMarkDrawable(R.drawable.checked);
                    othersTxt.setChecked(true);
                }
            }
        });

        issueDatabase.child("batteryType").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot issueid : dataSnapshot.getChildren()) {
                    batteryList.add(issueid.child("batteryName").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        issueDatabase.child("sdType").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot issueid : dataSnapshot.getChildren()) {
                    sdList.add(issueid.child("sdName").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        issueDatabase.child("lensType").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot issueid : dataSnapshot.getChildren()) {
                    getItem item = new getItem();
                    item.setLensName(issueid.child("lensName").getValue().toString());
                    item.setCover(issueid.child("lensCover").getValue().toString());
                    lensList.add(item);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final deIssueListAdapter batteryAdapter = new deIssueListAdapter(getApplicationContext(),batteryList);
        batteryListView.setAdapter(batteryAdapter);

        final deIssueListAdapter sdAdapter = new deIssueListAdapter(getApplicationContext(),sdList);
        sdListView.setAdapter(sdAdapter);

        deIssueLensListAdapter = new deIssueLensListAdapterB(deIssueKit.this,lensList);
        lensListView.setAdapter(deIssueLensListAdapter);

        deIssueListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild("cameraName")||dataSnapshot.hasChild("batterytype")||
                        dataSnapshot.hasChild("lensType")||dataSnapshot.hasChild("sdType")||
                        dataSnapshot.hasChild("bagType")){
                    Toast.makeText(deIssueKit.this, "Some Items are still remaining with the student", Toast.LENGTH_SHORT).show();
                }else{
                    issueDatabase.getRef().removeValue();
                    Toast.makeText(deIssueKit.this, "All items returned by the Student", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        deIssueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  deleteLens(deIssueLensListAdapter.getList(),"lensType");
                  deleteItems(batteryAdapter.getList(),"batteryType","batteryName","Battery Specs");
                  deleteItems(sdAdapter.getList(),"sdType","sdName","SD Card Specs");
                  if(cameraTxt.isChecked()){
                      deleteBasic("cameraName");
                      updateCameraInventory("Camera Name",cameraTxt.getText().toString());
                  }
                  if(BagTxt.isChecked()){
                      deleteBasic("bagType");
                  }
                  if(othersTxt.isChecked()){
                      deleteBasic("accessories");
                  }


                  issueDatabase.addValueEventListener(deIssueListener);




                  Intent homeActivity = new Intent(deIssueKit.this,HomeActivity.class);
                  startActivity(homeActivity);
            }
        });






    }

    public void deleteItems(final List<String> list, final String childNameMain, final String childNameSub,final String inventoryTag){

        final DatabaseReference inventoryDatabase = FirebaseDatabase.getInstance().getReference().child("inventoryItems").child(inventoryTag);


        issueDatabase.child(childNameMain).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot itemID : dataSnapshot.getChildren()) {
                        if(itemID.child(childNameSub).getValue().toString()!=null){
                            for(int i=0;i<list.size();i++){
                                if(itemID.child(childNameSub).getValue().toString().equals(list.get(i))){
                                    itemID.getRef().removeValue();

                                    final int finalI = i;
                                    inventoryDatabase.child("inventory"+list.get(i)).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            int quantity = Integer.parseInt(dataSnapshot.child("quantity").getValue().toString());
                                            quantity = quantity + 1;
                                            inventoryDatabase.child("inventory"+list.get(finalI)).child("quantity").setValue(quantity);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }
                        }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteLens(final List<getItem> list, final String childNameMain){
        final DatabaseReference inventoryDatabase = FirebaseDatabase.getInstance().getReference().child("inventoryItems").child("Lens Type");
        issueDatabase.child(childNameMain).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot itemID : dataSnapshot.getChildren()){
                    for(int i=0;i<list.size();i++){
                        if(itemID.child("lensName").getValue().toString().equals(list.get(i).getLensName())&&itemID.child("lensCover").getValue().toString().equals(list.get(i).getCover())){
                            itemID.getRef().removeValue();

                            final int finalI = i;
                            inventoryDatabase.child("inventory"+list.get(i).getLensName()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    int quantity = Integer.parseInt(dataSnapshot.child("quantity").getValue().toString());
                                    quantity = quantity + 1;
                                    inventoryDatabase.child("inventory"+list.get(finalI).getLensName()).child("quantity").setValue(quantity);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteBasic(String childName){
        issueDatabase.child(childName).getRef().removeValue();
    }

    public void updateCameraInventory(String childname, String id){

        final DatabaseReference inventoryDatabase = FirebaseDatabase.getInstance().getReference().child("inventoryItems").child(childname).child("inventory"+id);
        inventoryDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int quantity = Integer.parseInt(dataSnapshot.child("quantity").getValue().toString());
                quantity = quantity + 1;
                inventoryDatabase.child("quantity").setValue(quantity);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        issueDatabase.removeEventListener(deIssueListener);
    }

}
