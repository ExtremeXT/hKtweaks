/*
 * Copyright (C) 2015-2016 Willi Ye <williye97@gmail.com>
 *
 * This file is part of Kernel Adiutor.
 *
 * Kernel Adiutor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kernel Adiutor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kernel Adiutor.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.moro.kerneladiutor.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.moro.kerneladiutor.R;
import com.moro.kerneladiutor.activities.tools.profile.ProfileActivity;
import com.moro.kerneladiutor.fragments.kernel.BatteryFragment;
import com.moro.kerneladiutor.fragments.kernel.CPUVoltageCl0Fragment;
import com.moro.kerneladiutor.fragments.kernel.CPUVoltageCl1Fragment;
import com.moro.kerneladiutor.fragments.kernel.CPUFragment;
import com.moro.kerneladiutor.fragments.kernel.CPUHotplugFragment;
import com.moro.kerneladiutor.fragments.kernel.EntropyFragment;
import com.moro.kerneladiutor.fragments.kernel.GPUFragment;
import com.moro.kerneladiutor.fragments.kernel.IOFragment;
import com.moro.kerneladiutor.fragments.kernel.KSMFragment;
import com.moro.kerneladiutor.fragments.kernel.LEDFragment;
import com.moro.kerneladiutor.fragments.kernel.LMKFragment;
import com.moro.kerneladiutor.fragments.kernel.MiscFragment;
import com.moro.kerneladiutor.fragments.kernel.ScreenFragment;
import com.moro.kerneladiutor.fragments.kernel.SoundFragment;
import com.moro.kerneladiutor.fragments.kernel.ThermalFragment;
import com.moro.kerneladiutor.fragments.kernel.VMFragment;
import com.moro.kerneladiutor.fragments.kernel.WakeFrament;
import com.moro.kerneladiutor.utils.Prefs;

import java.util.HashMap;

/**
 * Created by willi on 03.05.16.
 */
public class ApplyOnBootFragment extends BaseFragment {

    public static final String CPU = "cpu_onboot";
    public static final String CPU_CL0_VOLTAGE = "cpucl0voltage_onboot";
    public static final String CPU_CL1_VOLTAGE = "cpucl1voltage_onboot";
    public static final String CPU_HOTPLUG = "cpuhotplug_onboot";
    public static final String THERMAL = "thermal_onboot";
    public static final String GPU = "gpu_onboot";
    public static final String SCREEN = "screen_onboot";
    public static final String WAKE = "wake_onboot";
    public static final String SOUND = "sound_onboot";
    public static final String BATTERY = "battery_onboot";
    public static final String LED = "led_onboot";
    public static final String IO = "io_onboot";
    public static final String KSM = "ksm_onboot";
    public static final String LMK = "lmk_onboot";
    public static final String VM = "vm_onboot";
    public static final String ENTROPY = "entropy_onboot";
    public static final String MISC = "misc_onboot";

    private static final HashMap<Class, String> sAssignments = new HashMap<>();

    static {
        sAssignments.put(CPUFragment.class, CPU);
        sAssignments.put(CPUVoltageCl0Fragment.class, CPU_CL0_VOLTAGE);
        sAssignments.put(CPUVoltageCl1Fragment.class, CPU_CL1_VOLTAGE);
        sAssignments.put(CPUHotplugFragment.class, CPU_HOTPLUG);
        sAssignments.put(ThermalFragment.class, THERMAL);
        sAssignments.put(GPUFragment.class, GPU);
        sAssignments.put(ScreenFragment.class, SCREEN);
        sAssignments.put(WakeFrament.class, WAKE);
        sAssignments.put(SoundFragment.class, SOUND);
        sAssignments.put(BatteryFragment.class, BATTERY);
        sAssignments.put(LEDFragment.class, LED);
        sAssignments.put(IOFragment.class, IO);
        sAssignments.put(KSMFragment.class, KSM);
        sAssignments.put(LMKFragment.class, LMK);
        sAssignments.put(VMFragment.class, VM);
        sAssignments.put(EntropyFragment.class, ENTROPY);
        sAssignments.put(MiscFragment.class, MISC);
    }

    public static String getAssignment(Class fragment) {
        if (!sAssignments.containsKey(fragment)) {
            throw new RuntimeException("Assignment key does not exists: " + fragment.getSimpleName());
        }
        return sAssignments.get(fragment);
    }

    public static ApplyOnBootFragment newInstance(RecyclerViewFragment recyclerViewFragment) {
        Bundle args = new Bundle();
        args.putString("category", getAssignment(recyclerViewFragment.getClass()));
        ApplyOnBootFragment fragment = new ApplyOnBootFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() instanceof ProfileActivity) {
            View rootView = inflater.inflate(R.layout.fragment_description, container, false);

            TextView title = (TextView) rootView.findViewById(R.id.title);
            TextView summary = (TextView) rootView.findViewById(R.id.summary);

            title.setText(getString(R.string.apply_on_boot));
            summary.setText(getString(R.string.apply_on_boot_not_available));

            return rootView;
        } else {
            View rootView = inflater.inflate(R.layout.fragment_apply_on_boot, container, false);

            final String category = getArguments().getString("category");
            SwitchCompat switcher = (SwitchCompat) rootView.findViewById(R.id.switcher);
            switcher.setChecked(Prefs.getBoolean(category, false, getActivity()));
            switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Prefs.saveBoolean(category, isChecked, getActivity());
                }
            });
            return rootView;
        }
    }

}
