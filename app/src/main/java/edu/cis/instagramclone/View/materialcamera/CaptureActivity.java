package edu.cis.instagramclone.View.materialcamera;

import android.app.Fragment;

import androidx.annotation.NonNull;

import edu.cis.instagramclone.View.materialcamera.internal.BaseCaptureActivity;
import edu.cis.instagramclone.View.materialcamera.internal.CameraFragment;

public class CaptureActivity extends BaseCaptureActivity {

  @Override
  @NonNull
  public Fragment getFragment() {
    return CameraFragment.newInstance();
  }
}
