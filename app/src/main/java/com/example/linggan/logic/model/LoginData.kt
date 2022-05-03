package com.example.linggan.logic.model

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/4/30
 */

/*
{
    "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcyI6IlJFRFJPQ0siLCJleHAiOjE2NTEyOTk3NTAsImlhdCI6MTY1MTI5MjU1MCwidXNlcklEIjoyfQ.cqAjnP5wn_i5QLFJAD42KB93rNs5ubEQYYVWphIq4PuQDkNOyprtR0Y0HGVSLtLVz6XLRuhWWt0pMGyvwyLfXw",
    "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcyI6IlJFRFJPQ0siLCJleHAiOjE2NTM4ODQ1NTAsImlhdCI6MTY1MTI5MjU1MCwidXNlcklEIjoyfQ.3q3lqn9uPEQtzUd_AaFv7ToNnIJNGVZDKoPh-7Q3zuE224SH95vQlP56rOkW665VEFUs2bp7EK167HhETflEtg"
}
 */
data class LoginData(val token: String)

data class LoginBody(val phone_number: Int)

data class RegisterBody(val phone_number: Int, val name: String)