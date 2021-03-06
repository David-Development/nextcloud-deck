package it.niedermann.nextcloud.deck;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;

import com.nextcloud.android.sso.AccountImporter;
import com.nextcloud.android.sso.exceptions.NextcloudFilesAppNotInstalledException;
import com.nextcloud.android.sso.model.SingleSignOnAccount;

public class LoginDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        try {
            AccountImporter.pickNewAccount(this);
        } catch (NextcloudFilesAppNotInstalledException e) {
            Log.e("Deck", "=============================================================");
            Log.e("Deck", "Nextcloud app is not installed. Cannot choose account");
            // ToDo redirect to Play Store
            e.printStackTrace();
        }
        return super.onCreateDialog(savedInstanceState);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        AccountImporter.onActivityResult(requestCode, resultCode, data, LoginDialogFragment.this, new AccountImporter.IAccountAccessGranted() {
            @Override
            public void accountAccessGranted(SingleSignOnAccount account) {
                ((MainActivity) getActivity()).onAccountChoose(account);
            }
        });
    }
}
