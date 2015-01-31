package com.grarak.kerneladiutor.fragments.kernel;

import android.os.Bundle;

import com.grarak.kerneladiutor.R;
import com.grarak.kerneladiutor.elements.CardViewItem;
import com.grarak.kerneladiutor.elements.PopupCardItem;
import com.grarak.kerneladiutor.elements.RecyclerViewFragment;
import com.grarak.kerneladiutor.utils.kernel.GPU;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by willi on 26.12.14.
 */
public class GPUFragment extends RecyclerViewFragment implements PopupCardItem.DPopupCard.OnDPopupCardListener {

    private CardViewItem.DCardView mCur2dFreqCard, mCurFreqCard;

    private PopupCardItem.DPopupCard mMax2dFreqCard, mMaxFreqCard;

    private PopupCardItem.DPopupCard m2dGovernorCard, mGovernorCard;

    @Override
    public void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        curFreqInit();
        maxFreqInit();
        governorInit();
    }

    private void curFreqInit() {
        if (GPU.hasGpu2dCurFreq()) {
            mCur2dFreqCard = new CardViewItem.DCardView();
            mCur2dFreqCard.setTitle(getString(R.string.gpu_2d_cur_freq));

            addView(mCur2dFreqCard);
        }

        if (GPU.hasGpuCurFreq()) {
            mCurFreqCard = new CardViewItem.DCardView();
            mCurFreqCard.setTitle(getString(R.string.gpu_cur_freq));

            addView(mCurFreqCard);
        }
    }

    private void maxFreqInit() {
        if (GPU.hasGpu2dMaxFreq() && GPU.hasGpu2dFreqs()) {
            List<String> freqs = new ArrayList<>();
            for (int freq : GPU.getGpu2dFreqs())
                freqs.add(freq / 1000000 + getString(R.string.mhz));

            mMax2dFreqCard = new PopupCardItem.DPopupCard(freqs);
            mMax2dFreqCard.setTitle(getString(R.string.gpu_2d_max_freq));
            mMax2dFreqCard.setDescription(getString(R.string.gpu_2d_max_freq_summary));
            mMax2dFreqCard.setItem(GPU.getGpu2dMaxFreq() / 1000000 + getString(R.string.mhz));
            mMax2dFreqCard.setOnDPopupCardListener(this);

            addView(mMax2dFreqCard);
        }

        if (GPU.hasGpuMaxFreq() && GPU.hasGpuFreqs()) {
            List<String> freqs = new ArrayList<>();
            for (int freq : GPU.getGpuFreqs())
                freqs.add(freq / 1000000 + getString(R.string.mhz));

            mMaxFreqCard = new PopupCardItem.DPopupCard(freqs);
            mMaxFreqCard.setTitle(getString(R.string.gpu_max_freq));
            mMaxFreqCard.setDescription(getString(R.string.gpu_max_freq_summary));
            mMaxFreqCard.setItem(GPU.getGpuMaxFreq() / 1000000 + getString(R.string.mhz));
            mMaxFreqCard.setOnDPopupCardListener(this);

            addView(mMaxFreqCard);
        }
    }

    private void governorInit() {
        if (GPU.hasGpu2dGovernor()) {
            m2dGovernorCard = new PopupCardItem.DPopupCard(GPU.getGpu2dGovernors());
            m2dGovernorCard.setTitle(getString(R.string.gpu_2d_governor));
            m2dGovernorCard.setDescription(getString(R.string.gpu_2d_governor_summary));
            m2dGovernorCard.setItem(GPU.getGpu2dGovernor());
            m2dGovernorCard.setOnDPopupCardListener(this);

            addView(m2dGovernorCard);
        }

        if (GPU.hasGpuGovernor()) {
            mGovernorCard = new PopupCardItem.DPopupCard(GPU.getGpuGovernors());
            mGovernorCard.setTitle(getString(R.string.gpu_governor));
            mGovernorCard.setDescription(getString(R.string.gpu_governor_summary));
            mGovernorCard.setItem(GPU.getGpuGovernor());
            mGovernorCard.setOnDPopupCardListener(this);

            addView(mGovernorCard);
        }
    }

    @Override
    public void onItemSelected(PopupCardItem.DPopupCard dPopupCard, int position) {
        if (dPopupCard == mMax2dFreqCard)
            GPU.setGpu2dMaxFreq(GPU.getGpu2dFreqs().get(position), getActivity());
        if (dPopupCard == mMaxFreqCard)
            GPU.setGpuMaxFreq(GPU.getGpuFreqs().get(position), getActivity());
        if (dPopupCard == m2dGovernorCard)
            GPU.setGpu2dGovernor(GPU.getGpu2dGovernors().get(position), getActivity());
        if (dPopupCard == mGovernorCard)
            GPU.setGpuGovernor(GPU.getGpuGovernors().get(position), getActivity());
    }

    @Override
    public boolean onRefresh() {

        String MHZ = getString(R.string.mhz);

        if (mCur2dFreqCard != null)
            mCur2dFreqCard.setDescription(GPU.getGpu2dCurFreq() / 1000000 + MHZ);

        if (mCurFreqCard != null)
            mCurFreqCard.setDescription(GPU.getGpuCurFreq() / 1000000 + MHZ);

        return true;
    }
}