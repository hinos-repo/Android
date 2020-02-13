package com.mysample.retrotest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mysample.retrotest.MainActivity;
import com.mysample.retrotest.R;

public class WaitFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_wait, container, false);
        v.setAlpha(0.5F);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    Toast.makeText(getActivity(), "ㅎㅎ", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });
        return v;
    }
}
