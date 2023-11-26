package com.example.rinapizza;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity2 extends AppCompatActivity {

    private static final String CHANNEL_ID = "RinaPizzaChannel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        createNotificationChannel();

        startService(new Intent(this, MyAudioService.class));

        final EditText nameEditText = findViewById(R.id.editText1);
        final EditText phoneNumberEditText = findViewById(R.id.editText2);
        final EditText emailEditText = findViewById(R.id.editText3);
        final EditText cityEditText = findViewById(R.id.editText4);
        final Button done = findViewById(R.id.button2);

        done.setOnClickListener(view -> {
            String name = nameEditText.getText().toString();
            String phoneNumber = phoneNumberEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String city = cityEditText.getText().toString();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(email) || TextUtils.isEmpty(city)) {
                showToast("All fields are required");
                return;
            }

            if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
                showToast("Invalid phone number");
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showToast("Invalid email address");
                return;
            }

            // Send a notification
            sendNotification(name);

            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
            intent.putExtra("Name", name);
            intent.putExtra("PhoneNumber", phoneNumber);
            intent.putExtra("Email", email);
            intent.putExtra("City", city);
            startActivity(intent);
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button button5 = findViewById(R.id.Splashtrailer);
        button5.setOnClickListener(view -> {
            // Navigate to PizzaMenuActivity
            Intent button21 = new Intent(MainActivity2.this, SplashVideoActivity.class);
            startActivity(button21);
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Rina Pizza Channel";
            String description = "Channel for Rina Pizza notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotification(String name) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.rina)
                .setContentTitle("Welcome to Rina Pizza!")
                .setContentText("Thank you, " + name + ", for joining the RINA PIZZA app.")
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
        notificationManager.notify(1, builder.build());
    }


}
