package com.fitness.tracking.domain.services.camera;

import android.hardware.Camera;

import androidx.annotation.Nullable;

import com.google.android.gms.common.images.Size;

/**
 * Stores a preview size and a corresponding same-aspect-ratio picture size. To avoid distorted
 * preview images on some devices, the picture size must be set to a size that is the same aspect
 * ratio as the preview size or the preview may end up being distorted. If the picture size is
 * null, then there is no picture size with the same aspect ratio as the preview size.
 */
public class SizePair {
    public final Size preview;
    @Nullable
    public final Size picture;

    SizePair(Camera.Size previewSize, @Nullable Camera.Size pictureSize) {
        preview = new Size(previewSize.width, previewSize.height);
        picture = pictureSize != null ? new Size(pictureSize.width, pictureSize.height) : null;
    }

    public SizePair(Size previewSize, @Nullable Size pictureSize) {
        preview = previewSize;
        picture = pictureSize;
    }
}
