package com.ascendant.criminalintent2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment {


    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;
    private static String TAG = "CHECK_FOR_RETURN";
    public static final String EXTRA_CRIME_ID = "com.ascendant.android.criminalIntent2.crimeid";
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    public CrimeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity()).saveCrimes();
    }

    public void updateDate() {
        mDateButton.setText(mCrime.getDate().toString());
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            private String textBefore,textDuring,textafter, changedText;

            public void onTextChanged(
                    CharSequence c, int start, int before, int count) {
                textDuring = c.toString();
                Boolean isReturn;
                Log.d(TAG,"text during is : " + textDuring);
                if (textBefore.length() <= textDuring.length()){
                    changedText = textDuring.substring(textBefore.length(),textDuring.length());
                    Log.d(TAG,"text changed is : \""+ changedText + "\"");
                    isReturn = changedText.compareTo("\n") == 0;
                }else {
                    isReturn = false;
                }

                if (isReturn) {
                    Log.d(TAG, "changed text is return");
                    mCrime.setTitle(textBefore);
                    Log.d(TAG,"finishing");
                    getActivity().finish();
                }else{
                    mCrime.setTitle(textDuring);
                }

            }

            public void beforeTextChanged(
                    CharSequence c, int start, int count, int after) {
                textBefore = (String)c.toString();
                Log.d(TAG, "text before is : " + textBefore);
                // This space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // This one too
                textafter = c.toString();
                Log.d(TAG, "text after is : " + textafter);
            }

        });

        mDateButton = (Button)v.findViewById(R.id.crime_date);
        updateDate();

        mDateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate() );
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);

            }
        });

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Set the crime's solved property
                mCrime.setSolved(isChecked);
            }
        });
        return v;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent i){
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Log.d("TAG","request code good");
            Date intentDate = (Date)i
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            Log.d("TAG","date gotten");
            mCrime.setDate(intentDate);
            Log.d("TAG","crime set");
            updateDate();
            Log.d("TAG","date updated");

        }
    }

    public static CrimeFragment newInstance(UUID crimeId)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID,crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (NavUtils.getParentActivityName(getActivity()) != null){
                    NavUtils.navigateUpFromSameTask(getActivity());
                }

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
