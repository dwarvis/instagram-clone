package edu.cis.instagramclone.View.materialcamera;

import android.app.Fragment;

import androidx.annotation.NonNull;

import edu.cis.instagramclone.View.materialcamera.internal.BaseCaptureActivity;
import edu.cis.instagramclone.View.materialcamera.internal.Camera2Fragment;


public class CaptureActivity2 extends BaseCaptureActivity {

  @Override
  @NonNull
  public Fragment getFragment() {
    return Camera2Fragment.newInstance();
  }
}
