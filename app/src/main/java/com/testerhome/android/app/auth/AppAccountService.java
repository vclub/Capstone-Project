package com.testerhome.android.app.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;

import com.testerhome.android.app.R;
import com.testerhome.android.app.models.OAuth;
import com.testerhome.android.app.models.TesterUser;
import com.testerhome.android.app.util.SharedPreferencesHelper;

/**
 * Created by Bin Li on 2016/6/21.
 */

public class AppAccountService {

    private volatile static AppAccountService mInstance;

    private final String KEY_DEFAULT_ACCOUNT = "defaultAccount";
    private final String KEY_USER_DATA_USER_ID = "uer_id";
    private final String KEY_USER_DATA_ACCOUNT_NAME = "username";
    private final String KEY_USER_DATA_NICKNAME = "nickname";
    private final String KEY_USER_DATA_AVATAR = "avatar_url";
    private final String KEY_USER_DATA_LOCATION = "location";
    private final String KEY_USER_DATA_WEBSITE = "website";
    private final String KEY_USER_DATA_GITHUB = "github";
    private final String KEY_USER_DATA_EMAIL = "email";
    private final String KEY_USER_DATA_TOKEN = "access_token";
    private final String KEY_USER_DATA_COMPANY = "company";
    private final String KEY_USER_DATA_TAGLINE = "tagline";
    private final String KEY_USER_DATA_REFRESH_TOKEN = "refresh_token";
    private final String KEY_USER_DATA_EXPIRE_DATE = "expireDate";
    private final String KEY_USER_DATA_CREATE_AT = "create_at";

    private Context mContext;
    private AccountManager mAccountManager;
    private Account activeAccount;

    public static AppAccountService getInstance(Context ctx){
        if (mInstance == null){
            synchronized (AppAccountService.class) {
                if (mInstance == null) {
                    mInstance = new AppAccountService(ctx.getApplicationContext());
                }
            }
        }
        return mInstance;
    }


    public AppAccountService(Context context) {
        this.mContext = context;
        mAccountManager = AccountManager.get(context);
        if (hasDefaultAccount()){
            activeAccount = getDefaultAccount();
        }
    }

    private static final String TAG = "AccountService";

    public void updateAccountInfo(Account account, TesterUser userProfile) {
        mAccountManager.setUserData(account, KEY_USER_DATA_USER_ID, String.valueOf(userProfile.getId()));
        mAccountManager.setUserData(account, KEY_USER_DATA_ACCOUNT_NAME, userProfile.getLogin());
        mAccountManager.setUserData(account, KEY_USER_DATA_NICKNAME, userProfile.getName());
        mAccountManager.setUserData(account, KEY_USER_DATA_AVATAR, userProfile.getAvatar_url());
        mAccountManager.setUserData(account, KEY_USER_DATA_GITHUB, userProfile.getGithub());
        mAccountManager.setUserData(account, KEY_USER_DATA_WEBSITE, userProfile.getWebsite());
        mAccountManager.setUserData(account, KEY_USER_DATA_EMAIL, userProfile.getEmail());
        mAccountManager.setUserData(account, KEY_USER_DATA_LOCATION, userProfile.getLocation());
        mAccountManager.setUserData(account, KEY_USER_DATA_TOKEN, userProfile.getAccess_token());
        mAccountManager.setUserData(account,KEY_USER_DATA_COMPANY,userProfile.getCompany());
        mAccountManager.setUserData(account,KEY_USER_DATA_TAGLINE,userProfile.getTagline());
        if (userProfile.getRefresh_token() != null) {
            mAccountManager.setUserData(account, KEY_USER_DATA_EXPIRE_DATE, String.valueOf(userProfile.getExpireDate()));
            Log.d(TAG, "updateAccountInfo timeout: " + userProfile.getExpireDate());
            mAccountManager.setUserData(account,KEY_USER_DATA_REFRESH_TOKEN,userProfile.getRefresh_token());
            mAccountManager.setUserData(account,KEY_USER_DATA_CREATE_AT,String.valueOf(userProfile.getCreate_at()));
        }
    }

    public void updateAccountToken(OAuth oAuth){
        mAccountManager.setUserData(activeAccount, KEY_USER_DATA_TOKEN, oAuth.getAccess_token());
        long timeout = System.currentTimeMillis() + oAuth.getExpires_in() * 1000 - (6 * 60 * 60 * 1000);
        mAccountManager.setUserData(activeAccount, KEY_USER_DATA_EXPIRE_DATE, String.valueOf(timeout));
        mAccountManager.setUserData(activeAccount,KEY_USER_DATA_REFRESH_TOKEN,oAuth.getRefresh_token());
        mAccountManager.setUserData(activeAccount,KEY_USER_DATA_CREATE_AT,String.valueOf(oAuth.getCreate_at()));
    }

