package amel.hog;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class MainActivity extends Activity implements CvCameraViewListener2
{

  private String TAG = "OpenCV";
  CameraBridgeViewBase mOpenCvCameraView;


  static public native String readFile();

  private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
    @Override
    public void onManagerConnected(int status) {
      switch (status) {
        case LoaderCallbackInterface.SUCCESS: {

          // Load native library after(!) OpenCV initialization
          Log.i(TAG, "OpenCV loaded successfully");
          System.loadLibrary("ndk_modules");
          String text = readFile();

          mOpenCvCameraView.enableView();
        }
        break;
        default: {
          super.onManagerConnected(status);
        }
        break;
      }
    }
  };



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.camera_view);
    mOpenCvCameraView.setVisibility(CameraBridgeViewBase.VISIBLE);
    mOpenCvCameraView.setCvCameraViewListener(this);
    //mOpenCvCameraView.setMaxFrameSize(512, 512);

  }

  @Override
  public void onPause() {
    super.onPause();
    if (mOpenCvCameraView != null) {
      mOpenCvCameraView.disableView();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    if (!OpenCVLoader.initDebug()) {
      Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
      OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_1_0, this, mLoaderCallback);
    } else {
      Log.d(TAG, "OpenCV library found inside package. Using it!");
      mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
    }
  }

  @Override
  public void onCameraViewStarted(int width, int height) {
   // mOpenCvCameraView.setMaxFrameSize(height, width);
  }

  @Override
  public void onCameraViewStopped() {


  }

  @Override
  public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
    Mat rgb_image = inputFrame.rgba();

    return rgb_image;
  }
}
