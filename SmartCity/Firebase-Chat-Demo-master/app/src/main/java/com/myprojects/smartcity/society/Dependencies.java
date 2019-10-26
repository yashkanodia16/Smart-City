package com.myprojects.smartcity.society;

import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.myprojects.smartcity.society.analytics.Analytics;
import com.myprojects.smartcity.society.analytics.FirebaseAnalyticsAnalytics;
import com.myprojects.smartcity.society.conversation.database.FirebaseConversationDatabase;
import com.myprojects.smartcity.society.conversation.service.ConversationService;
import com.myprojects.smartcity.society.conversation.service.PersistedConversationService;
import com.myprojects.smartcity.society.conversation_list.database.FirebaseConversationListDatabase;
import com.myprojects.smartcity.society.conversation_list.service.ConversationListService;
import com.myprojects.smartcity.society.conversation_list.service.PersistedConversationListService;
import com.myprojects.smartcity.society.global.database.FirebaseGlobalDatabase;
import com.myprojects.smartcity.society.global.service.GlobalService;
import com.myprojects.smartcity.society.global.service.PersistedGlobalService;
import com.myprojects.smartcity.society.login.database.FirebaseAuthDatabase;
import com.myprojects.smartcity.society.login.service.FirebaseLoginService;
import com.myprojects.smartcity.society.login.service.LoginService;
import com.myprojects.smartcity.society.main.database.FirebaseCloudMessagingDatabase;
import com.myprojects.smartcity.society.main.service.CloudMessagingService;
import com.myprojects.smartcity.society.main.service.FirebaseCloudMessagingService;
import com.myprojects.smartcity.society.main.service.MainService;
import com.myprojects.smartcity.society.main.service.PersistedMainService;
import com.myprojects.smartcity.society.profile.service.FirebaseProfileService;
import com.myprojects.smartcity.society.profile.service.ProfileService;
import com.myprojects.smartcity.society.registration.service.FirebaseRegistrationService;
import com.myprojects.smartcity.society.registration.service.RegistrationService;
import com.myprojects.smartcity.society.rx.FirebaseObservableListeners;
import com.myprojects.smartcity.society.storage.FirebaseStorageService;
import com.myprojects.smartcity.society.storage.StorageService;
import com.myprojects.smartcity.society.user.database.FirebaseUserDatabase;
import com.myprojects.smartcity.society.user.service.PersistedUserService;
import com.myprojects.smartcity.society.user.service.UserService;

/**
 * Created by marco on 27/07/16.
 */

public enum Dependencies {
    INSTANCE;

    private Analytics analytics;
    private RegistrationService registrationService;
    private LoginService loginService;
    private ConversationListService conversationListService;
    private ConversationService conversationService;
    private GlobalService globalService;
    private UserService userService;
    private MainService mainService;
    private ProfileService profileService;
    private CloudMessagingService messagingService;
    private StorageService storageService;
    private String firebaseToken;
    //private Config config;

    private boolean setPersistence = false;

    public void init(Context context) {
        if (needsInitialisation()) {
            Context appContext = context.getApplicationContext();
            //FirebaseApp firebaseApp = FirebaseApp.initializeApp(appContext, FirebaseOptions.fromResource(appContext), "ChatFire");
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseToken = FirebaseInstanceId.getInstance().getToken();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//            if (!setPersistence) {
//                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//                setPersistence = true;
//            }
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            FirebaseObservableListeners firebaseObservableListeners = new FirebaseObservableListeners();
            FirebaseUserDatabase userDatabase = new FirebaseUserDatabase(firebaseDatabase, firebaseObservableListeners);
            FirebaseCloudMessagingDatabase messagingDatabase = new FirebaseCloudMessagingDatabase(firebaseDatabase, firebaseObservableListeners, firebaseToken);
            FirebaseConversationDatabase conversationDatabase = new FirebaseConversationDatabase(firebaseDatabase, firebaseObservableListeners);
            FirebaseConversationListDatabase conversationListDatabase = new FirebaseConversationListDatabase(firebaseDatabase,firebaseObservableListeners);

            analytics = new FirebaseAnalyticsAnalytics(FirebaseAnalytics.getInstance(appContext));
            loginService = new FirebaseLoginService(new FirebaseAuthDatabase(firebaseAuth),messagingDatabase);
            registrationService = new FirebaseRegistrationService(firebaseAuth);
            conversationService = new PersistedConversationService(conversationDatabase);
            globalService = new PersistedGlobalService(new FirebaseGlobalDatabase(firebaseDatabase, firebaseObservableListeners));
            userService = new PersistedUserService(userDatabase);
            conversationListService = new PersistedConversationListService(conversationListDatabase,conversationDatabase,userDatabase);
            mainService = new PersistedMainService(firebaseAuth, userDatabase, messagingDatabase);
            profileService = new FirebaseProfileService(firebaseAuth);

            messagingService = new FirebaseCloudMessagingService(messagingDatabase);
            storageService = new FirebaseStorageService(firebaseStorage,firebaseObservableListeners);
//            config = FirebaseConfig.newInstance().init(errorLogger);
        }
    }

    public void clearDependecies() {
        loginService = null;
        conversationListService = null;
        conversationService = null;
        userService = null;
    }

    private boolean needsInitialisation() {
        return loginService == null || conversationListService == null || conversationService == null || registrationService == null
                || userService == null;// || analytics == null || errorLogger == null;
    }

   /* public Analytics getAnalytics() {
        return analytics;
    }*/

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void initFirebaseToken() {
        firebaseToken = FirebaseInstanceId.getInstance().getToken();
    }

    public MainService getMainService() {
        return mainService;
    }

    public CloudMessagingService getMessagingService() {
        return messagingService;
    }

    public GlobalService getGlobalService() {
        return globalService;
    }

    public ConversationService getConversationService() {
        return conversationService;
    }

    public ConversationListService getConversationListService() {
        return conversationListService;
    }

    public RegistrationService getRegistrationService() {
        return registrationService;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public ProfileService getProfileService() {
        return profileService;
    }

    public UserService getUserService() {
        return userService;
    }

    public StorageService getStorageService() {
        return storageService;
    }

    /*
    public Config getConfig() {
        return config;
    }*/
}
