package application.dopy_inventory;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class issueKitActivity extends AppCompatActivity {

    private Button addlensBtn,addBatteryBtn,addSdBtn,issueBtn,addCameranameBtn;
    private EditText nameTxt,eventNameTxt,othersTxt;
    private Spinner cameraSpinner,lensSpinner,batterySpinner,coverSpinner,bagSpinner,sdSpinner;
    private ListView lensListView, batteryListView, sdListView;
    private DatabaseReference inventoryDatabase;
    private DatabaseReference issueDatabase;
    String cameraType;
    String lensType;
    String batteryType;
    String sdType;
    String bagType;
    ArrayAdapter Batteryadapter,SDadapter;
    AlertDialog alertDialog;
    List<String> cameranameList,batterytypeList,sdcardtypeList,bagList,coverList,lenstypeList, lensCoverViewList,batterynameViewList,
    sdcardViewList;
    List<getItem> lensTypeViewList;
    private String lensCoverType;
    private lensListAdapter customAdapter;
    private String name;
    private String eventName;
    private String accessories;
    ProgressDialog loading;
    private int flagnew = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_kit);

        addlensBtn = findViewById(R.id.lensAddBtn);
        addBatteryBtn = findViewById(R.id.batteryAddBtn);
        addSdBtn = findViewById(R.id.sdCardAddBtn);
        issueBtn = findViewById(R.id.issueBtn);
        nameTxt = findViewById(R.id.nametxt);
        eventNameTxt = findViewById(R.id.eventEditTxt);
        othersTxt = findViewById(R.id.otherstxt);
        cameraSpinner = findViewById(R.id.cameraSpinner);
        lensSpinner = findViewById(R.id.lensSpinner);
        batterySpinner = findViewById(R.id.BatterySpinner);
        bagSpinner = findViewById(R.id.bagSpinner);
        coverSpinner = findViewById(R.id.capSpinner);
        sdSpinner = findViewById(R.id.sdCardSpinner);
        lensListView = findViewById(R.id.lensItemList);
        batteryListView = findViewById(R.id.batteryItemList);
        sdListView = findViewById(R.id.sdCardList);
        loading = new ProgressDialog(issueKitActivity.this);
        loading.setCancelable(false);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Please Wait");
        inventoryDatabase = FirebaseDatabase.getInstance().getReference().child("inventoryItems");

        bagList = new ArrayList<String>();
        bagList.add("Yes");
        bagList.add("No");

        coverList = new ArrayList<String>();
        coverList.add("Yes");
        coverList.add("No");

        ArrayAdapter<String> bagadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bagList);
        bagadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bagSpinner.setAdapter(bagadapter);

        ArrayAdapter<String> coveradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, coverList);
        coveradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coverSpinner.setAdapter(coveradapter);

        cameranameList = new ArrayList<String>();
        batterytypeList = new ArrayList<String>();
        sdcardtypeList = new ArrayList<String>();
        lenstypeList = new ArrayList<String>();
        lensCoverViewList = new ArrayList<String>();
        lensTypeViewList = new ArrayList<getItem>();
        sdcardViewList = new ArrayList<String>();
        batterynameViewList = new ArrayList<String>();
        inventoryDatabase.child("Camera Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    cameranameList.clear();
                    for (final DataSnapshot inventoryid : dataSnapshot.getChildren()) {
                        if(Integer.parseInt(inventoryid.child("quantity").getValue().toString())>0){

                            cameranameList.add(inventoryid.child("itemname").getValue().toString());
                            ArrayAdapter<String> cameraNameadapter = new ArrayAdapter<String>(issueKitActivity.this, android.R.layout.simple_spinner_item, cameranameList);
                            cameraNameadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            cameraSpinner.setAdapter(cameraNameadapter);

                        }
                    }
                }catch (Exception e){
                    Log.e("Error",e.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        inventoryDatabase.child("Battery Specs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    batterytypeList.clear();
                    for (final DataSnapshot inventoryid : dataSnapshot.getChildren()) {
                        if(Integer.parseInt(inventoryid.child("quantity").getValue().toString())>0){
                            batterytypeList.add(inventoryid.child("itemname").getValue().toString());
                            ArrayAdapter<String> batteryTypeadapter = new ArrayAdapter<String>(issueKitActivity.this, android.R.layout.simple_spinner_item, batterytypeList);
                            batteryTypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            batterySpinner.setAdapter(batteryTypeadapter);
                        }
                    }

                }catch (Exception e){
                    Log.e("Error",e.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        inventoryDatabase.child("Lens Type").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    lenstypeList.clear();
                    for (final DataSnapshot inventoryid : dataSnapshot.getChildren()) {
                        if(Integer.parseInt(inventoryid.child("quantity").getValue().toString())>0){
                            lenstypeList.add(inventoryid.child("itemname").getValue().toString());
                            ArrayAdapter<String> LensTypeadapter = new ArrayAdapter<String>(issueKitActivity.this, android.R.layout.simple_spinner_item, lenstypeList);
                            LensTypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            lensSpinner.setAdapter(LensTypeadapter);
                        }
                    }

                }catch (Exception e){
                    Log.e("Error",e.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        inventoryDatabase.child("SD Card Specs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    sdcardtypeList.clear();
                    for (final DataSnapshot inventoryid : dataSnapshot.getChildren()) {
                        if(Integer.parseInt(inventoryid.child("quantity").getValue().toString())>0){
                            sdcardtypeList.add(inventoryid.child("itemname").getValue().toString());
                            ArrayAdapter<String> sdcardTypeadapter = new ArrayAdapter<String>(issueKitActivity.this, android.R.layout.simple_spinner_item, sdcardtypeList);
                            sdcardTypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sdSpinner.setAdapter(sdcardTypeadapter);
                        }
                    }

                }catch (Exception e){
                    Log.e("Error",e.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        cameraSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cameraType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        batterySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    batteryType = parent.getItemAtPosition(position).toString();
                }catch (Exception e){
                    Log.e("Error battery",e.toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sdType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bagSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bagType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lensSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lensType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        coverSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lensCoverType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addBatteryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                batterynameViewList.add(batteryType);
                Batteryadapter = new ArrayAdapter<String>(issueKitActivity.this,
                        R.layout.listlayout,R.id.typeTxt, batterynameViewList);
                batteryListView.setAdapter(Batteryadapter);
            }
        });
        addSdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sdcardViewList.add(sdType);
                SDadapter = new ArrayAdapter<String>(issueKitActivity.this,
                        R.layout.listlayout,R.id.typeTxt, sdcardViewList);
                sdListView.setAdapter(SDadapter);
            }
        });
        addlensBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getItem lensitem = new getItem();
                lensitem.setLensName(lensType);
                lensitem.setCover(lensCoverType);
                lensTypeViewList.add(lensitem);
                customAdapter = new lensListAdapter(issueKitActivity.this, R.layout.lenslistlayout,lensTypeViewList);
                lensListView.setAdapter(customAdapter);

            }
        });

        lensListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        batteryListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        sdListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        lensListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(issueKitActivity.this);
                alertDialogBuilder.setMessage("Are you sure, You wanted to Delete This item");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                customAdapter.remove(lensTypeViewList.get(position));
                                customAdapter.notifyDataSetChanged();

                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            }
        });

        batteryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(issueKitActivity.this);
                alertDialogBuilder.setMessage("Are you sure, You wanted to Delete This item");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Batteryadapter.remove(batterynameViewList.get(position));
                                Batteryadapter.notifyDataSetChanged();

                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            }
        });

        sdListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(issueKitActivity.this);
                alertDialogBuilder.setMessage("Are you sure, You wanted to Delete This item");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                SDadapter.remove(sdcardViewList.get(position));
                                SDadapter.notifyDataSetChanged();

                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            }
        });




        issueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameTxt.getText().toString();
                eventName = eventNameTxt.getText().toString();
                int flag=0;
                accessories = othersTxt.getText().toString();
                if(TextUtils.isEmpty(name)){
                    nameTxt.setError("Please Enter the name");
                    flag=1;
                }
                if(TextUtils.isEmpty(eventName)){
                    eventNameTxt.setError("Please Enter the Event Name");
                    flag=1;
                }
                if(TextUtils.isEmpty(accessories)){
                    accessories = "None";
                }

                if(flag==0){
                    loading.show();
                    issueDatabase = FirebaseDatabase.getInstance().getReference().child("currentIssue").push();

                    for(int i=0;i<lensTypeViewList.size();i++){
                        final int finalI = i;
                        ValueEventListener listener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int quantity = Integer.parseInt(dataSnapshot.child("quantity").getValue().toString());
                                quantity=quantity-1;
                                inventoryDatabase.child("Lens Type").child("inventory"+lensTypeViewList.get(finalI).getLensName()).child("quantity").setValue(quantity);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };

                        inventoryDatabase.child("Lens Type").child("inventory"+lensTypeViewList.get(i).getLensName()).addListenerForSingleValueEvent(listener);

                        DatabaseReference reference = issueDatabase.child("lensType").push();
                        reference.child("lensName").setValue(lensTypeViewList.get(i).getLensName());
                        reference.child("lensCover").setValue(lensTypeViewList.get(i).getCover());
                    }

                    for(int i=0;i<batterynameViewList.size();i++){
                        final int finalI = i;
                        ValueEventListener listener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int quantity = Integer.parseInt(dataSnapshot.child("quantity").getValue().toString());
                                quantity=quantity-1;
                                inventoryDatabase.child("Battery Specs").child("inventory"+batterynameViewList.get(finalI)).child("quantity").setValue(quantity);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };

                        inventoryDatabase.child("Battery Specs").child("inventory"+batterynameViewList.get(i)).addListenerForSingleValueEvent(listener);
                        DatabaseReference reference = issueDatabase.child("batteryType").push();
                        reference.child("batteryType").push();
                        reference.child("batteryName").setValue(batterynameViewList.get(i));
                    }

                    for(int i=0;i<sdcardViewList.size();i++){

                        final int finalI = i;
                        ValueEventListener listener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int quantity = Integer.parseInt(dataSnapshot.child("quantity").getValue().toString());
                                quantity=quantity-1;
                                inventoryDatabase.child("SD Card Specs").child("inventory"+sdcardViewList.get(finalI)).child("quantity").setValue(quantity);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };

                            inventoryDatabase.child("SD Card Specs").child("inventory"+sdcardViewList.get(i)).addListenerForSingleValueEvent(listener);
                            DatabaseReference reference = issueDatabase.child("sdType").push();
                            reference.child("sdType").push();
                            reference.child("sdName").setValue(sdcardViewList.get(i));
                    }

                    ValueEventListener listener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int quantity = Integer.parseInt(dataSnapshot.child("quantity").getValue().toString());
                            quantity=quantity-1;
                            inventoryDatabase.child("Camera Name").child("inventory"+cameraType).child("quantity").setValue(quantity);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };

                    inventoryDatabase.child("Camera Name").child("inventory"+cameraType).addListenerForSingleValueEvent(listener);


                        issueDatabase.child("name").setValue(name);
                        issueDatabase.child("eventName").setValue(eventName);
                        issueDatabase.child("accessories").setValue(accessories);
                        issueDatabase.child("cameraName").setValue(cameraType);
                        issueDatabase.child("bagType").setValue(bagType).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                loading.dismiss();
                                Toast.makeText(issueKitActivity.this, "Successfully Issued", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(issueKitActivity.this);
                                alertDialogBuilder.setMessage("Do you want to return to the home screen ?");
                                alertDialogBuilder.setPositiveButton("yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                Intent homeActivity = new Intent(issueKitActivity.this,HomeActivity.class);
                                                startActivity(homeActivity);
                                            }
                                        });

                                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        alertDialog.dismiss();
                                    }
                                });
                                alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        });


                }

            }
        });






    }
    @Override
    public void onStart(){
        super.onStart();


    }

    @Override
    public void onResume(){
        super.onResume();

    }
}
