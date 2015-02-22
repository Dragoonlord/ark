package csc394.ark;

/**
 * Created by Daniel on 1/30/2015.
 */

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    static private Camera mCamera = null;
    int mCameraRotation;

    public CameraPreview(Context context, int cameraRotation) {
        super(context);
        mCameraRotation= cameraRotation;

        try {
            if(mCamera == null) mCamera = Camera.open();
            else
            {
                mCamera.stopPreview();
            }
        }
        catch (Exception e){
            Log.d(getClass().getSimpleName(), "Error getting camera instance: " + e.getMessage());
        }

        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(getClass().getSimpleName(), "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            //mCamera.stopPreview();
            //mCamera.release();
        } catch (Exception e){
            Log.d(getClass().getSimpleName(), "Error releasing camera: " + e.getMessage());
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        if (mHolder.getSurface() == null){
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            Log.d(getClass().getSimpleName(), "Error stopping camera preview: " + e.getMessage());
        }

        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPreviewSize(800, 480);
        mCamera.setParameters(parameters);

        mCamera.setDisplayOrientation(mCameraRotation);

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e){
            Log.d(getClass().getSimpleName(), "Error starting camera preview: " + e.getMessage());
        }

    }
}