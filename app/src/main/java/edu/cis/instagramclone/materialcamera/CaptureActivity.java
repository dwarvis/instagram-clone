package edu.cis.instagramclone.materialcamera;

import android.app.Fragment;

import androidx.annotation.NonNull;

import edu.cis.instagramclone.materialcamera.internal.BaseCaptureActivity;
import edu.cis.instagramclone.materialcamera.internal.CameraFragment;

public class CaptureActivity extends BaseCaptureActivity {

  @Override
  @NonNull
  public Fragment getFragment() {
    return CameraFragment.newInstance();
  }
}
