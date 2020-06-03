package io.blusalt.blusaltbiometricapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.LinkedList;

import io.blusalt.blusalt_biometric_sdk.CaptureComponentData;
import io.blusalt.blusalt_biometric_sdk.ui.FingerPrintCaptureListener;
import io.blusalt.blusalt_biometric_sdk.ui.fragments.FingerPrintScanFragment;

public class MainActivity extends AppCompatActivity implements FingerPrintCaptureListener {

    private RecyclerView list;
    Button start, m_btnFinishCapture;
    private LinkedList<CaptureComponentData> fingerPrints;
    private FingerPrintListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialisePrequistes();
        list = findViewById(R.id.listFingerPrint);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, RecyclerView.VERTICAL,
                false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        start = findViewById(R.id.buttonStart);
        m_btnFinishCapture = findViewById(R.id.buttonFinishCaptureSequence);
        m_btnFinishCapture.setOnClickListener(m_btnFinishCaptureListener);

        start.setOnClickListener(view -> {
            fingerPrints.clear();
            adapter.notifyDataSetChanged();
            FingerPrintScanFragment fragment = new FingerPrintScanFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.fingerprint_frame, fragment, "FINGER_PRINT");
            //fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();
            start.setVisibility(View.GONE);
           // m_btnFinishCapture.setVisibility(View.VISIBLE);
        });
    }

    /*
     * Handle click event on "Finish Capture" button
     */
    private Button.OnClickListener m_btnFinishCaptureListener = view -> {
       runOnUiThread(() -> {
           removeBiometricFragment();
        });
    };

    @Override
    public void removeBiometricFragment(){

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fingerprint_frame);
        if(fragment!=null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment).commitAllowingStateLoss();
        }
        //FingerPrintScanFragment.exitApp(this);

        start.setVisibility(View.VISIBLE);
        m_btnFinishCapture.setVisibility(View.GONE);
    }

    private void initialisePrequistes(){
        fingerPrints = new LinkedList<>();
        adapter = new FingerPrintListAdapter(fingerPrints, MainActivity.this);

    }
    @Override
    public void onBackPressed() {
            super.onBackPressed();
            //finish();

        //FingerPrintScanFragment.exitApp(this);
    }

    private void message( String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setBiometricResults(CaptureComponentData componentData) {
        fingerPrints.add(componentData);
        adapter.notifyItemChanged(0);
    }

    @Override
    public void onCaptureSequenceFinished(boolean isFinished) {
        if(isFinished){
            runOnUiThread(() ->  m_btnFinishCapture.setVisibility(View.VISIBLE));
        }
    }
}
