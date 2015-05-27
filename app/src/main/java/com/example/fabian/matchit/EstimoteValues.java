package com.example.fabian.matchit;

import android.app.Application;

import com.estimote.sdk.EstimoteSDK;

/**
 * Main {@link Application} object for Demos. It configures EstimoteSDK.
 *
 * @author wiktor@estimote.com (Wiktor Gworek)
 */
public class EstimoteValues extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initializes Estimote SDK with your App ID and App Token from Estimote Cloud.
    // You can find your App ID and App Token in the
    // Apps section of the Estimote Cloud (http://cloud.estimote.com).
    EstimoteSDK.initialize(this, "matchapp-cash---carry", "f9410e2b166c4bcc12385476b204dfe6");

    // Configure verbose debug logging.
    EstimoteSDK.enableDebugLogging(true);
  }
}
