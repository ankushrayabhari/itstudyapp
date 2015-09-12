package nf.co.arcanechicken.fblaproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import co.nf.arcanechicken.fblaproject.R;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.facebook.AppEventsLogger;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FriendPickerFragment;
import com.facebook.widget.LoginButton;
import com.facebook.widget.PickerFragment;
import com.facebook.widget.PlacePickerFragment;
import com.facebook.widget.ProfilePictureView;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.WebRequestor;
import com.restfb.types.FacebookType;
import com.restfb.types.User;

public class FBLAProjectLauncher extends FragmentActivity implements ApplicationListener {

	private static final String PERMISSION = "publish_actions";
	private static final String PERMISSION_1 = "create_event";
	private final String PENDING_ACTION_BUNDLE_KEY = "co.nf.arcanechicken.fblaproject:PendingAction";

	private FacebookClient fc;
	private ImageButton learnButton;
	private ImageButton enterGameButton, eventButton, postButton;
	private LoginButton loginButton;
	private PendingAction pendingAction = PendingAction.NONE;
	private GraphUser user;
	private GraphPlace place;
	private List<GraphUser> tags;
	private TextView points;
	private boolean canPresentShareDialog;

	private enum PendingAction {
		NONE, POST_STATUS_UPDATE
	}

	private UiLifecycleHelper uiHelper;

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
		@Override
		public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
			Log.d("co.nf.arcanechicken", String.format("Error: %s", error.toString()));
		}

		@Override
		public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
			Log.d("co.nf.arcanechicken", "Success!");
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);

			pendingAction = PendingAction.valueOf(name);
		}

		setContentView(R.layout.main);

		
		loginButton = (LoginButton) findViewById(R.id.login_button);
		
		loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(GraphUser user) {

			
				FBLAProjectLauncher.this.user = user;
				WebRequestor wr = new DefaultWebRequestor();

				if (user != null) {
					try {
						WebRequestor.Response response = wr
								.executeGet("https://graph.facebook.com/" + user.getId());

					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {

				}
				if (!isNetworkAvailable()) {
					getOuter().findViewById(R.id.enterText).setVisibility(View.VISIBLE);

				}

				updateUI();
				handlePendingAction();

			}
		});
		loginButton.setPublishPermissions(Arrays.asList("create_event", "publish_actions"));

		learnButton = (ImageButton) findViewById(R.id.learnButton);
		learnButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FBLAProjectLauncher.this, FBLAProjectLearnLauncher.class);
				FBLAProjectLauncher.this.startActivity(intent);
			}
		});
		eventButton = (ImageButton) findViewById(R.id.eventButton);
		
		eventButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FBLAProjectLauncher.this, FBLAEventLauncher.class);
				Session session = Session.getActiveSession();
				session.requestNewPublishPermissions(new Session.NewPermissionsRequest(FBLAProjectLauncher.this,
						PERMISSION_1));
				FBLAProjectLauncher.this.startActivity(intent);
			}
		});
		postButton = (ImageButton) findViewById(R.id.askButton);
		
		postButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FBLAProjectLauncher.this, FBLAProjectPostLauncher.class);
				Session session = Session.getActiveSession();
				session.requestNewPublishPermissions(new Session.NewPermissionsRequest(FBLAProjectLauncher.this,
						PERMISSION));
				FBLAProjectLauncher.this.startActivity(intent);
			}
		});
		final FBLAProjectLauncher fpl = this;
		enterGameButton = (ImageButton) findViewById(R.id.gameButton);
		enterGameButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FBLAProjectLauncher.this,
						FBLAProjectLibgdxLauncher.class);
				FBLAProjectLauncher.this.startActivity(intent);
			}
		});

		canPresentShareDialog = FacebookDialog.canPresentShareDialog(this,
				FacebookDialog.ShareDialogFeature.SHARE_DIALOG);

		points = (TextView) findViewById(R.id.points);

		if (Gdx.app == null) {
			points.setText("You can see points after completing an activity");
		} else {

			Preferences prefs = Gdx.app.getPreferences("data");
		}
		

	}

	private FragmentActivity getOuter() {
		return this;
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();

		AppEventsLogger.activateApp(this);
		if (Gdx.app == null) {
			points.setText("You can see points after completing an activity");
		} else {
			Preferences prefs = Gdx.app.getPreferences("data");
			System.out.println(prefs.getInteger("points"));
			points.setText("Points: " + String.valueOf(prefs.getInteger("points")));
		}
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}

		updateUI();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);

		outState.putString(PENDING_ACTION_BUNDLE_KEY, pendingAction.name());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
	}

	@SuppressWarnings("incomplete-switch")
	private void handlePendingAction() {
		PendingAction previouslyPendingAction = pendingAction;
		// These actions may re-set pendingAction if they are still pending, but
// we assume they
		// will succeed.
		pendingAction = PendingAction.NONE;

	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		callFacebookLogout(getBaseContext());
		uiHelper.onDestroy();
	}
	public static void callFacebookLogout(Context context) {
	    Session session = Session.getActiveSession();
	    if (session != null) {

	        if (!session.isClosed()) {
	            session.closeAndClearTokenInformation();
	            //clear your preferences if saved
	        }
	    } else {

	        session = new Session(context);
	        Session.setActiveSession(session);

	        session.closeAndClearTokenInformation();
	            //clear your preferences if saved

	    }

	}

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (pendingAction != PendingAction.NONE
				&& (exception instanceof FacebookOperationCanceledException || exception instanceof FacebookAuthorizationException)) {
			new AlertDialog.Builder(FBLAProjectLauncher.this).setTitle(R.string.cancelled)
					.setMessage(R.string.permission_not_granted)
					.setPositiveButton(R.string.ok, null).show();
			pendingAction = PendingAction.NONE;
		}
		updateUI();
	}

	private void updateUI() {
		Session session = Session.getActiveSession();
		boolean enableButtons = (session != null && session.isOpened());
		eventButton.setEnabled(enableButtons);
		postButton.setEnabled(enableButtons);

	}

	private interface GraphObjectWithId extends GraphObject {
		String getId();
	}

	private void showPublishResult(String message, GraphObject result, FacebookRequestError error) {
		String title = null;
		String alertMessage = null;
		if (error == null) {
			title = getString(R.string.success);
			String id = result.cast(GraphObjectWithId.class).getId();
			alertMessage = getString(R.string.successfully_posted_post, message, id);
		} else {
			title = getString(R.string.error);
			alertMessage = error.getErrorMessage();
		}

		new AlertDialog.Builder(this).setTitle(title).setMessage(alertMessage)
				.setPositiveButton(R.string.ok, null).show();
	}

	private FacebookDialog.ShareDialogBuilder createShareDialogBuilder() {
		return new FacebookDialog.ShareDialogBuilder(this).setName("fbla").setDescription("fbla")
				.setLink("http://developers.facebook.com/android");
	}

	private void showAlert(String title, String message) {
		new AlertDialog.Builder(this).setTitle(title).setMessage(message)
				.setPositiveButton(R.string.ok, null).show();
	}

	private boolean hasPublishPermission() {
		Session session = Session.getActiveSession();
		return session != null && session.getPermissions().contains("publish_actions");
	}

	private void performPublish(PendingAction action, boolean allowNoSession) {
		Session session = Session.getActiveSession();
		if (session != null) {
			pendingAction = action;
			if (hasPublishPermission()) {
				return;
			} else if (session.isOpened()) {

				session.requestNewPublishPermissions(new Session.NewPermissionsRequest(this,
						PERMISSION));
				return;
			}
		}

		if (allowNoSession) {
			pendingAction = action;
		}
	}

	

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		Session session = Session.getActiveSession();
		session.closeAndClearTokenInformation();

	}

}
