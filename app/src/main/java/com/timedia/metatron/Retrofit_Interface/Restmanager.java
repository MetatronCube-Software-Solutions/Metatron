package com.timedia.metatron.Retrofit_Interface;

import com.timedia.metatron.Request_Model.AddJob_Request;
import com.timedia.metatron.Request_Model.AddParts_Request;
import com.timedia.metatron.Request_Model.AddService_Request;
import com.timedia.metatron.Request_Model.AddUser_Request;
import com.timedia.metatron.Request_Model.Changepassword_Request;
import com.timedia.metatron.Request_Model.EditParts_Request;
import com.timedia.metatron.Request_Model.ForgotPassword_Request;
import com.timedia.metatron.Request_Model.ModifyService_Request;
import com.timedia.metatron.Request_Model.ModifyUser_Request;
import com.timedia.metatron.Request_Model.ModiyJobs_Request;
import com.timedia.metatron.Request_Model.SiteDataRequest;
import com.timedia.metatron.Request_Model.UserLogin_Request;
import com.timedia.metatron.Response_Model.AddJobs_Response;
import com.timedia.metatron.Response_Model.AddService_Response;
import com.timedia.metatron.Response_Model.AddUser_Response;
import com.timedia.metatron.Response_Model.Add_Parts_Response;
import com.timedia.metatron.Response_Model.AssignTechnician_Request;
import com.timedia.metatron.Response_Model.GetCountryDetails;
import com.timedia.metatron.Response_Model.IsUniqueUser_Response;
import com.timedia.metatron.Response_Model.JobList_Response;
import com.timedia.metatron.Response_Model.ListPartsNames_Response;
import com.timedia.metatron.Response_Model.ListService_Response;
import com.timedia.metatron.Response_Model.ModifyService_Reponse;
import com.timedia.metatron.Response_Model.PartsDetail_Response;
import com.timedia.metatron.Response_Model.Parts_List_Response;
import com.timedia.metatron.Response_Model.ServerMessage_Response;
import com.timedia.metatron.Response_Model.Server_Msg_Object;
import com.timedia.metatron.Response_Model.ServiceListNames_Response;
import com.timedia.metatron.Response_Model.UserLogin_Response;
import com.timedia.metatron.Response_Model.UserRegister_Request;
import com.timedia.metatron.Response_Model.UserRegistration_Response;
import com.timedia.metatron.Response_Model.UsersList_Response;
import com.timedia.metatron.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Restmanager {

    @POST(Constants.UserLoginValidation)
    Call<UserLogin_Response> UserLoginValidation(@Body UserLogin_Request aRequest);

    @POST(Constants.UserRegistration)
    Call<UserRegistration_Response> UserRegistration(@Body UserRegister_Request aRequest);

    @POST(Constants.AddUsers)
    Call<AddUser_Response> AddUsers(@Body AddUser_Request aRequest);

    @GET(Constants.ListUsers)
    Call<UsersList_Response> ListUsers(@Query("userid") String auserid);

    @DELETE(Constants.RemoveUsers)
    Call<ServerMessage_Response> RemoveUsers(@Query("userid") String auserid);

    @POST(Constants.ModifyUsers)
    Call<Server_Msg_Object> ModifyUsers(@Body ModifyUser_Request aRequest);


    @GET(Constants.IsUniqueUser)
    Call<IsUniqueUser_Response> IsUniqueUsername(@Query("username") String aUsername,@Query("userid") String auserid);

    @GET(Constants.GetListOfParts)
    Call<Parts_List_Response> GetParts_List(@Query("userid") String auserid);

    @DELETE(Constants.RemoveParts)
    Call<ServerMessage_Response> RemoveParts(@Query("partid") String partid);

    @POST(Constants.AddParts)
    Call<Add_Parts_Response> AddParts(@Body AddParts_Request aRequest);

    @GET(Constants.ListOfServices)
    Call<ListService_Response> GetListOfServices(@Query("userid") String auserid);

    @POST(Constants.AddService)
    Call<AddService_Response> AddService(@Body AddService_Request aRequest);

    @DELETE(Constants.RemoveService)
    Call<ServerMessage_Response> RemoveService(@Query("serviceid") String aserviceid);

    @POST(Constants.ModifyService)
    Call<ModifyService_Reponse> ModifyService(@Body ModifyService_Request aRequest);

    @POST(Constants.ModifyParts)
    Call<Add_Parts_Response> ModifyParts(@Body EditParts_Request aRequest);

    @GET(Constants.ListPartNames)
    Call<ListPartsNames_Response> ListPartsNames(@Query("jobid") String jobid,@Query("userid") String auserid);

    @GET(Constants.ServiceListNames)
    Call<ServiceListNames_Response> GetServiceListNames(@Query("userid") String auserid);

    @GET(Constants.ListJobs)
    Call<JobList_Response> ListAllJobs(@Query("userid") String auserid);

    @GET(Constants.ListJobs)
    Call<JobList_Response> ListJobs(@Query("completed") String acompleted,@Query("userid") String auserid);

    @GET(Constants.ListJobs)
    Call<JobList_Response> ListJobsTechnician(@Query("techid") String aTechid,@Query("userid") String auserid);

    @POST(Constants.AddJob)
    Call<AddJobs_Response> AddJobs(@Body AddJob_Request aRequest);

    @GET(Constants.RemoveJob)
    Call<ServerMessage_Response> RemoveJobs(@Query("jobid") String aJobid,@Query("userid") String auserid);

    @POST(Constants.ModiyJobs)
    Call<Server_Msg_Object> ModifyJobs(@Body ModiyJobs_Request aRequest);

    @GET(Constants.TechnicianList)
    Call<UsersList_Response> TechnicianList(@Query("type") String atype,@Query("userid") String auserid);

    @POST(Constants.Changepassword)
    Call<ServerMessage_Response> ChangePassword(@Body Changepassword_Request aRequest);

    @POST(Constants.ForgotPassword)
    Call<ServerMessage_Response> ForgotPassword(@Body ForgotPassword_Request aRequest);

    @POST(Constants.Assign_Technician)
    Call<ServerMessage_Response> Assign_Technician(@Body AssignTechnician_Request aRequest);

    @GET(Constants.GetPartsDetail)
    Call<PartsDetail_Response> GetPartsDetail(@Query("partserial") String partserial,@Query("userid") String auserid);

    @PUT(Constants.UpdateSiteData)
    Call<ServerMessage_Response> UpdateSiteData(@Body SiteDataRequest aRequest);

    @GET(Constants.CountryDetails)
    Call<GetCountryDetails> GetCountryList(@Query("CountryId") String aCountry,@Query("StateId") String aStateId);
}

