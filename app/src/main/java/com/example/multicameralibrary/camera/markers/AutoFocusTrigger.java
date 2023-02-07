package com.example.multicameralibrary.camera.markers;

import com.example.multicameralibrary.camera.CameraView;
import com.example.multicameralibrary.camera.gesture.GestureAction;

/**
 * Gives information about what triggered the autofocus operation.
 */
public enum AutoFocusTrigger {

    /**
     * Autofocus was triggered by {@link GestureAction#AUTO_FOCUS}.
     */
    GESTURE,

    /**
     * Autofocus was triggered by the {@link CameraView#startAutoFocus(float, float)} method.
     */
    METHOD
}
