package com.example.android.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//₹

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 1;   // Global variable
    String officialMail = "justjavastore@gmail.com";

    /* This method is called when ORDER button is clicked */

    @SuppressLint("QueryPermissionsNeeded")
    public void submitOrder(View view) {
        double unitPrice = 20.0;
        double price = calculatePrice(quantity, unitPrice);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"justjavastore@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + getName());
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(getName(), price, checkWhippCream(), checkChocolate()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /*
     *Create summary of the order.
     * @param number as the total price of order.
     * returning a String containing order details.
     */

    @SuppressLint({"StringFormatInvalid", "StringFormatMatches"})
    private String createOrderSummary(String name, double number, boolean hasWhippedCream, boolean hasChocolate) {
        String WhippedCream = "";
        String Chocolate = "";
        if(hasWhippedCream)
            WhippedCream = "Yes";
        else
            WhippedCream = "No";

        if(hasChocolate)
            Chocolate = "Yes";
        else
            Chocolate = "No";

        return getString(R.string.order_summary_name, name) + "\n\n" +
                getString(R.string.whipped_cream_java, WhippedCream) + "\n" +
                getString(R.string.chocolate_java, Chocolate) + "\n" +
                getString(R.string.quantity_java, quantity) + "\n" +
                getString(R.string.total_java, number) + "\n\n\n" +
                getString(R.string.thank_you) +"\n" +
                getString(R.string.greet) +"\n\n\n\n" +
                getString(R.string.company);
    }

    /* This method is called when PLUS button is called */

    public void increment(View view) {
        if (quantity <= 99)
            quantity = quantity + 1;
        else Toast.makeText(MainActivity.this, getText(R.string.toast2), Toast.LENGTH_SHORT).show();
        // Toast for displaying the error.
        displayQuantity(quantity);
    }

    /* This method is called when MINUS button is called */

    public void decrement(View view) {
        if (quantity >= 2)
            quantity = quantity - 1;
        else
            Toast.makeText(MainActivity.this, getString(R.string.toast1), Toast.LENGTH_SHORT).show();
        // Toast for displaying the error.
        displayQuantity(quantity);
    }

    // Returns the totalPrice.

    private double calculatePrice(int quantity, double price) {

        double totalPrice;

        if (checkChocolate() && checkWhippCream()) {
            totalPrice = quantity * (price + 15);
        } else if (checkChocolate()) {
            totalPrice = quantity * (price + 10);
        } else if (checkWhippCream()) {
            totalPrice = quantity * (price + 5);
        } else {
            totalPrice = quantity * price;
        }
        return totalPrice;
    }

    /*
     * Create displayQuantity method to display the quantity.
     *
     *   @param numberOfCups of the city
     */

    public void displayQuantity(int numberOfCups) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCups);
    }

    /*
     * This function will check if the whipped cream is added or not.
     */

    public boolean checkWhippCream() {
        CheckBox checkWhipCream = findViewById(R.id.checkbox_whipped_cream);
        boolean hasWhippedCream = checkWhipCream.isChecked();
        return hasWhippedCream;
    }

    /*
     * This function will check if the chocolate is added or not.
     */

    public boolean checkChocolate() {
        CheckBox checkChocolate = findViewById(R.id.checkbox_chocolate);
        boolean hasChocolate = checkChocolate.isChecked();
        return hasChocolate;
    }

    public String getName() {
        EditText name = findViewById(R.id.name_view);
        String customerName = name.getText().toString();
        return customerName;
    }
}