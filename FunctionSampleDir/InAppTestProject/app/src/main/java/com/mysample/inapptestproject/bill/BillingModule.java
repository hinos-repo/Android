package com.mysample.inapptestproject.bill;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.SkuDetails;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.gson.JsonObject;
import com.mysample.inapptestproject.common.MyConfig;
import com.mysample.inapptestproject.retrofit.NetRetrofit;
import com.mysample.inapptestproject.view.MainActivity;
import com.mysample.inapptestproject.R;
import com.mysample.inapptestproject.common.ComEtc;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingModule implements BillingProcessor.IBillingHandler {
    private MainActivity mainActivity;
    private BillingProcessor mBillingProcessor;

    private ArrayList<String> arrPurId; // 일반판매 아이디
    private ArrayList<String> arrSubId; //구독 아이디

    private List<SkuDetails> arr_whole;

    private boolean bListLoading = false;

    public List<SkuDetails> getArr_whole()
    {
        return arr_whole;
    }

    public boolean isbListLoading()
    {
        return bListLoading;
    }

    public BillingModule(MainActivity mainActivity, ArrayList<String> arrPur, ArrayList<String> arrSub) {
        this.mainActivity = mainActivity;
        this.arrPurId = arrPur;
        this.arrSubId = arrSub;
    }

    public void initBillingProcessor() {
        mBillingProcessor = new BillingProcessor(mainActivity, MyConfig.MY_DEV_KEY, this);
        // 아래와 차이는 기트허브 페이지에서 확인할 수 있다. 상황에 맞게 사용하면 됨.
        // mBilling Processor = BillingProcessor.newBillingProcessor(context, "rsa_key", this);
        // rsa_key는 개발자 콘솔에서 제공하는 id
    }

    public void purchaseProduct(String itemId) { // 아이템 구매 요청
        if(mBillingProcessor.isPurchased(itemId)) {
            Toast.makeText(mainActivity, "이미 구매하였습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean bSubscriptionCheck = false;
        for (int i = 0; i < arr_whole.size(); i++)
        {
            if(arr_whole.get(i).productId == itemId)
            {
                if(arr_whole.get(i).isSubscription == true)
                {
                    bSubscriptionCheck = true;
                }
            }
        }

        if(bSubscriptionCheck)
        {
            mBillingProcessor.subscribe(mainActivity, itemId);
        }
        else
        {
            mBillingProcessor.purchase(mainActivity, itemId);
        }

//        mBillingProcessor.purchase(mainActivity, itemId);
    }

    public void releaseBillingProcessor() {
        if(mBillingProcessor != null)
            mBillingProcessor.release();
    }

    public BillingProcessor getBillingProcessor() {
        return mBillingProcessor;
    }

    @Override
    public void onProductPurchased(@NonNull String id, @Nullable TransactionDetails transactionDetails)
    {
        Toast.makeText(mainActivity, "onProductPurchased open", Toast.LENGTH_SHORT).show();
        String strToken = transactionDetails.purchaseInfo.purchaseData.purchaseToken;
        String strProductId = transactionDetails.purchaseInfo.purchaseData.productId;

        // 아이템 구매 성공 시 호출.
        // 따라서 보상을 지급하든(광고 제거) 혹은 해당 아이템을 소비하든 해당 기능을 작성

        boolean isSubscription = false;
        for (int i = 0; i<arr_whole.size(); i++)
        {
            if(arr_whole.get(i).productId.equals(strProductId))
            {
                if (arr_whole.get(i).isSubscription)
                {
                    isSubscription = true;
                    break;
                }
            }
        }

        NetRetrofit.INSTANCE.getService().getBillingInfo(strProductId, strToken, MyConfig.MY_PACKAGE_NAME, isSubscription ? 2 : 1).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("BillingModule", "onResponse: " + response.body());
//                ComEtc.INSTANCE.writeLog(response.body().toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("BillingModule", "onResponse: " + t.getMessage());
            }
        });

//        mBillingProcessor.consumePurchase(strProductId);
    }



    @Override
    public void onPurchaseHistoryRestored() {
        Toast.makeText(mainActivity, "onPurchaseHistoryRestored", Toast.LENGTH_SHORT).show();
        // 구매 내역 복원 및 구매한 모든 PRODUCT ID 목록이 Google Play에서 로드 될 때 호출.
    }

    @Override
    public void onBillingError(int errCode, @Nullable Throwable throwable) {
        ComEtc.INSTANCE.writeLog(String.valueOf(errCode));
        // 구매 시 에러가 발생했을 때 처리
        if(errCode != com.anjlab.android.iab.v3.Constants.BILLING_RESPONSE_RESULT_USER_CANCELED) {
            // 사용자가 취소한 게 아니라면 에러 발생에 대해 사용자에게 고지하는 등의 처리
        }
    }

    @Override
    public void onBillingInitialized() {
        // 개발자 콘솔에서 등록한 아이템 아이디

        arr_whole = new ArrayList<>();
        List<SkuDetails> arrSubProduct = mBillingProcessor.getSubscriptionListingDetails(arrSubId);
        List<SkuDetails> arrPerProduct = mBillingProcessor.getPurchaseListingDetails(arrPurId);

        arr_whole.addAll(arrSubProduct);
        arr_whole.addAll(arrPerProduct);

        int j = -1;
        if (arr_whole == null || arr_whole.size() == 0)
        {
            return;
        }

        boolean bResult = mBillingProcessor.loadOwnedPurchasesFromGoogle(); // 소유하고 있는 구매 아이템 목록을 가져온다.
        bListLoading = true;

//        removeExistProduct();
    }

    private void removeExistProduct()
    {
        int j = 0;
        for (int i = 0; i<arr_whole.size(); i++)
        {
            boolean bSubscription = false;
            if(arr_whole.get(i).isSubscription)
            {
                bSubscription = true;
            }
            if(bSubscription)
            {
                if(isSubscribed(arr_whole.get(i).productId)){
                    j = i;
                }
            }
            else
            {
                if(isPurchased(arr_whole.get(i).productId)){
                    j = i;
                }
            }
        }

        if (j >= 0)
        {
            arr_whole.remove(j);
        }
    }

    public boolean doConsumePurchase(String strId)
    {
        return mBillingProcessor.consumePurchase(strId);
    }

    public boolean isPurchased(String strId)
    {
        boolean bResult = mBillingProcessor.loadOwnedPurchasesFromGoogle();
        List<String> arr = mBillingProcessor.listOwnedProducts();

        return mBillingProcessor.isPurchased(strId);
    }

    public boolean isSubscribed(String strId)
    {
        boolean bResult = mBillingProcessor.loadOwnedPurchasesFromGoogle();
        List<String> arr = mBillingProcessor.listOwnedSubscriptions();
        getOwnedSubscription(strId);
        return mBillingProcessor.isSubscribed(strId);
    }

    public boolean getOwnedSubscription(String strId)
    {
        TransactionDetails details = mBillingProcessor.getSubscriptionTransactionDetails(strId);
        if (details == null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public void sendTestData(String strId)
    {
        TransactionDetails details = mBillingProcessor.getSubscriptionTransactionDetails(strId);
        if (details == null)
        {
            Toast.makeText(mainActivity, "없음", Toast.LENGTH_SHORT).show();
            return;
        }
        NetRetrofit.INSTANCE.getService().getBillingInfo(details.purchaseInfo.purchaseData.productId, details.purchaseInfo.purchaseData.purchaseToken, details.purchaseInfo.purchaseData.packageName, 2).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("BillingModule", "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("BillingModule", "onResponse: " + t.getMessage());
            }
        });
    }

    public void cancelSubscribe(String strId)
    {
        boolean bResult = mBillingProcessor.loadOwnedPurchasesFromGoogle();
        TransactionDetails details = mBillingProcessor.getSubscriptionTransactionDetails(strId);

        if (details == null)
        {
            Toast.makeText(mainActivity, "취소할게 없음", Toast.LENGTH_SHORT).show();
            return;
        }

        NetRetrofit.INSTANCE.getService().getBillingInfo(details.purchaseInfo.purchaseData.productId, details.purchaseInfo.purchaseData.purchaseToken, details.purchaseInfo.purchaseData.packageName, 3).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                JsonObject object = response.body();
                int res_code = object.get("res_code").getAsInt();
                String res_message = object.get("res_msg").getAsString();
                Toast.makeText(mainActivity, res_message, Toast.LENGTH_SHORT).show();
                Log.d("BillingModule", "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("BillingModule", "onResponse: " + t.getMessage());
            }
        });
    }
}