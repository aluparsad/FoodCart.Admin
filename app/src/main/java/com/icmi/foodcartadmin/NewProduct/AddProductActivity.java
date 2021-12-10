package com.icmi.foodcartadmin.NewProduct;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.icmi.foodcartadmin.Model.Food;
import com.icmi.foodcartadmin.R;

public class AddProductActivity extends AppCompatActivity implements Contractor.View, AdapterView.OnItemSelectedListener {

    private Contractor.Presenter presenter;

    private ImageButton imgBTN;
    private EditText name, price;
    private Spinner catSpinner;
    private Button postBtn;

    private ArrayAdapter<CharSequence> spinnerAdapter;
    private Food foodItem;
    private final int REQUEST_CODE = 104;


    private void Init() {
        presenter = new Presenter(this);
        foodItem = new Food();

        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.support_simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        imgBTN = findViewById(R.id.productImageBTN);
        name = findViewById(R.id.ET_productname);
        price = findViewById(R.id.Et_price);
        catSpinner = findViewById(R.id.spinner_category);
        postBtn = findViewById(R.id.PostBtn);

        catSpinner.setAdapter(spinnerAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Init();

        initiateListener();

    }

    private void initiateListener() {
        postBtn.setOnClickListener(v -> {
            if (getProduct()) {
                presenter.uploadFoodItem(foodItem);
                Toast.makeText(this, "in if",Toast.LENGTH_SHORT).show();

            }
            else
                Toast.makeText(this, "in else",Toast.LENGTH_SHORT).show();
        });

        imgBTN.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("image/*");
            startActivityForResult(i, REQUEST_CODE);
        });

        catSpinner.setOnItemSelectedListener(this);

    }

    private boolean getProduct() {
        if ((foodItem.getCategory() != null) && (foodItem.getImage() != null)) {
            String itemName = name.getText().toString();
            String itemPrice = price.getText().toString();

            if (!(itemName.isEmpty() && itemPrice.isEmpty())) {
                foodItem.setName(itemName);
                foodItem.setPrice(Integer.decode(itemPrice));
                return true;
            }
            Toast.makeText(this, "Please fill the Fields", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && REQUEST_CODE == requestCode) {
            foodItem.setImage(data.getData().toString());
            imgBTN.setImageURI(Uri.parse(foodItem.getImage()));
        } else {
            Toast.makeText(this, "Try Again...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String[] catogs = getResources().getStringArray(R.array.category);
        foodItem.setCategory(catogs[position]);
        Toast.makeText(this, catogs[position], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void clearView() {
        imgBTN.setImageURI(null);
        name.setText("");
        price.setText("");
    }

    @Override
    public void ShowLoading() {

    }

    @Override
    public void HideLoading() {

    }

    @Override
    public void onSuccess() {
        HideLoading();
        clearView();
        Toast.makeText(this, "Done.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {
        HideLoading();
    }
}

