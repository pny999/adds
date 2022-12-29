package com.example.adds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    AdView myAdd ;
    private InterstitialAd mInterstitialAd;
    RewardedAd mRewardedAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                Toast.makeText(MainActivity.this, "ADD SDK Initialize", Toast.LENGTH_SHORT).show();
            }
        });
        RequestConfiguration configuration = new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("862859032808426" , "A000006DEA31AA")).build();
        MobileAds.setRequestConfiguration(configuration);

        myAdd = findViewById(R.id.adView);

        interstatialAddInMemory();

        rewardedAddInMemory();

    }

    private void rewardedAddInMemory() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mRewardedAd = null;
                        Toast.makeText(MainActivity.this, loadAdError.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Toast.makeText(MainActivity.this, "Rewarded Add Loaded in Memory", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void rewarded(View v){
        if (mRewardedAd != null) {
            mRewardedAd.show(MainActivity.this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    Toast.makeText(MainActivity.this, "You Have Earned Reward", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void interstatialAddInMemory() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
//                        mInterstitialAd.show(MainActivity.this);
                        Toast.makeText(MainActivity.this, "FullScreen Add in Memory", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Toast.makeText(MainActivity.this, loadAdError.getMessage(), Toast.LENGTH_SHORT).show();
                        mInterstitialAd = null;
                    }
                });
    }


    public void banner(View v){
        AdRequest adRequest = new AdRequest.Builder().build();
        myAdd.loadAd(adRequest);
    }

    public void fullScreen(View v) {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(MainActivity.this);
        }
    }
}