    public boolean login(String username, TesterUser user, OAuth oAuth){
        if (user == null) {
            return false;
        }
        Account account = setActiveAccount(username, oAuth.getAccess_token());
        setDefaultAccount(account);

        user.setAccess_token(oAuth.getAccess_token());
        user.setRefresh_token(oAuth.getRefresh_token());
        user.setCreate_at(oAuth.getCreate_at());

        long timeout = System.currentTimeMillis() + oAuth.getExpires_in() * 1000 - (6 * 60 * 60 * 1000);
        user.setExpireDate(timeout);
        updateAccountInfo(account, user);

        return true;
    }

    public Account setActiveAccount(String username, String password) {
        Account account = findAccountByUsername(username);
        if (account == null) {
            account = new Account(username, mContext.getString(R.string.ACCOUNT_TYPE));
            mAccountManager.addAccountExplicitly(account, password, null);
        }
        this.activeAccount = account;

        return account;
    }

    public void logout() {
        Account account = getDefaultAccount();
        if (account != null) {
            mAccountManager.removeAccount(account, null, null);
            SharedPreferencesHelper.remove(mContext, KEY_DEFAULT_ACCOUNT);
            activeAccount = null;

            // clean cookie
            CookieManager.getInstance().removeSessionCookie();
        }
    }

    public TesterUser getActiveAccountInfo() {
        if (null != activeAccount)
            return getAccountInfo(activeAccount);
        else {
            return new TesterUser();
        }
    }

    private TesterUser getAccountInfo(Account account) {
        TesterUser user = new TesterUser();
        user.setId(mAccountManager.getUserData(account, KEY_USER_DATA_USER_ID));
        user.setLogin(mAccountManager.getUserData(account, KEY_USER_DATA_ACCOUNT_NAME));
        user.setName(mAccountManager.getUserData(account, KEY_USER_DATA_NICKNAME));
        user.setAvatar_url(mAccountManager.getUserData(account, KEY_USER_DATA_AVATAR));
        user.setGithub(mAccountManager.getUserData(account, KEY_USER_DATA_GITHUB));
        user.setWebsite(mAccountManager.getUserData(account, KEY_USER_DATA_WEBSITE));
        user.setEmail(mAccountManager.getUserData(account, KEY_USER_DATA_EMAIL));
        user.setLocation(mAccountManager.getUserData(account, KEY_USER_DATA_LOCATION));
        user.setAccess_token(mAccountManager.getUserData(account, KEY_USER_DATA_TOKEN));
        user.setCompany(mAccountManager.getUserData(account, KEY_USER_DATA_COMPANY));
        user.setTagline(mAccountManager.getUserData(account, KEY_USER_DATA_TAGLINE));

        if (mAccountManager.getUserData(account,KEY_USER_DATA_REFRESH_TOKEN) != null) {
            user.setExpireDate(Long.valueOf(mAccountManager.getUserData(account, KEY_USER_DATA_EXPIRE_DATE)));
            Log.d(TAG, "getAccountInfo timeout: " + user.getExpireDate());
            user.setRefresh_token(mAccountManager.getUserData(account, KEY_USER_DATA_REFRESH_TOKEN));
            user.setCreate_at(Long.valueOf(mAccountManager.getUserData(account, KEY_USER_DATA_CREATE_AT)));
        }

        return user;
    }

    private void setDefaultAccount(Account defaultAccount) {
        SharedPreferencesHelper.setValue(mContext, KEY_DEFAULT_ACCOUNT, defaultAccount.name);
        boolean foundAccount = false;
        Account[] accounts = mAccountManager.getAccounts();
        for (int i = 0; i < accounts.length && !foundAccount; i++) {
            if (accounts[i].name.equals(defaultAccount.name)) {
                foundAccount = true;
            }
        }
    }

    private boolean hasDefaultAccount() {
        return getDefaultAccount() != null;
    }

    private Account getDefaultAccount() {
        String accountName = SharedPreferencesHelper.getValue(mContext, KEY_DEFAULT_ACCOUNT);
        return findAccountByUsername(accountName);
    }

    private Account findAccountByUsername(String username) {
        Log.d("tab", "findAccountByUsername, username: " + username);
        if (username.length() > 0) {
            for (Account account : mAccountManager.getAccountsByType(mContext.getString(R.string.ACCOUNT_TYPE))) {
                Log.d("tab", "findAccountByUsername, a.name: " + account.name);
                if (account.name.equals(username)) {
                    return account;
                }
            }
        }
        return null;
    }
}
