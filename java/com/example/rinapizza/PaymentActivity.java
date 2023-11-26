package com.example.rinapizza;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class PaymentActivity extends AppCompatActivity {

    private static final String DELIVERY_CHANNEL_ID = "DeliveryChannel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        createNotificationChannel();

        TextView tvTotalAmount = findViewById(R.id.tvTotalAmount);
        EditText etCardNumber = findViewById(R.id.etCardNumber);
        EditText etExpiryDate = findViewById(R.id.etExpiryDate);
        EditText etCVV = findViewById(R.id.etCVV);
        Button btnProcessPayment = findViewById(R.id.btnProcessPayment);

        // Retrieve total amount from the intent
        Intent intent = getIntent();
        if (intent != null) {
            double totalPrice = intent.getDoubleExtra("totalPrice", 0.0);
            tvTotalAmount.setText("Total Amount: $" + totalPrice);
        }

        btnProcessPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event to process the payment
                String cardNumber = etCardNumber.getText().toString().trim();
                String expiryDate = etExpiryDate.getText().toString().trim();
                String cvv = etCVV.getText().toString().trim();

                if (validatePaymentDetails(cardNumber, expiryDate, cvv)) {
                    // For simplicity, assume the payment is successful
                    // You should replace this with your actual payment processing logic
                    handlePaymentSuccess();

                    // Notify user about pizza delivery
                    notifyDelivery();

                    // Start countdown timer for delivery
                    startDeliveryTimer();
                } else {
                    // Show an error message if payment details are not valid
                    Toast.makeText(PaymentActivity.this, "Invalid Payment Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Delivery Channel";
            String description = "Channel for delivery notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(DELIVERY_CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private boolean validatePaymentDetails(String cardNumber, String expiryDate, String cvv) {
        // Add your payment validation logic here
        // For simplicity, checking if fields are not empty
        return !cardNumber.isEmpty() && !expiryDate.isEmpty() && !cvv.isEmpty();
    }

    private void handlePaymentSuccess() {
        Toast.makeText(PaymentActivity.this, "Payment Success!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void notifyDelivery() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, DELIVERY_CHANNEL_ID)
                .setSmallIcon(R.drawable.pizza)
                .setContentTitle("RINA PIZZA")
                .setContentText("Your pizza will be delivered soon!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void startDeliveryTimer() {
        new CountDownTimer(300000, 1000) { // 5 minutes (300,000 milliseconds)
            @SuppressLint("MissingPermission")
            public void onTick(long millisUntilFinished) {
                // Update UI or perform actions during the countdown (optional)
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished % 60000) / 1000;
                String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);

                // Update the notification text with the live countdown
                NotificationCompat.Builder builder = new NotificationCompat.Builder(PaymentActivity.this, DELIVERY_CHANNEL_ID)
                        .setSmallIcon(R.drawable.pizza)
                        .setContentTitle("RINA PIZZA")
                        .setContentText("Your pizza will be delivered in: " + timeLeftFormatted)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(PaymentActivity.this);
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }

            public void onFinish() {
                // Delivery time is up, you can perform additional actions here if needed
                Toast.makeText(PaymentActivity.this, "Your pizza is on your doorstep!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }
}
