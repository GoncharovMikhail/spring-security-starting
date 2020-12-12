/**
 * A <pre> POST </pre> requests executor for
 * <pre> success </pre> page.
 * For admins <strong>ONLY</strong>.
 */
export default class SuccessPageAdminPostRequestsExecutor {

    executeBanUserPostRequest(username) {
        return $.post(
            {
                url: '/ban',
                contentType: "application/json; charset=UTF-8",
                mimeType: "text/html; charset=UTF-8",
                scriptCharset: "utf-8",
                data: JSON.stringify(
                    {
                        "username": username
                    }
                )
            }
        );
    }

    executeUnbanUserPostRequest(username) {
        return $.post(
            {
                url: '/unban',
                contentType: "application/json; charset=UTF-8",
                mimeType: "text/html; charset=UTF-8",
                scriptCharset: "utf-8",
                data: JSON.stringify(
                    {
                        "username": username
                    }
                )
            }
        );
    }
}