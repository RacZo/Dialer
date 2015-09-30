/***
 * Copyright (c) 2015 Oscar Salguero www.oscarsalguero.com
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oscarsalguero.dialer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oscarsalguero.dialer.adapter.CustomSpinnerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Main Activity
 * <p/>
 * This app serves as an alternative phone dialer.
 * <p/>
 * This activity presents the user with a a spinner to select a country, a display that shows the phone number,
 * a delete button, a phone keypad, a cancel and call buttons.
 * <p/>
 * The phone number is validated before calling/dialing the phone.
 * <p/>
 * Created by RacZo on 9/28/15.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = MainActivity.class.getName();

    private static final String URI_PREFIX_TEL = "tel:";
    private static final String SYMBOL_STAR = "*";
    private static final String SYMBOL_POUND = "#";

    private static final int PERMISSIONS_REQUEST_CALL_PHONE = 100;

    private RelativeLayout mLayout;
    private Spinner spinnerCountryCode;
    private TextView textViewDisplay;
    private Button buttonDel;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button buttonStar;
    private Button buttonZero;
    private Button buttonPound;
    private Button buttonCancel;
    private Button buttonCall;

    private List<HashMap<String, Object>> spinnerList = new ArrayList<HashMap<String, Object>>();
    private CustomSpinnerAdapter adapter;
    private static Integer[] flagDatabase = {
            R.drawable.us_normal,
            R.drawable.sv_normal,
            R.drawable.au_normal,
            R.drawable.br_normal,
            R.drawable.de_normal,
            R.drawable.es_normal,
            R.drawable.fr_normal,
            R.drawable.it_normal,
            R.drawable.jp_normal,
            R.drawable.mx_normal
    };
    private String[] countryCodeDatabase = {
            "+1",
            "+503",
            "+61",
            "+55",
            "+49",
            "+34",
            "+33",
            "+39",
            "+81",
            "+52"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = (RelativeLayout) findViewById(R.id.relative_layout_dialer);
        spinnerCountryCode = (Spinner) findViewById(R.id.spinner_country_code);
        textViewDisplay = (TextView) findViewById(R.id.text_view_display);
        textViewDisplay.setOnClickListener(this);
        buttonDel = (Button) findViewById(R.id.button_del);
        buttonDel.setOnClickListener(this);
        button1 = (Button) findViewById(R.id.button_1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button_2);
        button2.setOnClickListener(this);
        button3 = (Button) findViewById(R.id.button_3);
        button3.setOnClickListener(this);
        button4 = (Button) findViewById(R.id.button_4);
        button4.setOnClickListener(this);
        button5 = (Button) findViewById(R.id.button_5);
        button5.setOnClickListener(this);
        button6 = (Button) findViewById(R.id.button_6);
        button6.setOnClickListener(this);
        button7 = (Button) findViewById(R.id.button_7);
        button7.setOnClickListener(this);
        button8 = (Button) findViewById(R.id.button_8);
        button8.setOnClickListener(this);
        button9 = (Button) findViewById(R.id.button_9);
        button9.setOnClickListener(this);
        buttonZero = (Button) findViewById(R.id.button_zero);
        buttonZero.setOnClickListener(this);
        buttonStar = (Button) findViewById(R.id.button_star);
        buttonStar.setOnClickListener(this);
        buttonPound = (Button) findViewById(R.id.button_pound);
        buttonPound.setOnClickListener(this);
        buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(this);
        buttonCall = (Button) findViewById(R.id.button_call);
        buttonCall.setOnClickListener(this);

        initializeCountryList();

        adapter = new CustomSpinnerAdapter(
                this,
                spinnerList,
                R.layout.spinner_view,
                new String[]{"flag", "code"},
                new int[]{R.id.image_view_flag, R.id.text_view_code}
        );
        spinnerCountryCode.setAdapter(adapter);
        spinnerCountryCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> map = (HashMap) adapter.getItem(position);
                String code = (String) map.get("code");
                textViewDisplay.setText(code);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    /**
     * Handle UI clicks.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_display:
                // Nothing happens when the display is tapped
                break;
            case R.id.button_del:
                deleteCharacter();
                break;
            case R.id.button_1:
                appendDigitAndFormat(1);
                break;
            case R.id.button_2:
                appendDigitAndFormat(2);
                break;
            case R.id.button_3:
                appendDigitAndFormat(3);
                break;
            case R.id.button_4:
                appendDigitAndFormat(4);
                break;
            case R.id.button_5:
                appendDigitAndFormat(5);
                break;
            case R.id.button_6:
                appendDigitAndFormat(6);
                break;
            case R.id.button_7:
                appendDigitAndFormat(7);
                break;
            case R.id.button_8:
                appendDigitAndFormat(8);
                break;
            case R.id.button_9:
                appendDigitAndFormat(9);
                break;
            case R.id.button_zero:
                appendDigitAndFormat(0);
                break;
            case R.id.button_star:
                appenSymbolAndFormat(SYMBOL_STAR);
                break;
            case R.id.button_pound:
                appenSymbolAndFormat(SYMBOL_POUND);
                break;
            case R.id.button_cancel:
                cancel();
                break;
            case R.id.button_call:
                call();
                break;
            default:
                break;
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mLayout, R.string.permission_available_call_phone, Snackbar.LENGTH_SHORT).show();
                callPhone();
            } else {
                Snackbar.make(mLayout, R.string.permissions_not_granted, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Requests the CALL_PHONE permission. The CALL_PHONE permission is
     * required in order to make outbound calls.
     */
    private void requestCallPhonePermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {

            Snackbar.make(mLayout, R.string.permission_contacts_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.button_ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_CALL_PHONE);
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_CALL_PHONE);
        }
    }

    /**
     * Validates phone number and initiates the phone call
     */
    private void call(){
        if (textViewDisplay.getText() != null) {
            if (textViewDisplay.getText().toString().length() > 0) {
                if(validPhoneNumber(textViewDisplay.getText().toString())){
                    try {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            requestCallPhonePermissions();
                        } else {
                            callPhone();
                        }
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "Could not start CALL intent. Will try to start DIAL intent.", e);
                        dialPhone();
                    }
                }else{
                    showPhoneValidationToast();
                }
            } else {
                showPhoneValidationToast();
            }
        } else {
            showPhoneValidationToast();
        }
    }

    /**
     * Validates the phone number
     */
    private boolean validPhoneNumber(String phoneNumber){
        //TODO Validate the  phone number
        return true;
    }

    /**
     * Shows a toast saying the phone number is not valid
     */
    private void showPhoneValidationToast(){
        Toast.makeText(MainActivity.this, getString(R.string.validation_enter_phone_number), Toast.LENGTH_SHORT).show();
    }

    /**
     * Cancels the call and clears the display
     */
    private void cancel(){
        spinnerCountryCode.setSelection(0);
        textViewDisplay.setText("");
    }

    /**
     * Appends a symbol to the dialer's display and tries to format the phone number
     *
     * @param symbol The symbol to append
     */
    private void appenSymbolAndFormat(String symbol) {
        textViewDisplay.append(symbol);
        // TODO Format phone number
    }

    /**
     * Appends a digit to the dialer's display and tries to format the phone number
     *
     * @param digit The digit to append
     */
    private void appendDigitAndFormat(int digit) {
        textViewDisplay.append(String.valueOf(digit));
        // TODO Format phone number
    }

    /**
     * Deletes the last character from the dialer's display
     */
    private void deleteCharacter() {
        if (textViewDisplay.getText() != null) {
            String phoneNumber = textViewDisplay.getText().toString();
            if (phoneNumber.length() > 0) {
                phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
                textViewDisplay.setText(phoneNumber);
            }
        }
    }

    /**
     * Creates a list with hash maps of flags and country codes that serves as the data for the spinner.
     */
    private void initializeCountryList() {
        for (int i = 0; i < flagDatabase.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("flag", flagDatabase[i]);
            map.put("code", countryCodeDatabase[i]);
            spinnerList.add(map);
        }
    }

    /**
     * Dials a phone number using the ACTION_DIAL intent (does not require permission).
     */
    private void dialPhone() {
        try {
            String phoneNumber = textViewDisplay.getText().toString();
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse(URI_PREFIX_TEL + Uri.encode(phoneNumber)));
            dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialIntent);
            logPhoneNumber(phoneNumber);
        } catch (Exception e) {
            Log.e(LOG_TAG, "An exception occurred trying to execute the DIAL intent.", e);
        }
    }

    /**
     * Dials a phone number using the ACTION_CALL intent (requires CALL_PHONE permission).
     */
    private void callPhone() {
        try {
            String phoneNumber = textViewDisplay.getText().toString();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(URI_PREFIX_TEL + Uri.encode(phoneNumber)));
            startActivity(callIntent);
            logPhoneNumber(phoneNumber);
        } catch (SecurityException e) {
            Log.e(LOG_TAG, "An exception occurred trying to execute the CALL intent.", e);
        }
    }

    /**
     * Logs the phone number that was handed to the phone dialer.
     *
     * @param phoneNumber The phone number the user dialed
     */
    private void logPhoneNumber(String phoneNumber) {
        Log.d(LOG_TAG, "The user dialed: " + phoneNumber);
        // TODO Do whatever you want with the phone number (keep track of calls, create stats, etc...)
    }

}
