package application.dopy_inventory;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class addItemsActivity extends AppCompatActivity {

    Spinner typeSpinner;
    Button addItem;
    EditText itemTxt,quantitytxt;
    List<String> spinnerlist;
    String type;
    DatabaseReference inventoryDatabase;
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        typeSpinner = findViewById(R.id.typeSpinner);
        addItem = findViewById(R.id.addBtn);
        itemTxt = findViewById(R.id.itemTxt);
        quantitytxt = findViewById(R.id.quantityTxt);
        loading = new ProgressDialog(addItemsActivity.this);
        loading.setCancelable(false);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.setMessage("Please Wait");
        spinnerlist = new ArrayList<String>();
        spinnerlist.add("Camera Name");
        spinnerlist.add("Lens Type");
        spinnerlist.add("Battery Specs");
        spinnerlist.add("SD Card Specs");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemname = itemTxt.getText().toString();
                String quantity = quantitytxt.getText().toString();
                int flag=0;
                if(TextUtils.isEmpty(itemname)){
                    itemTxt.setError("Please enter the name");
                    flag=1;
                }
                if(TextUtils.isEmpty(quantity)){
                    quantitytxt.setError("Please enter the quantity");
                    flag=1;
                }
                if(flag==0){
                    loading.show();
                    inventoryDatabase = FirebaseDatabase.getInstance().getReference().child("inventoryItems").child(type).child("inventory"+itemname);
                    inventoryDatabase.child("quantity").setValue(quantity);
                    inventoryDatabase.child("itemname").setValue(itemname).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(addItemsActivity.this, "Item Added to inventory Successfully", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                            itemTxt.setText("");
                            quantitytxt.setText("");
                        }
                    });
                }

            }
        });
    }
}
