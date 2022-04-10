package com.timedia.metatron.Presenter_Views;

public interface IViews {


    interface GetUserLoginDetails {
        void UserLoginSuccess(int aResultCode, Object aObject);

        void UserError(int aResultCode, Object aObject);
    }

    interface AddUserShopReg {
        void RegisterShopsuccess(int aResultCode, Object aObject);

        void RegisterShopfailure(int aResultCode, Object aObject);
    }

    interface UserRegistrationDetails {
        void AddUserSuccess(int aResultCode, Object aObject);

        void AddUserError(int aResultCode, Object aObject);
    }

    interface PartsManagementDetails {
        void SuccessResult(int aResultCode, Object aObject);

        void FailureResult(int aResultCode, Object aObject);
    }

    interface ServiceManagementDetails {
        void SuccessResult(int aResultCode, Object aObject);

        void FailureResult(int aResultCode, Object aObject);
    }

}
