package com.mysample.retrotest;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 프래그먼트를 엑티비티 위에 올려서 터치 이벤트 막기
 */

public class MainActivity extends AppCompatActivity
{
    long m_nReservedTime = 0;
    ReservedTimer m_hReservedHandler;
    TextView textView;
    ViewGroup MainContainer;

    FragmentTransaction transaction;
    WaitFragment fragment;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = new WaitFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.MainContainer, fragment).commit();

        m_nReservedTime = System.currentTimeMillis() + 1000 * 30;
        m_hReservedHandler = new ReservedTimer();
        m_hReservedHandler.sendEmptyMessage(1 * 1000);

        initViewSetting();
    }

    private void initViewSetting()
    {
        MainContainer = findViewById(R.id.MainContainer);
        textView = findViewById(R.id.textView);
        textView.setOnClickListener(m_hClickListener);
        recyclerView = findViewById(R.id.recyclerView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(m_hClickListener);
    }

    View.OnClickListener m_hClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.button:
                    showToast("gg");
                    break;
                case R.id.textView:
                    showToast(textView.getText().toString());
                    break;
            }
        }
    };

    private void showToast(String strMessage)
    {
        Toast.makeText(this, strMessage, Toast.LENGTH_SHORT).show();
    }

    class ReservedTimer extends Handler
    {
        public void handleMessage(Message msg)
        {
            long nCur = System.currentTimeMillis();

            if(m_nReservedTime <= nCur)
            {
                clearReservedTime();
                return;
            }
            else
            {
                int nRemain = (int)(m_nReservedTime - nCur);
                String strText;

                nRemain /= 1000;

                strText = String.format("%d:%02d", nRemain/60, nRemain%60);
                textView.setText(strText);
                m_hReservedHandler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    }

    private void clearReservedTime()
    {
        m_nReservedTime = 0;
        m_hReservedHandler.removeMessages(0);
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.MainContainer)).commit();
        MainContainer.setAlpha(1F);
    }
}
