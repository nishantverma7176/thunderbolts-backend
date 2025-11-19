//import com.verma.loginapi.model.Chat;
//import org.aspectj.lang.annotation.After;🟢 1. Register User
//
//POST → http://172.168.25.164:8080/api/auth/register
//
//        {
//        "name": "Nishant Verma",
//        "email": "nishant@example.com",
//        "phoneNumber": "7300623532",
//        "password": "12345678"
//        }
//
//
//
//
//
//        🟢 2. Login User
//
//POST → http://172.168.25.164:8080/api/auth/login
//
//        {
//        "username": "nishant@example.com",
//        "password": "12345678"
//        }
//
//
//
//        🟢 3. Forgot Password (Send OTP)
//
//POST → http://172.168.25.164:8080/api/auth/forgot-password
//
//        {
//        "email": "nishant@example.com"
//        }
//
//
//
//        🟢 4. Verify OTP
//
//POST → http://172.168.25.164:8080/api/auth/verify-otp
//
//        {
//        "email": "nishant@example.com",
//        "otp": "123456"
//        }
//
//
//
//        🟢 5. Reset Password (After OTP Verified)
//
//POST → http://172.168.25.164:8080/api/auth/reset-password
//
//        {
//        "email": "nishant@example.com",
//        "newPassword": "newpass123"
//        }
//
//
//
//
//        🟢 6. Change Password (Using Old Password)
//
//POST → http://172.168.25.164:8080/api/auth/change-password
//
//        {
//        "email": "nishant@example.com",
//        "oldPassword": "12345678",
//        "newPassword": "newpass456"
//        }
//
//        🟢 7. Health Check
//
//GET → http://172.168.25.164:8080/api/auth/health
//
//
//        🟢 8. Create / Open Chat
//
//POST → http://172.168.25.164:8080/api/chat/create
//
//        {
//        "senderPhone": "7300623532",
//        "receiverPhone": "7351546326"
//        }
//
//        🟢 9. Get All Chats for User
//
//GET → http://172.168.25.164:8080/api/chat/get/7300623532
//
//
//        🟢 10. Send Message (REST API)
//
//POST → http://172.168.25.164:8080/api/message/send
//
//        {
//        "senderPhone": "7300623532",
//        "receiverPhone": "7351546326",
//        "content": "Hello there, I’m Nishant Verma!"
//        }
//
//
//        🟢 11. Get All Messages by Chat ID
//
//GET → http://172.168.25.164:8080/api/message/1
//
//
//
//
//
