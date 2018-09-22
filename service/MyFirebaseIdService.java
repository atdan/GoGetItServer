package com.example.root.gogetitserver.service;

import com.example.root.gogetitserver.common.Common;
import com.example.root.gogetitserver.model.Token;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseIdService extends FirebaseInstanceIdService{
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        updateToServer(refreshedToken);
    }

    private void updateToServer(String refreshedToken) {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokensReference =  db.getReference("Tokens");

        Token token = new Token(refreshedToken,true); //false because this token is from client

        tokensReference.child(Common.current_user.getPhone()).setValue(token);
    }
}
