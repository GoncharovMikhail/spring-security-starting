export default class CommonGetRequestsExecutor {

    executeSearchSomeOnesAgendaGetRequest(username) {
        return $.get(
            {
                url: '/search',
                contentType: "application/json; charset=UTF-8",
                mimeType: "text/html; charset=UTF-8",
                scriptCharset: "utf-8",
                data: {
                    "username": username
                }
            }
        )
    }
}