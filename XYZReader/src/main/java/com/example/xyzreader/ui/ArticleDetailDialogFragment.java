package com.example.xyzreader.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;

/**
 * Created by Edwin Ramirez Ventura on 6/23/2017.
 */

public class ArticleDetailDialogFragment extends DialogFragment implements LoaderManager
        .LoaderCallbacks<Cursor>, ArticleDetailFragment.FragmentAcions, View.OnTouchListener {

    public static final String TAG = ArticleDetailDialogFragment.class.getSimpleName();
    public static final double HEIGHT_PERCENTAGE = 0.75;
    public static final double WIDTH_PERCENTAGE = 0.82;
    private Cursor mCursor;
    private long mStartId;
    private int mPosition;
    private View mView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_article_detail, container, false);
        getLoaderManager().initLoader(0, null, this);
        if (savedInstanceState == null && getArguments() != null) {
            mStartId = getArguments().getLong(ArticleDetailFragment.ARG_ITEM_ID);
            mPosition = getArguments().getInt(ArticleDetailFragment.ARG_ITEM_POSITION);
            Log.d(TAG, "mStartId: " + mStartId);
            Log.d(TAG, "mPosition: " + mPosition);
        } else {
            return mView;
        }

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        View fragmentContainer = mView.findViewById(R.id.fragment_article_detail);
        fragmentContainer.getLayoutParams().height = (int) (height * HEIGHT_PERCENTAGE);
        fragmentContainer.getLayoutParams().width = (int) (width * WIDTH_PERCENTAGE);

        startFragment();

        mView.setOnTouchListener(this);

        return mView;
    }

    private void startFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_article_detail, ArticleDetailFragment.newInstance
                (mStartId, mPosition, this));
        fragmentTransaction.commit();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return ArticleLoader.newAllArticlesInstance(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursor = cursor;
        // Select the start ID
        if (mStartId > 0) {
            mCursor.moveToFirst();
            // TODO: optimize
            while (!mCursor.isAfterLast()) {
                if (mCursor.getLong(ArticleLoader.Query._ID) == mStartId) {
                    break;
                }
                mCursor.moveToNext();
            }
            mStartId = 0;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursor = null;
    }

    @Override
    public void homeSelected(View view) {

        closeDialog();
    }


    private void closeDialog() {
        AlphaAnimation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(390);
        mView.setAnimation(fadeOut);
        mView.startAnimation(fadeOut);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        closeDialog();
        return true;
    }
}
