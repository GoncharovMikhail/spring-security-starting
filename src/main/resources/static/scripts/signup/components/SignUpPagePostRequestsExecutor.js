export default class SignUpPagePostRequestsExecutor {

    executeRegistrationPostRequest(email, username, password) {
        $.post(
            {
                url: '/registration',
                contentType: "application/json; charset=UTF-8",
                mimeType: "text/html; charset=UTF-8",
                scriptCharset: "utf-8",
                data: JSON.stringify(
                    {
                        "email": email,
                        "username": username,
                        "password": password
                    }
                )
            }
        )
    }
}