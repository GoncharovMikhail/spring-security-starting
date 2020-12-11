export default class SignUpPagePostRequestsExecutor {

    executeRegistrationPostRequest(verifiedRegistrationData) {
        return this.#registrationPostRequest(
            verifiedRegistrationData.getEmail(),
            verifiedRegistrationData.getUsername(),
            verifiedRegistrationData.getPassword()
        )
    }

    #registrationPostRequest(email, username, password) {
        return $.post(
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