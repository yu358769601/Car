package com.qichen.ruida.imageviewer.View;

import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Provided default implementation of GestureDetector.OnDoubleTapListener, to be overriden with custom behavior, if needed
 * <p>&nbsp;</p>
 * To be used via {@link ImageViewerAttacher.co.senab.photoview.PhotoViewAttacher#setOnDoubleTapListener(GestureDetector.OnDoubleTapListener)}
 */
public class DefaultOnDoubleTapListener implements GestureDetector.OnDoubleTapListener {

    private ImageViewerAttacher imageViewAttacher;

    /**
     * Default constructor
     *
     * @param photoViewAttacher PhotoViewAttacher to bind to
     */
    public DefaultOnDoubleTapListener(ImageViewerAttacher photoViewAttacher) {
        setPhotoViewAttacher(photoViewAttacher);
    }

    /**
     * Allows to change PhotoViewAttacher within range of single instance
     *
     * @param newPhotoViewAttacher PhotoViewAttacher to bind to
     */
    public void setPhotoViewAttacher(ImageViewerAttacher newPhotoViewAttacher) {
        this.imageViewAttacher = newPhotoViewAttacher;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (this.imageViewAttacher == null)
            return false;

        ImageView imageView = imageViewAttacher.getImageView();

        if (null != imageViewAttacher.getOnImageTapListener()) {
            final RectF displayRect = imageViewAttacher.getDisplayRect();

            if (null != displayRect) {
                final float x = e.getX(), y = e.getY();

                // Check to see if the user tapped on the photo
                if (displayRect.contains(x, y)) {

                    float xResult = (x - displayRect.left)
                            / displayRect.width();
                    float yResult = (y - displayRect.top)
                            / displayRect.height();

                    imageViewAttacher.getOnImageTapListener().onImageTap(imageView, xResult, yResult);
                    return true;
                }
            }
        }
        if (null != imageViewAttacher.getOnViewTapListener()) {
        	imageViewAttacher.getOnViewTapListener().onViewTap(imageView, e.getX(), e.getY());
        }

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent ev) {
        if (imageViewAttacher == null)
            return false;

        try {
            float scale = imageViewAttacher.getScale();
            float x = ev.getX();
            float y = ev.getY();

            if (scale < imageViewAttacher.getMediumScale()) {
            	imageViewAttacher.setScale(imageViewAttacher.getMediumScale(), x, y, true);
            } else if (scale >= imageViewAttacher.getMediumScale() && scale < imageViewAttacher.getMaximumScale()) {
            	imageViewAttacher.setScale(imageViewAttacher.getMaximumScale(), x, y, true);
            } else {
            	imageViewAttacher.setScale(imageViewAttacher.getMinimumScale(), x, y, true);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // Can sometimes happen when getX() and getY() is called
        }

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        // Wait for the confirmed onDoubleTap() instead
        return false;
    }

}
