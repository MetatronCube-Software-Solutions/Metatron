package com.timedia.metatron.Interfaces;

public interface SwitchConstantKeys {

    interface UserLogin {
        int Login_Success = 1;
        int Login_Failure = 2;
        int Internet_Failure = 3;
        int SomethingError = 4;
    }

    interface UserRegister {
        int RegisterSuccess = 1;
        int Register_Failure = 2;
        int Internet_Failure = 3;
        int SomethingError = 4;
    }

    interface AddUsers {
        int AddUser_Success = 5;
        int AddUser_Failure = 6;
        int Internet_Failure = 7;
        int SomethingError = 8;
    }

    interface Parts_Mgmt {
        int ListParts_Success = 9;
        int ListParts_Failure = 10;
        int Internet_Failure = 11;
        int SomethingError = 12;
        int PartsRemove_Success = 13;
        int PartsRemove_Failure = 14;
        int AddParts_Success = 15;
        int AddParts_failure = 16;
    }

    interface Service_Mgmt {
        int ListJob_Success = 17;
        int ListJob_Failure = 18;
        int Internet_Failure = 19;
        int SomethingError = 20;
    }
}
