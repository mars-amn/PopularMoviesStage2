package nanodegree.udacity.popularmovies;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nanodegree.udacity.popularmovies.database.MoviesContract;

/**
 * Created by AbdullahAtta on 5/12/2018.
 */
public class FavoriteMovieDialog {
    @BindView(R.id.dialogTitleTextView)
    TextView mDialogTextView;
    @BindView(R.id.dialogNegativeButton)
    Button mNegativeButton;
    @BindView(R.id.dialogPositiveButton)
    Button mPositiveButton;
    @BindView(R.id.dialogPosterImageView)
    ImageView mPosterDialogImageView;
    private int mMovieId;
    private Dialog dialog;
    private Activity mActivity;

    public void showRemoveFavoriteViewDialog(final Activity activity, int id, String posterUrl) {
        mActivity = activity;
        dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.delete_dialog);
        ButterKnife.bind(this, dialog);
        mMovieId = id;
        mDialogTextView.setText(R.string.delete_movie_alert_dialog_message);
        GlideApp.with(mActivity)
                .load(posterUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(mPosterDialogImageView);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @OnClick(R.id.dialogPositiveButton)
    public void onPositiveButtonClick() {
        Uri movieId = MoviesContract.MoviesEntry.CONTENT_URI.buildUpon()
                .appendEncodedPath(Integer.toString(mMovieId))
                .build();

        mActivity.getContentResolver().delete(movieId, null, null);
        mActivity.finish();
    }

    @OnClick(R.id.dialogNegativeButton)
    public void onNegativeButtonClick() {
        dialog.dismiss();
    }
}
