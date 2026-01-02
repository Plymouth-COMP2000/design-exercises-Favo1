package com.restaurant.app.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestaurantApiService {

    // 1. Create Student Database
    @POST("create_student/{student_id}")
    Call<ApiResponse> createStudentDatabase(@Path("student_id") String studentId);

    // 2. Create User Entry
    @POST("create_user/{student_id}")
    Call<ApiResponse> createUser(@Path("student_id") String studentId, @Body User user);

    // 3. Read All Users
    @GET("read_all_users/{student_id}")
    Call<UserListResponse> readAllUsers(@Path("student_id") String studentId);

    // 4. Read Specific User
    @GET("read_user/{student_id}/{username}")
    Call<User> readUser(@Path("student_id") String studentId, @Path("username") String username);

    // 5. Update User
    @PUT("update_user/{student_id}/{username}")
    Call<ApiResponse> updateUser(@Path("student_id") String studentId, @Path("username") String username, @Body User user);

    // 6. Delete User
    @DELETE("delete_user/{student_id}/{username}")
    Call<ApiResponse> deleteUser(@Path("student_id") String studentId, @Path("username") String username);
}