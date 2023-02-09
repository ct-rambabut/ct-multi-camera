package com.example.multicameralibrary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CameraSettingsBottomSheet extends BottomSheetDialogFragment {

    Switch swWatermark, swAddress, swLatLng, swTime, swName, swGuideBox;
    Spinner spWatermarkPosition, spAspectRatio;
    Button btnApply;
    MyListener myListener;

    public CameraSettingsBottomSheet(MyListener listener) {
        myListener = listener;
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.camera_settings_bottom_sheet, container, false);
        swWatermark = v.findViewById(R.id.swWatermark);
        swAddress = v.findViewById(R.id.swAddress);
        swLatLng = v.findViewById(R.id.swLatLng);
        swTime = v.findViewById(R.id.swTime);
        swName = v.findViewById(R.id.swName);
        swGuideBox = v.findViewById(R.id.swGuideBox);
        spWatermarkPosition = v.findViewById(R.id.spWatermarkPosition);
        spAspectRatio = v.findViewById(R.id.spAspectRatio);
        btnApply = v.findViewById(R.id.btnApply);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swWatermark.setChecked(Pref.getIn(requireContext()).getCamShowWaterMark());
        swAddress.setChecked(Pref.getIn(requireContext()).getCamShowAddress());
        swLatLng.setChecked(Pref.getIn(requireContext()).getCamShowLatLng());
        swTime.setChecked(Pref.getIn(requireContext()).getCamShowTime());
        swName.setChecked(Pref.getIn(requireContext()).getCamShowName());
        swGuideBox.setChecked(Pref.getIn(requireContext()).getCamShowGuideBox());

        btnApply.setOnClickListener(view1 -> {
            myListener.applyListener();
            dismiss();
        });

        swWatermark.setOnCheckedChangeListener((compoundButton, b) -> {
            Pref.getIn(requireContext()).setCamShowWaterMark(b);
        });
        swAddress.setOnCheckedChangeListener((compoundButton, b) -> {
            Pref.getIn(requireContext()).setCamShowAddress(b);
        });
        swLatLng.setOnCheckedChangeListener((compoundButton, b) -> {
            Pref.getIn(requireContext()).setCamShowLatLng(b);
        });
        swTime.setOnCheckedChangeListener((compoundButton, b) -> {
            Pref.getIn(requireContext()).setCamShowTime(b);
        });
        swName.setOnCheckedChangeListener((compoundButton, b) -> {
            Pref.getIn(requireContext()).setCamShowName(b);
        });
        swGuideBox.setOnCheckedChangeListener((compoundButton, b) -> {
            Pref.getIn(requireContext()).setCamShowGuideBox(b);
        });
    }

}
