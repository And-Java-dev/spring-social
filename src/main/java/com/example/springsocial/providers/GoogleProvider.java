package com.example.springsocial.providers;


import com.example.springsocial.model.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class GoogleProvider {

    private static final String REDIRECT_CONNECT_GOOGLE = "redirect:/index";
    private static final String GOOGLE = "google";

    @Autowired
    BaseProvider socialLoginBean;

    public String getGoogleUserData(Model model, UserBean userForm) {

        ConnectionRepository connectionRepository = socialLoginBean.getConnectionRepository();
        if (connectionRepository.findPrimaryConnection(Google.class) == null) {
            return REDIRECT_CONNECT_GOOGLE;
        }

        populateUserDetailsFromGoogle(userForm);
        model.addAttribute("loggedInUser",userForm);
        return "user";
    }

    protected  void populateUserDetailsFromGoogle(UserBean userBean){
        Google google = socialLoginBean.getGoogle();
        Person googleUser = google.plusOperations().getGoogleProfile();
        userBean.setFirstName(googleUser.getGivenName());
        userBean.setLastName(googleUser.getFamilyName());
        userBean.setImage(googleUser.getImageUrl());
        userBean.setProvider(GOOGLE);
    }
}
