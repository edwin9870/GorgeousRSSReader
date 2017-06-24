package com.example.xyzreader.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;

/**
 * Created by Edwin Ramirez Ventura on 6/23/2017.
 */

public class ArticleDetailDialogFragment extends DialogFragment implements LoaderManager
        .LoaderCallbacks<Cursor> {

    public static final String TAG = ArticleDetailDialogFragment.class.getSimpleName();
    private Cursor mCursor;
    private long mStartId;
    private int mPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_article_detail, container, false);
        getLoaderManager().initLoader(0, null, this);
        if (savedInstanceState == null) {
            if (getArguments() != null) {
                mStartId = getArguments().getLong(ArticleDetailFragment.ARG_ITEM_ID);
                mPosition = getArguments().getInt(ArticleDetailFragment.ARG_ITEM_POSITION);
                Log.d(TAG, "mStartId: "+ mStartId);
                Log.d(TAG, "mPosition: "+ mPosition);
            }
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_article_detail, ArticleDetailFragment.newInstance(mStartId, mPosition));
        fragmentTransaction.commit();
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
}
