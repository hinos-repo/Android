package com.mysample.inapptestproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.mysample.inapptestproject.bill.BillingModule;


import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    final static int PURCHASE       = 4;
    final static int SUBSCRIBE      = 5;

    private Context m_context;
    private List<SkuDetails> skuDetailsList;
    private BillingModule billingModule;
    private BillingProcessor billingProcessor;


    public ListAdapter(Context context, List<SkuDetails> list, BillingModule module) {
        this.m_context = context;
        this.skuDetailsList = list;
        this.billingModule = module;
        billingProcessor = billingModule.getBillingProcessor();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout llContainer;
        LinearLayout llRoot;
        TextView tvTitle;
        TextView tvPrice;
        Button btnPurchase;
        Button btnConfirm;
        Button btnConsume;
        Button btnCancel;
        Button btnSend;

        public ViewHolder(View v) {
            super(v);
            llContainer = v.findViewById(R.id.llContainer);
            llRoot = v.findViewById(R.id.llRoot);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvPrice = v.findViewById(R.id.tvPrice);
            btnPurchase = v.findViewById(R.id.btnPurchase);
            btnConfirm = v.findViewById(R.id.btnConfirm);
            btnConsume = v.findViewById(R.id.btnConsume);
            btnCancel = v.findViewById(R.id.btnCancel);
            btnSend = v.findViewById(R.id.btnSend);
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        if(skuDetailsList.get(position).isSubscription)
        {
            return SUBSCRIBE;
        }
        else
        {
            return PURCHASE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        if(viewType == PURCHASE)
        {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase, parent, false);
        }
        else
        {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscribe, parent, false);
        }


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder vh = (ViewHolder)holder;
        int viewType = vh.getItemViewType();
        final SkuDetails sd = skuDetailsList.get(position);
        vh.tvTitle.setText(sd.title);
        vh.tvPrice.setText(sd.priceText);

        vh.btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billingModule.purchaseProduct(sd.productId);
            }
        });
        vh.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bResult = false;
                if(sd.isSubscription)
                {
                    bResult = billingModule.isSubscribed(sd.productId);
                }
                else
                {
                    bResult = billingModule.isPurchased(sd.productId);
                }
                if (bResult)
                {
                    Toast.makeText(m_context, "이미 구매한 상품입니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(m_context, "구매할 수 있습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        vh.btnConsume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bResult = billingModule.doConsumePurchase(sd.productId);
                if (bResult)
                {
                    Toast.makeText(m_context, "소비완료", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(m_context, "소비실패", Toast.LENGTH_SHORT).show();
                }
            }
        });

        vh.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billingModule.cancelSubscribe(sd.productId);
            }
        });

        vh.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billingModule.sendTestData(sd.productId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return skuDetailsList.size();
    }
}