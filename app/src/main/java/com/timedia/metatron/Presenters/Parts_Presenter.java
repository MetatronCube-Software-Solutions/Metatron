package com.timedia.metatron.Presenters;

import android.content.Context;

import com.timedia.metatron.IPresenters.IPresenters;
import com.timedia.metatron.Interfaces.ITaskFinishListener;
import com.timedia.metatron.Interfaces.SwitchConstantKeys;
import com.timedia.metatron.Presenter_Views.IViews;
import com.timedia.metatron.Request_Model.AddParts_Request;
import com.timedia.metatron.Request_Model.EditParts_Request;
import com.timedia.metatron.Tasks.Parts_Task;

import java.io.IOException;


public class Parts_Presenter implements IPresenters.UserParts_Presenters {

    IViews.PartsManagementDetails mPartsViews;
    ITaskFinishListener mFinish_Listener;

    public Parts_Presenter(final IViews.PartsManagementDetails aPartsViews) {

        mPartsViews = aPartsViews;
        mFinish_Listener = new ITaskFinishListener() {
            @Override
            public void onFinished(int logicType, Object obj) {
                if (logicType == SwitchConstantKeys.Parts_Mgmt.ListParts_Success) {
                    aPartsViews.SuccessResult(SwitchConstantKeys.Parts_Mgmt.ListParts_Success, obj);
                } else if (logicType == SwitchConstantKeys.Parts_Mgmt.ListParts_Failure) {
                    aPartsViews.FailureResult(SwitchConstantKeys.Parts_Mgmt.ListParts_Failure, obj);
                } else if (logicType == SwitchConstantKeys.Parts_Mgmt.PartsRemove_Success) {
                    aPartsViews.SuccessResult(SwitchConstantKeys.Parts_Mgmt.PartsRemove_Success, obj);
                } else if (logicType == SwitchConstantKeys.Parts_Mgmt.PartsRemove_Failure) {
                    aPartsViews.FailureResult(SwitchConstantKeys.Parts_Mgmt.PartsRemove_Failure, obj);
                } else if (logicType == SwitchConstantKeys.Parts_Mgmt.AddParts_Success) {
                    aPartsViews.SuccessResult(SwitchConstantKeys.Parts_Mgmt.AddParts_Success, obj);
                } else if (logicType == SwitchConstantKeys.Parts_Mgmt.AddParts_failure) {
                    aPartsViews.FailureResult(SwitchConstantKeys.Parts_Mgmt.AddParts_failure, obj);
                }
            }
        };
    }


    @Override
    public void ListParts(Context aContext) {
        try {
            Parts_Task aParts_Task = new Parts_Task(aContext, mFinish_Listener);
            aParts_Task.GetParts_List();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void RemoveParts(Context aContext, String aPartid) {
        try {
            Parts_Task aParts_Task = new Parts_Task(aContext, mFinish_Listener);
            aParts_Task.Remove_Parts(aPartid);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void AddParts(Context aContext, AddParts_Request aRequest) {
        try {
            Parts_Task aParts_Task = new Parts_Task(aContext, mFinish_Listener);
            aParts_Task.AddParts(aRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ModifyParts(Context aContext, EditParts_Request aRequest) {
        try {
            Parts_Task aParts_Task = new Parts_Task(aContext, mFinish_Listener);
            aParts_Task.ModifyParts(aRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
