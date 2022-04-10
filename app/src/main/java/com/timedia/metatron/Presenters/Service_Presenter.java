package com.timedia.metatron.Presenters;

import android.content.Context;

import com.timedia.metatron.IPresenters.IPresenters;
import com.timedia.metatron.Interfaces.ITaskFinishListener;
import com.timedia.metatron.Interfaces.SwitchConstantKeys;
import com.timedia.metatron.Presenter_Views.IViews;
import com.timedia.metatron.Tasks.JobService_Task;

import java.io.IOException;

public class Service_Presenter implements IPresenters.AdminServices_Presenters {


    IViews.ServiceManagementDetails mServicesViews;
    ITaskFinishListener mFinish_Listener;

    public Service_Presenter(IViews.ServiceManagementDetails aServicesViews) {

        mServicesViews = aServicesViews;
        mFinish_Listener = new ITaskFinishListener() {
            @Override
            public void onFinished(int logicType, Object obj) {
                if (logicType == SwitchConstantKeys.Service_Mgmt.ListJob_Success) {
                    mServicesViews.SuccessResult(SwitchConstantKeys.Service_Mgmt.ListJob_Success, obj);
                } else if (logicType == SwitchConstantKeys.Service_Mgmt.ListJob_Failure) {
                    mServicesViews.FailureResult(SwitchConstantKeys.Service_Mgmt.ListJob_Failure, obj);
                }
            }
        };
    }


    @Override
    public void ListOfServices(Context aContext) {
        try {
            JobService_Task aJobServices = new JobService_Task(aContext, mFinish_Listener);
            aJobServices.ListServices();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
