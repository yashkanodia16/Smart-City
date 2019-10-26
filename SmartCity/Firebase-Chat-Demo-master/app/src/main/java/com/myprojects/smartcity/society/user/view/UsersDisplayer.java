package com.myprojects.smartcity.society.user.view;

import com.myprojects.smartcity.society.user.data_model.User;
import com.myprojects.smartcity.society.user.data_model.Users;

/**
 * Created by marco on 01/09/16.
 */

public interface UsersDisplayer {

    void display(Users users);

    void attach(UserInteractionListener userInteractionListener);

    void detach(UserInteractionListener userInteractionListener);

    interface UserInteractionListener {

        void onUserSelected(User user);

    }

    void filter(String text);

}
