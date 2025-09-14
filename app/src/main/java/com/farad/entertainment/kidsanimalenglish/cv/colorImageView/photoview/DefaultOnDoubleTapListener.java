package com.farad.entertainment.kidsanimalenglish.cv.colorImageView.photoview;

import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Provided default implementation of GestureDetector.OnDoubleTapListener, to be overriden with custom behavior, if needed
 * <p>&nbsp;</p>
 */
public class DefaultOnDoubleTapListener implements GestureDetector.OnDoubleTapListener {

    private PhotoViewAttacker photoViewAttacker;

    /**
     * Default constructor
     *
     * @param photoViewAttacker PhotoViewAttacher to bind to
     */
    public DefaultOnDoubleTapListener(PhotoViewAttacker photoViewAttacker) {
        setPhotoViewAttacher(photoViewAttacker);
    }

    /**
     * Allows to change PhotoViewAttacher within range of single instance
     *
     * @param newPhotoViewAttacker PhotoViewAttacher to bind to
     */
    public void setPhotoViewAttacher(PhotoViewAttacker newPhotoViewAttacker) {
        this.photoViewAttacker = newPhotoViewAttacker;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (this.photoViewAttacker == null)
            return false;

        ImageView imageView = photoViewAttacker.getImageView();

        if (null != photoViewAttacker.getOnPhotoTapListener()) {
            final RectF displayRect = photoViewAttacker.getDisplayRect();

            if (null != displayRect) {
                final float x = e.getX(), y = e.getY();

                // Check to see if the user tapped on the photo
                if (displayRect.contains(x, y)) {

                    float xResult = (x - displayRect.left)
                            / displayRect.width();
                    float yResult = (y - displayRect.top)
                            / displayRect.height();

                    photoViewAttacker.getOnPhotoTapListener().onPhotoTap(imageView, xResult, yResult);
                    return true;
                }
            }
        }
        if (null != photoViewAttacker.getOnViewTapListener()) {
            photoViewAttacker.getOnViewTapListener().onViewTap(imageView, e.getX(), e.getY());
        }

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent ev) {
        if (photoViewAttacker == null)
            return false;

        try {
            float scale = photoViewAttacker.getScale();
            float x = ev.getX();
            float y = ev.getY();

            if (scale < photoViewAttacker.getMediumScale()) {
                photoViewAttacker.setScale(photoViewAttacker.getMediumScale(), x, y, true);
            } else if (scale >= photoViewAttacker.getMediumScale() && scale < photoViewAttacker.getMaximumScale()) {
                photoViewAttacker.setScale(photoViewAttacker.getMaximumScale(), x, y, true);
            } else {
                photoViewAttacker.setScale(photoViewAttacker.getMinimumScale(), x, y, true);
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
